package com.roshka.dtaporteria.controller;

import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.reporte.ImportMembersExcel;
import com.roshka.dtaporteria.service.MemberService;
import com.roshka.dtaporteria.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService service;
    @Autowired
    private TypeService typeService;
    @Autowired
    private ImportMembersExcel importMembersExcel;
    @GetMapping
    public String Miembros(Model model) {
        List<MemberDTO> miembros = service.list();
        model.addAttribute("miembros", miembros);
        model.addAttribute("tipos", typeService.list());
        return "members";
    }
    @GetMapping("/add-form")
    public String addForm(Model model){
        model.addAttribute("tipos", typeService.list());
        model.addAttribute("member", new MemberDTO());
        return "newMember";
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
        return "newMember";
    }

    @GetMapping("/import")
    public String getMiembroImportarExcel(){
        return "importMiembroExcel";
    }
    @PostMapping("/import")
    public String cargarMiembrosExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirAttr) throws IOException {
        List<String> err= importMembersExcel.validateExcel(file);
        if (!err.isEmpty()){
            redirAttr.addFlashAttribute("err", err);
            return "redirect:/members/import?error";
        }
        List<MemberDTO> miembros = importMembersExcel.obtenerMiembros(file);
        service.AddMembersByList(miembros);
        return "redirect:/members";
    }

    @PostMapping("/add")
    public ResponseEntity agregarMember(@RequestBody MemberDTO post){
        return new ResponseEntity(service.add(post), HttpStatus.OK);
    }
    @PostMapping("/add-form")
    public String agregarFormMember(MemberDTO member) {
        if (!Objects.equals(member.getType(), "Socio"))
        {
            member.setId_member(null);
        }
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
        return "updateMember";
    }
    @PutMapping("/{id}/update")
    public ResponseEntity editMember(@PathVariable(value = "id") String id, @RequestBody MemberDTO post){
        return new ResponseEntity(service.edit(id, post), HttpStatus.OK);
    }
    @PostMapping ("/update")
    public String updateMember(MemberDTO member){
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
