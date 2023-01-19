package com.roshka.dtaporteria.service;
import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.dto.RecordDTO;
import com.roshka.dtaporteria.model.LogErrores;
import com.roshka.dtaporteria.model.Member;
import com.roshka.dtaporteria.repository.LogErroresRepository;
import com.roshka.dtaporteria.repository.MembersRepository;
import com.roshka.dtaporteria.repository.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    
    @Scheduled(cron = "0 0 0 * * *")

    public void tasks(){
        checkFechaAndSync();
    }
    

    public void syncRecords(){
        List<RecordDTO> listaRecords = recordService.listToSync();
    }

    public void checkFechaAndSync(){
        List<MemberDTO>members = memberService.list();
        List<Member>resultMembers = new LinkedList<>();
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
        membersRepository.saveAll(resultMembers);
    }

    public Boolean isAfterCurrentDate(String fecha){
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
        return member;
    }
}
