package com.roshka.dtaporteria.service;
import com.roshka.dtaporteria.dto.MemberDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class ImportMembersExcelService {
    private final MemberService memberService;
    public ImportMembersExcelService(MemberService memberService) {
        this.memberService = memberService;
    }

    private static int posicion = 0;
    private static final List<String> mensaje = new ArrayList<>();


    public List<String> validarExcel(MultipartFile file) throws IOException {
        mensaje.clear();
        Sheet hoja = getHoja1(file);
        conseguirNumeroDeFilas(hoja);
        int filas = hoja.getLastRowNum();
        if (hoja.getRow(hoja.getLastRowNum()) == null)
        {
            mensaje.add("No hay filas validas en el Excel.");
            return mensaje;
        }
        try {
            validacionesExcel(hoja, filas);
        } catch (NullPointerException n){
                mensaje.add("Error: " + n.getMessage() + "Una fila esta vacia. Por favor, asegurese de cargar los miembros en filas consecutivas y las celdas necesarias.");
                return mensaje;
            }
        return mensaje;
    }

    private void validacionesExcel(Sheet hoja, int filas) {
        HashMap<Double, Integer> mapaID  = new HashMap<>();
        if (filas ==0)
        {
            mensaje.add("No hay filas validas en el Excel.");
        }
        for (posicion=1; posicion<= filas; posicion++){
            Row row = hoja.getRow(posicion);
            String tipo = row.getCell(5).getStringCellValue();
            checkMiembroNoSocioCiVacio(row, tipo);
            if (row.getCell(0) != null){
                Double id_miembro = row.getCell(0).getNumericCellValue();
                checkMiembroNoSocioConIdMiembro(tipo, id_miembro);
                checkMiembroSocioConIdRepetido(id_miembro, mapaID);
            }
            checkNullCreadoporNombreApellido(row);
            checkMiembroSocioConFechavencimiento(row, tipo);
            checkMiembroNoSocioSinFechavencimiento(row, tipo);
        }
    }

    private void checkNullCreadoporNombreApellido(Row row) {
        if (row.getCell(2) == null){
            mensaje.add("Fila "+(posicion+1) +": Campo 'Creado por' vacio.");
        }
        if (row.getCell(3) == null){
            mensaje.add("Fila "+(posicion+1) +": Campo 'Nombre' vacio.");
        }
        if (row.getCell(4) == null){
            mensaje.add("Fila "+(posicion+1) +": Campo 'Apellido' vacio.");
        }
    }

    private void checkMiembroNoSocioSinFechavencimiento(Row row, String tipo) {
        if(!Objects.equals(tipo, "Socio") && row.getCell(6) == null){
            mensaje.add("Fila "+(posicion+1) +": Miembro no Socio sin fecha de vencimiento.");
            }
    }

    private void checkMiembroSocioConFechavencimiento(Row row, String tipo) {
        if(Objects.equals(tipo, "Socio") && row.getCell(6) != null){
            mensaje.add("Fila "+(posicion+1)+": Miembro Socio con fecha de vencimiento " + row.getCell(6)
                    .getLocalDateTimeCellValue()
                    .toLocalDate().toString()+".");
        }
    }

    private void checkMiembroSocioConIdRepetido(Double id_miembro, HashMap<Double, Integer> ID) {
        if (ID.containsKey(id_miembro)){
            mensaje.add("Fila "+(posicion+1)+": ID de miembro repetido: "+ id_miembro +".");
        }
        else {
            ID.put(id_miembro, 1);
            if (memberService.getById(String.valueOf(id_miembro)) == null)
            {
                mensaje.add("Fila "+(posicion+1)+": ID de miembro "+id_miembro.intValue()+" ya existe en la base de datos.");
            }
        }
    }

    private void checkMiembroNoSocioConIdMiembro(String tipo, Double id_miembro) {
        if (!Objects.equals(tipo, "Socio"))
        {
            mensaje.add("Fila "+(posicion+1)+": La combinacion ID de miembro "+ id_miembro +" y tipo "+ tipo +" no es valida.");
        }
    }

    private void checkMiembroNoSocioCiVacio(Row row, String tipo) {
        if (!Objects.equals(tipo, "Socio") && row.getCell(1) == null )
        {
            mensaje.add("Fila "+(posicion+1)+": Miembo no Socio con Campo 'CI' vacio.");
        }
    }

    public List<MemberDTO> obtenerMiembros(MultipartFile file) throws IOException {
        Sheet hoja = getHoja1(file);
        conseguirNumeroDeFilas(hoja);
        int filas = hoja.getLastRowNum();
        List<MemberDTO> miembros = new ArrayList<>();
        for (posicion =1; posicion<=filas; posicion++){
            Row row = hoja.getRow(posicion);
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setId_member(row.getCell(0)!= null ? String.valueOf((int) row.getCell(0).getNumericCellValue()) : null);
            memberDTO.setCi(row.getCell(1)!=null ? String.valueOf((int) row.getCell(1).getNumericCellValue()) : null);
            memberDTO.setName(String.valueOf(row.getCell(2)));
            memberDTO.setSurname(String.valueOf(row.getCell(3)));
            memberDTO.setCreated_by(String.valueOf(row.getCell(4)));
            memberDTO.setType(String.valueOf(row.getCell(5)));
            memberDTO.setFecha_vencimiento(row.getCell(6)!=null
                    ? row.getCell(6)
                        .getLocalDateTimeCellValue()
                        .toLocalDate()
                        .toString() : null);
            memberDTO.setIs_defaulter(Objects.equals(String.valueOf(row.getCell(7)), "Si") ? String.valueOf(row.getCell(7)) : null);
            miembros.add(memberDTO);
            }
        return miembros;
    }

    private static void conseguirNumeroDeFilas(Sheet hoja) {
        short c;
        Row lastRow;
        Cell cell;
        boolean nonBlankRowFound;
        boolean stop = false;
        while (!stop) {
            nonBlankRowFound = false;
            lastRow = hoja.getRow(hoja.getLastRowNum());
            if (lastRow == null){
                break;
            }
            for (c = lastRow.getFirstCellNum(); c <= lastRow.getLastCellNum(); c++) {
                cell = lastRow.getCell(c);
                if (cell != null && lastRow.getCell(c).getCellType() != CellType.BLANK) {
                    nonBlankRowFound = true;
                }
            }
            if (nonBlankRowFound) {
                stop = true;
            } else {
                hoja.removeRow(lastRow);
            }
        }
    }

    private static Sheet getHoja1(MultipartFile file) throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
        return xssfWorkbook.getSheetAt(0);
    }

}