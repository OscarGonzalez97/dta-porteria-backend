package com.roshka.dtaporteria.Controller;
import com.roshka.dtaporteria.Model.Users;
import com.roshka.dtaporteria.Service.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class UsersController {

    public UsersService usersService;
    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping("/createUsers")
    public String createUSERS(@RequestBody Users users, @RequestParam String email) throws InterruptedException, ExecutionException {
        return usersService.createUSERS(users, email);
    }
    @GetMapping("/getUsers")
    public Users getUSERS(@RequestParam String documentID) throws InterruptedException, ExecutionException {
        return usersService.getUSERS(documentID);
    }
    @GetMapping("/getAllUsers")
    public List<Users> getAllUSERS() throws InterruptedException, ExecutionException {
        return usersService.getAllUSERS();
    }
    @PutMapping("/updateUsers")
    public String updateUSERS(@RequestBody Users users, @RequestParam String email) throws InterruptedException, ExecutionException {
        return usersService.updateUSERS(users, email);
    }
    @DeleteMapping("/deleteUsers")
    public String deleteUSERS(@RequestParam String documentID) throws InterruptedException, ExecutionException {
        return usersService.deleteUSERS(documentID);
    }
}
