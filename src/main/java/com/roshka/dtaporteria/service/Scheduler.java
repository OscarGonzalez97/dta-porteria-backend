package com.roshka.dtaporteria.service;
import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.model.LogErrores;
import com.roshka.dtaporteria.model.Member;
import com.roshka.dtaporteria.repository.LogErroresRepository;
import com.roshka.dtaporteria.repository.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Configuration
@EnableScheduling
public class Scheduler {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MembersRepository membersRepository;
    @Autowired
    private LogErroresRepository logErroresRepository;
    @PersistenceContext
    private EntityManager manager;

    @Scheduled(cron = " 40 59 17 * * *")
    public void tasks(){
        syncDatabases();
        checkFechaVencimiento();
    }

    public void syncDatabases(){
        List<Member> memberpg = new LinkedList<>();
        List<MemberDTO> members = memberService.list();
        if (members == null)
        {
            Date now = new Date();
            String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(now);
            logErroresRepository.save(new LogErrores(fecha,
                    "No se pudo obtener la lista de miembros almacenada en Firestore"));
            return;
        }
        for (MemberDTO member : members){
            memberpg.add(membersPg(member));
        }
        membersRepository.saveAll(memberpg);
    }
    void checkFechaVencimiento() {
            membersRepository.deleteFecha(LocalDate.now().toString());
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
        member.setPhoto(memberDTO.getPhoto());
        member.setSurname(memberDTO.getSurname());
        member.setType(memberDTO.getType());
        member.setFechaVencimiento(memberDTO.getFecha_vencimiento());
        return member;
    }
}
