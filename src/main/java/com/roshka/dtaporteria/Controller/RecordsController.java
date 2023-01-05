package com.roshka.dtaporteria.Controller;
import com.roshka.dtaporteria.Model.Records;
import com.roshka.dtaporteria.Service.RecordsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class RecordsController {

    public RecordsService recordsService;

    public RecordsController(RecordsService recordsService) {
        this.recordsService = recordsService;
    }

    @PostMapping("/createRecord")
    public String createRECORDS(@RequestBody Records records) throws InterruptedException, ExecutionException {
        return recordsService.createRECORDS(records);
    }

    @GetMapping("/getRecord")
    public Records getRECORDS(@RequestParam String documentID) throws InterruptedException, ExecutionException {
        return recordsService.getRECORDS(documentID);
    }

    @GetMapping("/getAllRecords")
    public List<Records> getAllRECORDS() throws InterruptedException, ExecutionException {
        return recordsService.getAllRECORDS();
    }
}
