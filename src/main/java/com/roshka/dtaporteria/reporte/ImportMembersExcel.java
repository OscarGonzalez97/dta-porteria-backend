package com.roshka.dtaporteria.reporte;
import com.roshka.dtaporteria.dto.MemberDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ImportMembersExcel {
    public List<String> validateExcel(MultipartFile file) throws IOException {
        List<String> mensaje = new ArrayList<>();
        Sheet hoja = getHoja1(file);
        conseguirNumeroDeFilas(hoja);

        Map<Double, Integer> ID  = new HashMap<>();
        int filas = hoja.getLastRowNum();
        int cells = hoja.getRow(0).getLastCellNum();
        //Verificaciones
        try {
            for (int x =1; x<=filas; x++){
            Row row = hoja.getRow(x);
            String tipo = row.getCell(5).getStringCellValue();
            if (row.getCell(0) != null){
                Double id_miembro = row.getCell(0).getNumericCellValue();
                if (!Objects.equals(tipo, "Socio"))
                {
                    mensaje.add("Fila "+(x+1)+": La combinacion ID de miembro "+id_miembro+" y tipo "+tipo+" no es valida.");
                }
                if (ID.containsKey(id_miembro)){
                    mensaje.add("Fila "+(x+1)+": ID de miembro repetido: "+id_miembro);
                }
                else {
                    ID.put(id_miembro, 1);
                }
            }
            if(Objects.equals(tipo, "Socio") && row.getCell(6) != null){
                mensaje.add("Fila "+(x+1)+": Miembro Socio con fecha de vencimiento " + row.getCell(6)
                        .getLocalDateTimeCellValue()
                        .toLocalDate().toString());
            }
            if(!Objects.equals(tipo, "Socio") && row.getCell(6) == null){
                mensaje.add("Fila "+(x+1) +": Miembro no Socio sin fecha de vencimiento");
                }
        }
        } catch (NullPointerException n){
            mensaje.add("Error: " + n.getMessage() + "Una fila esta vacia. Por favor, asegurese de cargar los miembros en filas consecutivas y las celdas necesarias.");
            return mensaje;
        }
        return mensaje;
    }
    public List<MemberDTO> obtenerMiembros(MultipartFile file) throws IOException {
        Sheet hoja = getHoja1(file);
        conseguirNumeroDeFilas(hoja);
        int filas = hoja.getLastRowNum();
        int cells = hoja.getRow(0).getLastCellNum();
        List<MemberDTO> miembros = new ArrayList<>();
        for (int x =1; x<=filas; x++){
            Row row = hoja.getRow(x);
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setId_member(String.valueOf(row.getCell(0)));
            memberDTO.setCi(String.valueOf(row.getCell(1)));
            memberDTO.setName(String.valueOf(row.getCell(2)));
            memberDTO.setSurname(String.valueOf(row.getCell(3)));
            memberDTO.setCreated_by(String.valueOf(row.getCell(4)));
            memberDTO.setType(String.valueOf(row.getCell(5)));
            memberDTO.setFecha_vencimiento(row.getCell(6)!=null
                    ? row.getCell(6)
                        .getLocalDateTimeCellValue()
                        .toLocalDate()
                        .toString() : null);
            memberDTO.setIs_defaulter(String.valueOf(row.getCell(7)));
            miembros.add(memberDTO);
            }
        return miembros;
    }
//        Iterar cada columna dentro de cada fila

    private static void conseguirNumeroDeFilas(Sheet hoja) {
        short c;
        Row lastRow;
        Cell cell;
        boolean nonBlankRowFound;
        boolean stop = false;
        while (!stop) {
            nonBlankRowFound = false;
            lastRow = hoja.getRow(hoja.getLastRowNum());
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
        Sheet hoja = xssfWorkbook.getSheetAt(0);
        return hoja;
    }

}