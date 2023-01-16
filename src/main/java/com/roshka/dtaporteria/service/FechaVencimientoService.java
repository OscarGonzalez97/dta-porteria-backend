package com.roshka.dtaporteria.service;
import com.roshka.dtaporteria.dto.MemberDTO;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class FechaVencimientoService {
    private final MemberService memberService;

    public FechaVencimientoService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkFecha_Vencimiento(){
        List<MemberDTO>members = memberService.list();
        Map<String, MemberDTO> fecha_vencimiento = members.stream().collect(Collectors.toMap(MemberDTO::getId, Function.identity()));

        fecha_vencimiento.forEach(
                (key, value) -> {
                    if (!Objects.equals(value.getType(), "Socio") && !isAfterCurrentDate(value.getFecha_vencimiento())){
                        memberService.delete(key);
                    }
                }
        );
    }
    public Boolean isAfterCurrentDate(String fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(fecha, formatter);
        return ld.isAfter(LocalDate.now());
    }
}
