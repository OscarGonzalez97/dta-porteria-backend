package com.roshka.dtaporteria.Utils;
import com.roshka.dtaporteria.Model.MEMBERS;
import com.roshka.dtaporteria.Model.RECORDS;
import com.roshka.dtaporteria.Model.USERS;
import java.util.LinkedList;
import java.util.List;
public class Update {
    public static List<String> getUsers(USERS user){
        List<String> lista = new LinkedList<>();
        if(user.getActive()!=null)
            lista.add("active");
        if(user.getCi()!=null)
            lista.add("ci");
        if(user.getSurname()!=null)
            lista.add("surname");
        if(user.getRol()!=null)
            lista.add("rol");
        if(user.getName()!=null)
            lista.add("name");
        return lista;
        }
    public static List<String> getMembers(MEMBERS members){
        List<String> lista = new LinkedList<>();
        if(members.getCreated_by()!=null)
            lista.add("created_by");
        if(members.getId_member()!=null)
            lista.add("id_member");
        if(members.getIs_defaulter()!=null)
            lista.add("is_defaulter");
        if(members.getPhoto()!=null)
            lista.add("photo");
        if(members.getName()!=null)
            lista.add("name");
        if(members.getSurname()!=null){
            lista.add("surname");
        }
        if(members.getType()!=null){
            lista.add("type");
        }
        return lista;
    }
    public static List<String> getRecords(RECORDS records){
        List<String> lista = new LinkedList<>();
        if(records.getCi_member()!=null)
            lista.add("ci_member");
        if(records.getCi_portero()!=null)
            lista.add("ci_portero");
        if(records.getDate_time()!=null)
            lista.add("date_time");
        if(records.getEmail_portero()!=null)
            lista.add("email_portero");
        if(records.getId_member()!=null)
            lista.add("id_member");
        if(records.getIs_defaulter()!=null){
            lista.add("is_defaulter");
        }
        if(records.getIs_exit()!=null){
            lista.add("is_exit");
        }
        if(records.getIs_walk()!=null){
            lista.add("is_walk");
        }
        if(records.getName_member()!=null){
            lista.add("name_member");
        }
        if(records.getName_portero()!=null){
            lista.add("name_portero");
        }
        if(records.getPhoto()!=null){
            lista.add("photo");
        }
        if(records.getSurname_member()!=null){
            lista.add("surname_member");
        }
        if(records.getSurname_poretero()!=null){
            lista.add("surname_portero");
        }
        if(records.getType()!=null){
            lista.add("type");
        }
        return lista;
    }

}
