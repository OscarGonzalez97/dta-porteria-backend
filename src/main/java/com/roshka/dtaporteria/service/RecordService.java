package com.roshka.dtaporteria.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.roshka.dtaporteria.config.FirebaseInitializer;
import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.dto.RecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class RecordService {
    @Autowired
    private FirebaseInitializer firebase;

    public RecordDTO getById(String id) {
        DocumentReference docRef = getCollection().document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()){
                RecordDTO record = document.toObject(RecordDTO.class);
                return record;
            }
            return null;
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public List<RecordDTO> list(){
        List<RecordDTO> response = new ArrayList<>();
        RecordDTO post;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(RecordDTO.class);
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }
    public int[] dataGraficoLinea(int ano){
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        int[] mesArray={0,0,0,0,0,0,0,0,0,0,0,0};  //Iniciamos el array con 12 datos que serian los meses
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                Long fecha_ingreso = Long.parseLong(String.valueOf(doc.get("date_time")));  //aqui obtnemos la fecha exacta en milisegundos
                int mes=getMonthofdatetime(fecha_ingreso,ano);//getMonthofdatetime nos traera el mes de la fecha que esta en milisegundo
                arraymes(mes,mesArray);
            }
            return mesArray;
        } catch (Exception e) {
            return null;
        }
    }
    public int getMonthofdatetime(Long l,int ano ){  //para el grafico mes a mes :)

        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(l);
        date.setTimeZone(TimeZone.getDefault());


        if(date.get(Calendar.YEAR)==ano){
            return date.get(Calendar.MONTH);
        }

//        System.out.println(new SimpleDateFormat().format(date));
        return 99;
    }
//    private int obtenerUsuarioDia(){
//        Date datee = new Date();
//        DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
//
//        SimpleDateFormat dateFormatt = new SimpleDateFormat("dd-MM-yyyy");
//        dateFormat.setTimeZone(date.getTimeZone());
//        if(dateFormatt.format(date.getTime())==dateFormat.format(datee)){
//            mes_cantip[1]+=1;
//        }
//    }
    private void arraymes(int mes,int[] mesArray){
        switch (mes) {
            case 99:
                break;
            case 0:
                mesArray[0] += 1;
                break;
            case 1:
                mesArray[1] += 1;
                break;
            case 2:
                mesArray[2] += 1;
                break;
            case 3:
                mesArray[3] += 1;
                break;
            case 4:
                mesArray[4] += 1;
                break;
            case 5:
                mesArray[5] += 1;
                break;
            case 6:
                mesArray[6] += 1;
                break;
            case 7:
                mesArray[7] += 1;
                break;
            case 8:
                mesArray[8] += 1;
                break;
            case 9:
                mesArray[9] += 1;
                break;
            case 10:
                mesArray[10] += 1;
                break;
            case 11:
                mesArray[11] += 1;
                break;

        }
    }
    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("RECORDS");
    }
}
