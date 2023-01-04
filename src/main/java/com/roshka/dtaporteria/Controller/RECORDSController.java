package com.roshka.dtaporteria.Controller;
import com.roshka.dtaporteria.CRUD.RECORDS;
import com.roshka.dtaporteria.Service.RECORDSService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ExecutionException;

@RestController
public class RECORDSController {

    public RECORDSService recordsService;
    public RECORDSController(RECORDSService recordsService){
        this.recordsService = recordsService;
    }

    @PostMapping("/createRecord")
    public String createRECORDS(@RequestBody RECORDS records) throws InterruptedException, ExecutionException {
        return recordsService.createRECORDS(records);
    }
    @GetMapping("/getRecord")
    public RECORDS getRECORDS(@RequestParam String documentID) throws InterruptedException, ExecutionException {
        return recordsService.getRECORDS(documentID);
    }
    @PutMapping("/updateRecord")
    public String updateRECORDS(@RequestBody RECORDS records) throws InterruptedException, ExecutionException {
        return recordsService.updateRECORDS(records);
    }
    @DeleteMapping("/deleteRecord")
    public String deleteRECORDS(@RequestParam String documentID) throws InterruptedException, ExecutionException {
        return recordsService.deleteRECORDS(documentID);
    }
}
