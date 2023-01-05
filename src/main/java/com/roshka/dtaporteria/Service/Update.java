package com.roshka.dtaporteria.Service;
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
}
