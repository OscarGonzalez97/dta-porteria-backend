package com.roshka.dtaporteria.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@Entity
@Data
@NoArgsConstructor
public class Records {
    @Id
    private String id;
    private String ci_member;
    private String ci_portero;
    private String date_time; //fecha y hora exacta
    private String email_portero;
    private String id_member;
    private String is_defaulter;
    private Boolean is_exit;
    private Boolean is_walk;
    private String name_member;
    private String name_portero;
    private String photo;
    private String surname_member;
    private String surname_portero;
    private String type;
    private String sector;

    public Records(String id, String ci_member, String ci_portero, String date_time, String email_portero, String id_member, String is_defaulter, Boolean is_exit, Boolean is_walk, String name_member, String name_portero, String photo, String surname_member, String surname_portero, String type, String sector) {
        this.id = id;
        this.ci_member = ci_member;
        this.ci_portero = ci_portero;
        this.date_time = date_time;
        this.email_portero = email_portero;
        this.id_member = id_member;
        this.is_defaulter = is_defaulter;
        this.is_exit = is_exit;
        this.is_walk = is_walk;
        this.name_member = name_member;
        this.name_portero = name_portero;
        this.photo = photo;
        this.surname_member = surname_member;
        this.surname_portero = surname_portero;
        this.type = type;
        this.sector = sector;
    }


    public String CambiarFormatoFecha(){ //formato para fecha y hora

        long fechaL= Long.parseLong(this.date_time.substring(0,10)); //extrae los numeros necesarios para que sea fecha y hora

        Date Fecha = new Date(fechaL*1000L);

        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jdf.setTimeZone(TimeZone.getDefault()); //define la zona horaria

        String java_date = jdf.format(Fecha);

        return java_date;
        //java_date.substring(0,10) para traer solo la fecha
    }

    public String ExtraerHora(){ //formato para fecha y hora

        long fechaL= Long.parseLong(this.date_time.substring(0,10)); //extrae los numeros necesarios para que sea fecha y hora

        Date Fecha = new Date(fechaL*1000L);

        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jdf.setTimeZone(TimeZone.getDefault()); //define la zona horaria

        String java_date = jdf.format(Fecha);

        return java_date.substring(11,19);
        //java_date.substring(0,10) para traer solo la fecha
    }
    public String CambiarFormatoFecha2(){ //formato para fecha

        long fechaL= Long.parseLong(this.date_time.substring(0,10)); //extrae los numeros necesarios para que sea fecha y hora

        Date Fecha = new Date(fechaL*1000L);

        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd");
        jdf.setTimeZone(TimeZone.getDefault()); //define la zona horaria

        String java_date = jdf.format(Fecha);

        return java_date;
    }
    public String fotoDefault () { //para poner una foto por defecto a las que no tienen

        if (photo == "") {
            return "https://firebasestorage.googleapis.com/v0/b/porteria-dta-test.appspot.com/o/images%2Fdefault.jpg?alt=media&token=61c94dfb-d197-4e95-b5ec-552354635679";
            //return "https://simulacionymedicina.es/wp-content/uploads/2015/11/default-avatar-300x300-1.jpg";
        }
        else {
            return photo;
        }
    }
    public String CambiarIsDefaulter() {
        if (is_defaulter.toLowerCase()=="true") {
            return "Si";
        }
        else {
            return "No";
        }
    }
    public String CambiarIsExit() {
        if (is_exit) {
            return "Si";
        }
        else {
            return "No";
        }
    }
    public String CambiarIsWalk () {
        if (is_walk) {
            return "Si";
        }
        else {
            return "No";
        }
    }
    public String ConcatNameMember () {
        return name_member+ " " +surname_member;
    }
    public String ConcatNamePortero () {
        return name_portero+ " " +surname_portero;
    }

}
