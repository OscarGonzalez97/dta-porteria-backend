package com.roshka.dtaporteria.service;
import com.roshka.dtaporteria.config.DtaConfig;
import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.dto.RecordDTO;
import com.roshka.dtaporteria.model.LogErrores;
import com.roshka.dtaporteria.model.Member;
import com.roshka.dtaporteria.model.Records;
import com.roshka.dtaporteria.model.Sync;
import com.roshka.dtaporteria.repository.LogErroresRepository;
import com.roshka.dtaporteria.repository.MembersRepository;
import com.roshka.dtaporteria.repository.RecordsRepository;
import com.roshka.dtaporteria.repository.SyncRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Configuration
@EnableScheduling
public class Scheduler {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MembersRepository membersRepository;
    @Autowired
    private LogErroresRepository logErroresRepository;

    @Autowired
    private RecordsRepository recordsRepository;
    @Autowired
    private RecordService recordService;

    @Autowired
    private SyncRepository syncRepository;

    @Autowired
    private DtaConfig dtaConfig;

    private static final String GET_MEMBER_TO_SYNC_SQL =
            "select c.cli_doc_ident_propietario, c.cli_codigo, c.cli_nom, c.cli_ape, c.cli_est_cli " +
            ",min(e.fecha_vencimiento) as vencimiento " +
            "from fin_cliente c " +
            " left outer join estado_socios e " +
            "                 on c.cli_codigo = e.cod_socio " +
                             "and (e.saldo_cuota > 0  or e.saldo_cuota is null) " +
            "where c.cli_est_cli in ('A','V', 'I','B') " +
            "group by c.cli_doc_ident_propietario, c.cli_codigo, c.cli_nom, c.cli_ape, c.cli_est_cli";

    @Scheduled(cron = "00 00 00 * * *")
    public void tasks(){
        if (syncRepository.findAll().isEmpty()){
            syncRepository.save(new Sync(1,0));
        }
        syncRepository.resetCantidad();
        syncMembers();
        checkFechaAndSync();
        syncRecords();

    }

    @Scheduled(cron = "00 00 00 * * *")
    public void makeDbSync() {
        // Consulta a Oracle y actualización en PostgreSQL
        Connection conn = null;
        try {
            Locale.setDefault(Locale.US);
            Class.forName(dtaConfig.getDataSourceDriverClassName());
            conn = DriverManager.getConnection(
                    dtaConfig.getDataSourceUrl(),
                    dtaConfig.getDataSourceUsername(),
                    dtaConfig.getDataSourcePassword());

            System.out.println(GET_MEMBER_TO_SYNC_SQL);
            membersRepository.updateAllByStatus(Member.STATUS_INACTIVE);

            ResultSet rs = conn.prepareStatement(GET_MEMBER_TO_SYNC_SQL).executeQuery();
            while (rs.next()) {
                Member member = getMemberFromRS(rs);
                //System.out.println(member);

                Member memberData = membersRepository.findOneByIdMember(member.getIdMember());
                if(memberData != null && memberData.getIdMember()!=null) {
                    member.setId(memberData.getId());
                }

                membersRepository.save(member);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void makeFirebaseSync(){
        List<MemberDTO> fbMembers = memberService.list();

        List<Member> members = membersRepository.findAll();
        System.out.println("members size ::" + members.size());
        int i=0;
        try {
            for (Member member : members) {
                System.out.println("# "  + i++);
//                System.out.println(member);
                if (Member.STATUS_ACTIVE.equals(member.getStatus())) {

                    if (fbMembers.stream().anyMatch(m -> m.getId().equalsIgnoreCase(member.getId()))) {
                        //se actualiza
                        memberService.updateFirebase(member);
                    } else {
                        //se inserta
                        memberService.addFirebase(member);
                    }
                } else if (Member.STATUS_INACTIVE.equals(member.getStatus())) {
                    // borra (dependiendo del status)
                    if (fbMembers.stream().anyMatch(m -> m.getId().equalsIgnoreCase(member.getId()))) {
                        memberService.deleteFirebase(member.getId());
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private Member getMemberFromRS(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setIdMember(rs.getString("cli_codigo"));
        member.setCreatedBy("sync");
        member.setName(rs.getString("cli_nom"));
        member.setSurname(rs.getString("cli_ape"));
        member.setCi(rs.getString("cli_doc_ident_propietario"));
        member.setType(Member.TYPE_SOCIO);
        member.setIsDefaulter(""+false);
        member.setStatus(Member.STATUS_ACTIVE);

        String sourceStatus = rs.getString("cli_est_cli");
        if("B".equalsIgnoreCase(sourceStatus)){
            member.setStatus(Member.STATUS_INACTIVE);
        }

        java.sql.Timestamp vencimientoUltimaCuotaPaga = rs.getTimestamp("vencimiento");
        if(vencimientoUltimaCuotaPaga!=null){
            //si el vencimiento de la ultima cuota tiene mas de n meses, se marca 'true' el 'defaulter'
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(vencimientoUltimaCuotaPaga.getTime());
            c.add(Calendar.MONTH,dtaConfig.getMemberAmountMonthArrearsClaim());
            member.setIsDefaulter(""+Calendar.getInstance().after(c));
        }


        return member;
    }

    public void syncMembers(){
        List<MemberDTO> listaMember;
        if(!membersRepository.findAll().isEmpty())
            return;

        listaMember = memberService.list();
        if(listaMember==null){
            Date now = new Date();
            String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(now);
            logErroresRepository.save(new LogErrores(fecha,
                    "Hubo un Error en la sincronizacion"));
            return;
        }
        List<Member> listaMemberPg = new LinkedList<>();
        for(MemberDTO member : listaMember)
            listaMemberPg.add(membersPg(member));
        membersRepository.saveAll(listaMemberPg);
    }
    public void syncRecords(){
        List<RecordDTO> listaRecords;
        if(recordsRepository.findAll().isEmpty())
            listaRecords = recordService.list();
        else
            listaRecords = recordService.listToSync();
        if(listaRecords==null){
            Date now = new Date();
            String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(now);
            logErroresRepository.save(new LogErrores(fecha,
                    "Hubo un Error en la sincronizacion"));
            return;
        }
        List<Records> listaRecordsPg = toListRecord(listaRecords);
        recordsRepository.saveAll(listaRecordsPg);
    }

    private List<Records> toListRecord(List<RecordDTO> lista){
        List<Records> listToReturn = new LinkedList<>();
        for(RecordDTO record : lista)
            listToReturn.add(transformer(record));
        return listToReturn;
    }
    private Records transformer(RecordDTO record){
        return new Records(record.getId(),record.getCi_member(),record.getCi_portero(),
                record.getDate_time(),
                record.getEmail_portero(),
                record.getId_member(),
                record.getIs_defaulter(),
                record.getIs_exit(),
                record.getIs_walk(),
                record.getName_member(),
                record.getName_portero(),
                record.getPhoto(),
                record.getSurname_member(),
                record.getSurname_portero(),
                record.getType(),
                record.getSector());
    }

    public void checkFechaAndSync(){
        List<MemberDTO>members = memberService.list();
        List<Member>resultMembers = new LinkedList<>();
        if (members != null) {
            Map<String, MemberDTO> fecha_vencimiento = members.stream().collect(Collectors.toMap(MemberDTO::getId, Function.identity()));
            fecha_vencimiento.forEach(
                    (key, value) -> {
                        if (!Objects.equals(value.getType(), "Socio") && !isAfterCurrentDate(value.getFecha_vencimiento())){
                            try {
                                memberService.delete(key);
                            } catch (Exception e) {
                                Date now = new Date();
                                String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(now);
                                logErroresRepository.save(new LogErrores(fecha,
                                        "No se pudo borrar un miembro con fecha de vencimiento invalida"));
                            }
                        }
                        else{
                            resultMembers.add(membersPg(value));
                        }
                    }
            );
        }
        membersRepository.saveAll(resultMembers);
    }

    public Boolean isAfterCurrentDate(String fecha){
        if (fecha == null)
        {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(fecha, formatter);
        return ld.isAfter(LocalDate.now());
    }
    public Member membersPg(MemberDTO memberDTO){
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setCreatedBy(memberDTO.getCreated_by());
        member.setCi(memberDTO.getCi());
        member.setIdMember(memberDTO.getId_member());
        member.setIsDefaulter(memberDTO.getIs_defaulter());
        member.setName(memberDTO.getName());
        member.setPhoto("");
        member.setSurname(memberDTO.getSurname());
        member.setType(memberDTO.getType());
        member.setFechaVencimiento(memberDTO.getFecha_vencimiento());
        member.setStatus(Member.STATUS_ACTIVE);
        return member;
    }
}
