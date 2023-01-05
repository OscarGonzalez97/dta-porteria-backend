package com.roshka.dtaporteria.Controller;
import com.roshka.dtaporteria.Service.MEMBERSService;
import org.springframework.web.bind.annotation.*;
import com.roshka.dtaporteria.Model.MEMBERS;

import java.util.List;
import java.util.concurrent.ExecutionException;
@RestController
public class MEMBERSController {

    public MEMBERSService membersService;

    @PostMapping("/createMembers")
    public String createMEMBERS(@RequestBody MEMBERS members) throws InterruptedException, ExecutionException {
        return membersService.createMEMBERS(members);
    }
    @GetMapping("/getMembers")
    public MEMBERS getMEMBERS(@RequestParam String documentID) throws InterruptedException, ExecutionException {
        return membersService.getMEMBERS(documentID);
    }
    @GetMapping("/getAllMembers")
    public List<MEMBERS> getAllMEMBERS() throws InterruptedException, ExecutionException {
        return membersService.getAllMEMBERS();
    }
    @PutMapping("/updateMembers")
    public String updateMEMBERS(@RequestBody MEMBERS members) throws InterruptedException, ExecutionException {
        return membersService.updateMEMBERS(members);
    }
    @DeleteMapping("/deleteMembers")
    public String deleteMEMBERS(@RequestParam String documentID) throws InterruptedException, ExecutionException {
        return membersService.deleteMEMBERS(documentID);
    }
}
