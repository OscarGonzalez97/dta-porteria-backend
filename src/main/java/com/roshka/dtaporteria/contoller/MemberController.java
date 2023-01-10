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
        return "listmembers";
    }
    @GetMapping("/add-form")
    public String addForm(Model model){
        model.addAttribute("tipos", typeService.list());
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
            if ((member.getId_member() == null)
            || (member.getCreated_by() == "")
            || (member.getFecha_vencimiento() == "" && !Objects.equals(member.getType(), "Socio"))
            || (member.getName() == "")
            || (member.getSurname() == "")
            || (member.getPhoto() == "")
            || (member.getType() == "")
            || (member.getIs_defaulter() == ""))
            {
                return "redirect:/members/add-form?error001";}
            if (service.getById(member.getId_member())){
                return "redirect:/members/add-form?error002";}
            System.out.println(member);
        new ResponseEntity(service.add(member), HttpStatus.OK);
        return "redirect:/members";
    }

    @GetMapping("/{id_member}/form-update")
    public String editMember(@PathVariable(value = "id_member") String id_member, Model model){
        model.addAttribute("member", service.getById(id_member));
        return "miembro-update";
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
