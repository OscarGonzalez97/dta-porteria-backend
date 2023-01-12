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
import java.util.List;

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
        celda.setCellValue("Cedula");
        celda.setCellStyle(estilo);

        celda = fila.createCell(2);
        celda.setCellValue("Cedula Portero");
        celda.setCellStyle(estilo);

        celda = fila.createCell(3);
        celda.setCellValue("Fecha");
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
            celda.setCellValue(record.getCi_member());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(record.getCi_portero());
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(record.getDate_time());
            hoja.autoSizeColumn(3);
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
}
