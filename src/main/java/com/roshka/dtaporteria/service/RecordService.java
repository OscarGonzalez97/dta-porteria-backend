package com.roshka.dtaporteria.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.roshka.dtaporteria.config.FirebaseInitializer;
import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.dto.RecordDTO;
import com.roshka.dtaporteria.model.Records;

import com.roshka.dtaporteria.repository.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class RecordService {
    @Autowired
    private FirebaseInitializer firebase;
    @Autowired
    private RecordsRepository recordsRepository;

    public List<RecordDTO> listToSync(){
        List<RecordDTO> response = new LinkedList<>();
        RecordDTO post;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().whereGreaterThanOrEqualTo("date_time", String.valueOf(Instant.now().minusSeconds(86400).toEpochMilli())).get();
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

    public RecordDTO getById(String id) { //metodo para obtener un record por su id
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

    public List<RecordDTO> list(){ //metodo para listar todos los records
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
        List<Records> docs = recordsRepository.findAll();
        int[] mesArray={0,0,0,0,0,0,0,0,0,0,0,0,0};  //Iniciamos el array con 13 datos. los primeros 12, son los meses y el ultimo es la
        int canti_perso=0;                           // cantidad de personas que ingresaron ese mismo dia. (Por cuestion de rapidez se hizo asi.)
        try {
            for (Records doc : docs) {
                Long fecha_ingreso = Long.parseLong(String.valueOf(doc.getDate_time()));  //aqui obtnemos la fecha exacta en milisegundos
                int mes=getMonthofdatetime(fecha_ingreso,ano,canti_perso);//getMonthofdatetime nos traera el mes de la fecha que esta en milisegundo
                arraymes(mes,mesArray);
            }
            mesArray[12]=canti_perso;
            return mesArray;
        } catch (Exception e) {
            return new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        }
    }
    public int getMonthofdatetime(Long l,int ano,int persona_dia){  //para el grafico mes a mes :)
        Calendar date = new GregorianCalendar();  //fecha que nos da la BBDD
        date.setTimeInMillis(l);
        date.setTimeZone(TimeZone.getDefault());
        SimpleDateFormat dateFormatt = new SimpleDateFormat("dd-MM-yyyy");   //formateamos la fecha

        Date datee = new Date();  //fecha actual
        DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");  //formateamos la fecha

        if(date.get(Calendar.YEAR)==ano){

            if(dateFormatt.format(date.getTime()).equals(dateFormat.format(datee))){  //si las fechar formateadas son iguales suma 1 persona
                persona_dia++;                                                  // que ingreso ese dia.
            }
            return date.get(Calendar.MONTH);
        }
        return 99;
    }
    private void arraymes(int mes,int[] mesArray){
        switch (mes) {
            case 99:  //por si hay error (esta controlado que sea 99)
                break;
            case 0:  //Enero
                mesArray[0] += 1;
                break;
            case 1:   //Feb
                mesArray[1] += 1;
                break;
            case 2:   //Mar
                mesArray[2] += 1;
                break;
            case 3:   //Abril
                mesArray[3] += 1;
                break;
            case 4:   //Mayo
                mesArray[4] += 1;
                break;
            case 5:   //Junio
                mesArray[5] += 1;
                break;
            case 6:   //Julio
                mesArray[6] += 1;
                break;
            case 7:  //Agos
                mesArray[7] += 1;
                break;
            case 8:  //SEP
                mesArray[8] += 1;
                break;
            case 9:  //Oct
                mesArray[9] += 1;
                break;
            case 10:  //Nov
                mesArray[10] += 1;
                break;
            case 11:  //Dec
                mesArray[11] += 1;
                break;
        }
    }
    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("RECORDS");
    } //coleccion de records
}
