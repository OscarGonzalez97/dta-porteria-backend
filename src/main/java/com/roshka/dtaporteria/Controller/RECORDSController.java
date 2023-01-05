package com.roshka.dtaporteria.Controller;
import com.roshka.dtaporteria.Model.MEMBERS;
import com.roshka.dtaporteria.Model.RECORDS;
import com.roshka.dtaporteria.Service.RECORDSService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class RECORDSController {

    public RECORDSService recordsService;

    @PostMapping("/createRecord")
    public String createRECORDS(@RequestBody RECORDS records) throws InterruptedException, ExecutionException {
        return recordsService.createRECORDS(records);
    }
    @GetMapping("/getRecord")
    public RECORDS getRECORDS(@RequestParam String documentID) throws InterruptedException, ExecutionException {
        return recordsService.getRECORDS(documentID);
    }
    @GetMapping("/getAllRecords")
    public List<RECORDS> getAllRECORDS() throws InterruptedException, ExecutionException {
        return recordsService.getAllRECORDS();
    }
    @DeleteMapping("/deleteRecord")
    public String deleteRECORDS(@RequestParam String documentID) throws InterruptedException, ExecutionException {
        return recordsService.deleteRECORDS(documentID);
    }
}
