package com.roshka.dtaporteria.Controller;
import com.roshka.dtaporteria.Model.USERS;
import com.roshka.dtaporteria.Service.USERSService;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ExecutionException;

@RestController
public class USERSController {

    public USERSService usersService;
    public USERSController(USERSService usersService){
        this.usersService = usersService;
    }

    @PostMapping("/createUsers")
    public String createUSERS(@RequestBody USERS users, @RequestParam String email) throws InterruptedException, ExecutionException {
        return usersService.createUSERS(users, email);
    }
    @GetMapping("/getUsers")
    public USERS getUSERS(@RequestParam String documentID) throws InterruptedException, ExecutionException {
        return usersService.getUSERS(documentID);
    }
    @PutMapping("/updateUsers")
    public String updateUSERS(@RequestBody USERS users, @RequestParam String email) throws InterruptedException, ExecutionException {
        return usersService.updateUSERS(users, email);
    }
    @DeleteMapping("/deleteUsers")
    public String deleteUSERS(@RequestParam String documentID) throws InterruptedException, ExecutionException {
        return usersService.deleteUSERS(documentID);
    }
}
