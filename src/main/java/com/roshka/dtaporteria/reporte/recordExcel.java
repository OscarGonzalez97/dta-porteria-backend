package com.roshka.dtaporteria.reporte;

import com.roshka.dtaporteria.dto.RecordDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.*;
import java.text.*;

public class recordExcel {

        private XSSFWorkbook excel;
        private XSSFSheet hoja;

        private List<RecordDTO> listaRecord;

    public recordExcel(List<RecordDTO> listaRecord){
        this.listaRecord = listaRecord;
        excel = new XSSFWorkbook();
        hoja = excel.createSheet("Records");
    }
    private void EscribirCabecera(){
        Row fila = hoja.createRow(0);

        CellStyle estilo = excel.createCellStyle();
        XSSFFont fuente = excel.createFont();
        fuente.setBold(true); //para que la cabecera este en negrita
        fuente.setFontHeight(16); //el tamaño de la letra
        estilo.setFont(fuente);

        Cell celda = fila.createCell(0);
        celda.setCellValue("Nombre"); //el valor de la celda
        celda.setCellStyle(estilo); //define el estilo que se definio mas arriba

        celda = fila.createCell(1);
        celda.setCellValue("Apellido");
        celda.setCellStyle(estilo);

        celda = fila.createCell(2);
        celda.setCellValue("Cedula");
        celda.setCellStyle(estilo);

        celda = fila.createCell(3);
        celda.setCellValue("Numero de socio");
        celda.setCellStyle(estilo);

        celda = fila.createCell(4);
        celda.setCellValue("Nombre Portero");
        celda.setCellStyle(estilo);

        celda = fila.createCell(5);
        celda.setCellValue("Apellido Portero");
        celda.setCellStyle(estilo);

        celda = fila.createCell(6);
        celda.setCellValue("Cedula Portero");
        celda.setCellStyle(estilo);

        celda = fila.createCell(7);
        celda.setCellValue("Fecha");
        celda.setCellStyle(estilo);

        celda = fila.createCell(8);
        celda.setCellValue("Tipo");
        celda.setCellStyle(estilo);

        celda = fila.createCell(9);
        celda.setCellValue("Lugar");
        celda.setCellStyle(estilo);
    }
    private void EscribirDatos(){
        int numeroFilas=1;

        CellStyle estilo = excel.createCellStyle();
        XSSFFont fuente = excel.createFont();
        fuente.setFontHeight(16); //el tamaño de la letra
        estilo.setFont(fuente);

        for (RecordDTO record: listaRecord){
            Row fila = hoja.createRow(numeroFilas ++); //para ir creando filas dependiendo de tu cant de record

            //estos son los valores de la columna de la fila creada
            Cell celda = fila.createCell(0);
            celda.setCellValue(record.getName_member());
            hoja.autoSizeColumn(0); //para que se ajuste el texto
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(record.getSurname_member());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(record.getCi_member());
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(record.getId_member());
            hoja.autoSizeColumn(3);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(record.getName_portero());
            hoja.autoSizeColumn(4);
            celda.setCellStyle(estilo);

            celda = fila.createCell(5);
            celda.setCellValue(record.getSurname_portero());
            hoja.autoSizeColumn(5);
            celda.setCellStyle(estilo);

            celda = fila.createCell(6);
            celda.setCellValue(record.getCi_portero());
            hoja.autoSizeColumn(6);
            celda.setCellStyle(estilo);

            celda = fila.createCell(7);
            celda.setCellValue(CambiarFormatoFecha(record.getDate_time()));
            hoja.autoSizeColumn(7);
            celda.setCellStyle(estilo);

            celda = fila.createCell(8);
            celda.setCellValue(record.getType());
            hoja.autoSizeColumn(8);
            celda.setCellStyle(estilo);

            celda = fila.createCell(9);
            celda.setCellValue(record.getSector());
            hoja.autoSizeColumn(9);
            celda.setCellStyle(estilo);
        }
    }

    public void GenerarReporte(HttpServletResponse response) throws IOException {

        EscribirCabecera();
        EscribirDatos();

        ServletOutputStream outputStream = response.getOutputStream();
        excel.write(outputStream);

        excel.close();
        outputStream.close();
    }

    public String CambiarFormatoFecha(String fechalong){

        long fechaL= Long.parseLong(fechalong.substring(0,9));

        Date Fecha = new Date(fechaL*1000L);

        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));

        String java_date = jdf.format(Fecha);

        return java_date;
    }
}
