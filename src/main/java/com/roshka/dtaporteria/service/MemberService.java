package com.roshka.dtaporteria.service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.roshka.dtaporteria.config.FirebaseInitializer;
import com.roshka.dtaporteria.dto.MemberDTO;

import com.roshka.dtaporteria.model.Member;
import com.roshka.dtaporteria.repository.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class MemberService {

    @Autowired
    private FirebaseInitializer firebase;
    @Autowired
    private MembersRepository membersRepository;

    public List<Member> listMemberPg(){return membersRepository.findAll();}

    public Boolean present(String id){
        Optional<Member> member = membersRepository.findById(id);
        return member.isPresent();
    }

    public MemberDTO getById(String id) {
        DocumentReference docRef = getCollection().document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                return document.toObject(MemberDTO.class);
            }
            return null;
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }
    public Boolean getByIdmember(String id) {
        ApiFuture<QuerySnapshot> future = getCollection().whereEqualTo("id_member", id).get();
        try {
            QuerySnapshot document = future.get();
            if (document.getDocuments().isEmpty()) {
                return false;
            }
            return true;
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }
    public int[] dataGraficoPie(){
        List<Member> docs = membersRepository.findAll();
        int[] members={0,0,0,0,0,0,0,0,0};  //Iniciamos el array con 9 datos que serian los tipos
        try {
            for (Member doc : docs) {
                String tipo_miembro = doc.getType();  //aqui obtnemos la fecha exacta en milisegundos
                //getMonthofdatetime nos traera el mes de la fecha que esta en milisegundo
                arrayMiembros(tipo_miembro,members);
            }
            return members;
        } catch (Exception e) {
            return null;
        }
    }
    public List<MemberDTO> list() {
        List<MemberDTO> response = new ArrayList<>();
        MemberDTO post;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(MemberDTO.class);
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Transactional(rollbackFor={ExecutionException.class, InterruptedException.class})
    public void add(MemberDTO post) throws ExecutionException, InterruptedException {
        Map<String, Object> docData = getDocData(post);
        CollectionReference posts = getCollection();
        String id = posts.document().getId();
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(id).set(docData);
        membersRepository.save(new Member(id, post.getCreated_by(),
                post.getCi(),
                post.getId_member(),
                post.getIs_defaulter(),
                post.getName(),
                post.getPhoto(),
                post.getSurname(),
                post.getType(),
                post.getFecha_vencimiento()));
        writeResultApiFuture.get();
    }


    @Transactional(rollbackFor={ExecutionException.class, InterruptedException.class})
    public void update(MemberDTO member) throws ExecutionException, InterruptedException {
        Map<String, Object> docData = getDocData(member);
        docData.values().remove(Collections.singleton(null));
        CollectionReference posts = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(member.getId()).update(docData);
        String id = member.getId();
        Optional<Member> present = membersRepository.findById(id);
        if(!present.isPresent()){
            return;
        }
        Member memberPg = present.get();
        memberPg.setCreatedBy(member.getCreated_by());
        memberPg.setCi(member.getCi());
        memberPg.setIdMember(member.getId_member());
        memberPg.setIsDefaulter(member.getIs_defaulter());
        memberPg.setName(member.getName());
        memberPg.setPhoto("");
        memberPg.setSurname(member.getSurname());
        memberPg.setType(member.getType());
        memberPg.setFechaVencimiento(member.getFecha_vencimiento());
        membersRepository.save(memberPg);
        writeResultApiFuture.get();
    }

    @Transactional(rollbackFor={ExecutionException.class, InterruptedException.class})
    public String delete(String id) throws ExecutionException, InterruptedException {
        CollectionReference posts = getCollection();
        if (id == null || !membersRepository.findById(id).isPresent()) return "?err002";
        membersRepository.deleteById(id);
        ApiFuture<WriteResult> writeResultApiFuture = posts.document(id).delete();
        writeResultApiFuture.get();
        return "?deleteSuccess";
    }

    private static Map<String, Object> getDocData(MemberDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("created_by", post.getCreated_by());
        docData.put("id_member", post.getId_member());
        docData.put("ci", post.getCi());
        docData.put("is_defaulter", post.getIs_defaulter());
        docData.put("name", post.getName());
        docData.put("photo", post.getPhoto());
        docData.put("surname", post.getSurname());
        docData.put("type", post.getType());
        docData.put("fecha_vencimiento", post.getFecha_vencimiento());
        return docData;
    }
    public int dataTarjetaM(){
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        int canti_defaulter=0;
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                String tipo_miembro = String.valueOf(doc.get("is_defaulter"));
                if (tipo_miembro.equals("Si")){
                    canti_defaulter++;
                }
            }
            return canti_defaulter;
        } catch (Exception e) {
            return -99;
        }
    }
    private void arrayMiembros(String tipo,int[] arrayMembers){
        switch (tipo) {
            case "Socio":
                arrayMembers[0] += 1;
                break;
            case "Staff":
                arrayMembers[1] += 1;
                break;
            case "Guarderia":
                arrayMembers[2] += 1;
                break;
            case "Piscina":
                arrayMembers[3] += 1;
                break;
            case "Tenis":
                arrayMembers[4] += 1;
                break;
            case "Restaurante":
                arrayMembers[5] += 1;
                break;
            case "Gimnasio":
                arrayMembers[6] += 1;
                break;
            case "Eventos":
                arrayMembers[7] += 1;
                break;
            case "Invitado":
                arrayMembers[8] += 1;
                break;
        }
    }
    @Transactional(rollbackFor={ExecutionException.class, InterruptedException.class})
    public void AddMembersByList(List<MemberDTO> miembros) throws ExecutionException, InterruptedException {
        Firestore db = getCollection().getFirestore();
        WriteBatch batch = db.batch();
        List<Member> lista = new LinkedList<>();
        for(MemberDTO miembro : miembros){
            String id = db.collection("MEMBERS").document().getId();
            System.out.println(id);
            lista.add(new Member(id, miembro.getCreated_by(),
                    miembro.getCi(),
                    miembro.getId_member(),
                    miembro.getIs_defaulter(),
                    miembro.getName(),
                    miembro.getPhoto(),
                    miembro.getSurname(),
                    miembro.getType(),
                    miembro.getFecha_vencimiento()));

            DocumentReference memberReference = db.collection("MEMBERS").document(id);
            batch.set(memberReference, miembro);
        }
        membersRepository.saveAll(lista);
        ApiFuture<List<WriteResult>> resultbatch = batch.commit();
        resultbatch.get();

    }
    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("MEMBERS");
    }
}
