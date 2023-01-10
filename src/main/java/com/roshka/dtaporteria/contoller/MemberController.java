package com.roshka.dtaporteria.contoller;

import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService service;

    @GetMapping
    public String Miembros(Model model) {
        List<MemberDTO> miembros = service.list();
        model.addAttribute("miembros", miembros);
        return "listmembers";
    }
    @GetMapping("/add-form")
    public String addForm(){
        return "formulario-miembro";
    }
    @GetMapping("/{id}")
    public ResponseEntity getMember(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.getById(id), HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }

    @GetMapping("/add")
    public String getMiembroFormulario(){
        return "formulario-miembro";
    }

    @PostMapping("/add")
    public ResponseEntity agregar(@RequestBody MemberDTO post){
        return new ResponseEntity(service.add(post), HttpStatus.OK);
    }
    @PostMapping("/add-form")
    public String agregarForm(@ModelAttribute("addMember") MemberDTO member){
            if (member.getId_member() == null)
                return "redirect:/members/add-form?error001";
            if (service.getById(member.getId_member()))
                return "redirect:/members/add-form?error002";
        new ResponseEntity(service.add(member), HttpStatus.OK);
        return "redirect:/members";
    }

    @PutMapping("/{id}/update")
    public ResponseEntity edit(@PathVariable(value = "id") String id, @RequestBody MemberDTO post){
        return new ResponseEntity(service.edit(id, post), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity delete(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }

}
