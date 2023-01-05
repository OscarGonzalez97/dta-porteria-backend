package com.roshka.dtaporteria.Controller;
import com.roshka.dtaporteria.Service.MembersService;
import org.springframework.web.bind.annotation.*;
import com.roshka.dtaporteria.Model.Members;

import java.util.List;
import java.util.concurrent.ExecutionException;
@RestController
public class MembersController {

    public MembersService membersService;
    public MembersController(MembersService membersService){
        this.membersService = membersService;
    }

    @PostMapping("/createMembers")
    public String createMEMBERS(@RequestBody Members members) throws InterruptedException, ExecutionException {
        return membersService.createMEMBERS(members);
    }
    @GetMapping("/getMembers")
    public Members getMEMBERS(@RequestParam String documentID) throws InterruptedException, ExecutionException {
        return membersService.getMEMBERS(documentID);
    }
    @GetMapping("/getAllMembers")
    public List<Members> getAllMEMBERS() throws InterruptedException, ExecutionException {
        return membersService.getAllMEMBERS();
    }
    @PutMapping("/updateMembers")
    public String updateMEMBERS(@RequestBody Members members) throws InterruptedException, ExecutionException {
        return membersService.updateMEMBERS(members);
    }
    @DeleteMapping("/deleteMembers")
    public String deleteMEMBERS(@RequestParam String documentID) throws InterruptedException, ExecutionException {
        return membersService.deleteMEMBERS(documentID);
    }
}
