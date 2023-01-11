package com.roshka.dtaporteria.contoller;

import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.service.MemberService;
import com.roshka.dtaporteria.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService service;
    @Autowired
    private TypeService typeService;

    @GetMapping
    public String Miembros(Model model) {
        List<MemberDTO> miembros = service.list();
        model.addAttribute("miembros", miembros);
        model.addAttribute("tipos", typeService.list());
        return "listmembers";
    }
    @GetMapping("/add-form")
    public String addForm(Model model){
        model.addAttribute("tipos", typeService.list());
        model.addAttribute("member", new MemberDTO());
        return "formulario-miembro";
    }
    @GetMapping("/{id}")
    public ResponseEntity getMember(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.getById(id), HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity listMember(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }

    @GetMapping("/add")
    public String getMiembroFormulario(){
        return "formulario-miembro";
    }

    @PostMapping("/add")
    public ResponseEntity agregarMember(@RequestBody MemberDTO post){
        return new ResponseEntity(service.add(post), HttpStatus.OK);
    }
    @PostMapping("/add-form")
    public String agregarFormMember(MemberDTO member){
        new ResponseEntity(service.add(member), HttpStatus.OK);
        return "redirect:/members";
    }

    @GetMapping("/update/{id}")
    public String getEditMember(@PathVariable(value = "id", required=true) String id, Model model){
        MemberDTO member = service.getById(id);
        if (member==null){
            return "redirect:/members";
        }
        model.addAttribute("m", member);
        model.addAttribute("tipos", typeService.list());
        return "miembro-update";
    }
    @PutMapping("/{id}/update")
    public ResponseEntity editMember(@PathVariable(value = "id") String id, @RequestBody MemberDTO post){
        return new ResponseEntity(service.edit(id, post), HttpStatus.OK);
    }
    @PostMapping ("/update")
    public String updateMember(MemberDTO member){
        member.setId_member(Integer.parseInt(member.getId()));
        if (service.someAttributeIsNull(member)){
            return "redirect:/members/update/"+member.getId()+"?error001";}
        service.update(member);
        return "redirect:/members";
    }

    @PostMapping("/delete/{id}")
    public String deleteMember(@PathVariable(value = "id") String id){
        new ResponseEntity(service.delete(id), HttpStatus.OK);
        return "redirect:/members";
    }
    @GetMapping("/delete/{id}")
    public String getDeleteMember(@PathVariable(value = "id") String id){
        new ResponseEntity(service.delete(id), HttpStatus.OK);
        return "redirect:/members";
    }
}
