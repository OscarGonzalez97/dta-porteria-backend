package com.roshka.dtaporteria.controller;
import com.roshka.dtaporteria.config.UserRecordCustom;
import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.service.HistorialService;
import com.roshka.dtaporteria.service.ImportMembersExcelService;
import com.roshka.dtaporteria.service.MemberService;
import com.roshka.dtaporteria.service.TypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService service;
    @Autowired
    private TypeService typeService;
    @Autowired
    private ImportMembersExcelService importMembersExcelService;

    @Autowired
    private HistorialService historialService;

    @GetMapping
    public String Miembros(Model model) {
        List<MemberDTO> miembros = service.list();
        model.addAttribute("miembros", miembros);
        model.addAttribute("tipos", typeService.list());
        return "members";
    }


    @GetMapping("/historial/{id}")
    public String showHistorial(Model model , @PathVariable(value = "id") String id){
        List<Map<String,Object>> miembros = historialService.list(id);
        System.out.println(miembros);
        model.addAttribute("historia", miembros);
        return "historial";
    }
    @GetMapping("/add-form")
    public String addForm(Model model){
        model.addAttribute("tipos", typeService.list());
        model.addAttribute("member", new MemberDTO());
        return "newMember";
    }

    @GetMapping("/list")
    public List<MemberDTO> listMember(){
        return service.list();
    }

    // @GetMapping("/add")
    // public String getMiembroFormulario(){
    //     return "newMember";
    // }

    @GetMapping("/import")
    public String getMiembroImportarExcel(){
        return "importMiembroExcel";
    }

    @PostMapping("/import")
    public String cargarMiembrosExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirAttr) throws IOException {
        if (file.isEmpty()){
            redirAttr.addFlashAttribute("err", "No se ha elegido ningun archivo");
            return "redirect:/members/import?error";
        }
        List<String> err= importMembersExcelService.validarExcel(file);
        if (!err.isEmpty()){
            redirAttr.addFlashAttribute("err", err);
            return "redirect:/members/import?error";
        }
        List<MemberDTO> miembros = importMembersExcelService.obtenerMiembros(file);
        service.AddMembersByList(miembros);
        return "redirect:/members?importSuccess";
    }

    @PostMapping("/add-form")
    public String agregarFormMember(MemberDTO member) {
        UserRecordCustom usr = (UserRecordCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        member.setCreated_by(usr.getPassword());
        if (!Objects.equals(member.getType(), "Socio"))
        {
            member.setId_member(null);
        }
        if (service.getByIdmember(member.getId_member()) && member.getId_member() != null)
        {
            return "redirect:/members/add-form?error002";
        }
        service.add(member);
        return "redirect:/members?createSuccess";
    }

    @GetMapping("/update/{id}")
    public String getEditMember(@PathVariable(value = "id") String id, Model model){
        MemberDTO member = service.getById(id);
        if (member==null){
            return "redirect:/members?err002";
        }
        model.addAttribute("m", member);
        model.addAttribute("tipos", typeService.list());
        return "updateMember";
    }

    @PostMapping ("/update")
    public String updateMember(MemberDTO member){
        MemberDTO memberDTO = service.getById(member.getId());
        if(memberDTO == null){
            return "redirect:/members?err002";
        }
        member.setCreated_by(memberDTO.getCreated_by());
        if (memberDTO.getType().equals("Socio")
            && member.getType().equals("Socio"))
        {
            if (!memberDTO.getId_member().equals(member.getId_member())){
                return "redirect:/members/update/"+member.getId()+"?error002";
            }
        }
        if (!memberDTO.getType().equals("Socio")
                && member.getType().equals("Socio"))
        {
            if (service.getByIdmember(member.getId_member())){
                return "redirect:/members/update/"+member.getId()+"?error002";
            }
        }
        service.update(member);
        return "redirect:/members?editSuccess";
    }

    @PostMapping("/delete/{id}")
    public String deleteMember(@PathVariable(value = "id") String id){
        String res = service.delete(id);
        return "redirect:/members" + res;
    }
    @GetMapping("/delete/{id}")
    public String getDeleteMember(@PathVariable(value = "id") String id){
        String res = service.delete(id);
        return "redirect:/members" + res;
    }

}
