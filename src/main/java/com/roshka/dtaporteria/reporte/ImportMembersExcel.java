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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class ImportMembersExcel {
    public List<String> validarExcel(MultipartFile file) throws IOException {
        List<String> mensaje = new ArrayList<>();
        Sheet hoja = getHoja1(file);
        conseguirNumeroDeFilas(hoja);
        //Si es un Excel vacio sin filas, retornar el error
        if (hoja.getRow(hoja.getLastRowNum()) == null)
        {
            mensaje.add("No hay filas validas en el Excel.");
            return mensaje;
        }
        HashMap<Double, Integer> ID  = new HashMap<>();
        int filas = hoja.getLastRowNum();
        //Verificaciones
        try {
            if (filas==0)
            {
                mensaje.add("No hay filas validas en el Excel.");
            }
            for (int x =1; x<=filas; x++){
                Row row = hoja.getRow(x);
                String tipo = row.getCell(5).getStringCellValue();
                if (!Objects.equals(tipo, "Socio") && row.getCell(1) == null )
                {
                    mensaje.add("Fila "+(x+1)+": Miembo no Socio con Campo 'CI' vacio.");
                }
                if (row.getCell(0) != null){
                    Double id_miembro = row.getCell(0).getNumericCellValue();
                    if (!Objects.equals(tipo, "Socio"))
                    {
                        mensaje.add("Fila "+(x+1)+": La combinacion ID de miembro "+id_miembro+" y tipo "+tipo+" no es valida.");
                    }
                    if (ID.containsKey(id_miembro)){
                        mensaje.add("Fila "+(x+1)+": ID de miembro repetido: "+id_miembro+".");
                    }
                    else {
                        ID.put(id_miembro, 1);
                    }
                }
                //Un socio no puede tener fecha de vencimiento
                if(Objects.equals(tipo, "Socio") && row.getCell(6) != null){
                    mensaje.add("Fila "+(x+1)+": Miembro Socio con fecha de vencimiento " + row.getCell(6)
                            .getLocalDateTimeCellValue()
                            .toLocalDate().toString()+".");
                }
                //Un miembro no socio debe tener fecha de vencimiento
                if(!Objects.equals(tipo, "Socio") && row.getCell(6) == null){
                    mensaje.add("Fila "+(x+1) +": Miembro no Socio sin fecha de vencimiento.");
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
        List<MemberDTO> miembros = new ArrayList<>();
        for (int x =1; x<=filas; x++){
            Row row = hoja.getRow(x);
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

    //No hay un metodo que de con exactitud el numero de filas con valores dentro de una hoja de excel, por lo que hay que usar el metodo de abajo para encontrarlo
    private static void conseguirNumeroDeFilas(Sheet hoja) {
        short c;
        Row lastRow;
        Cell cell;
        boolean nonBlankRowFound;
        boolean stop = false;
        while (!stop) {
            nonBlankRowFound = false;
            lastRow = hoja.getRow(hoja.getLastRowNum());
            //No hay filas
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