package com.roshka.dtaporteria.Controller;
import com.roshka.dtaporteria.Service.MEMBERSService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.roshka.dtaporteria.CRUD.MEMBERS;
import java.util.concurrent.ExecutionException;
@RestController
public class MEMBERSController {

    public MEMBERSService membersService;
    public MEMBERSController(MEMBERSService membersService){
        this.membersService = membersService;
    }

    @PostMapping("/create")
    public String createMEMBERS(@RequestBody MEMBERS members) throws InterruptedException, ExecutionException {
        return membersService.createMEMBERS(members);
    }
    @GetMapping("/get")
    public MEMBERS getMEMBERS(@RequestParam String documentID) throws InterruptedException, ExecutionException {
        return membersService.getMEMBERS(documentID);
    }
    @PutMapping("/update")
    public String updateMEMBERS(@RequestBody MEMBERS members) throws InterruptedException, ExecutionException {
        return membersService.updateMEMBERS(members);
    }
    @DeleteMapping("/delete")
    public String deleteMEMBERS(@RequestParam String documentID) throws InterruptedException, ExecutionException {
        return membersService.deleteMEMBERS(documentID);
    }
    @GetMapping("/test")
    public ResponseEntity<String> testGetEndpoint(){return ResponseEntity.ok("Test done!");}
}
