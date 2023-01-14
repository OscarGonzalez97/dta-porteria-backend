package com.roshka.dtaporteria.reporte;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class ImportMembersExcel {
    public String getFileAndAddMember(MultipartFile file) throws IOException {

        //Conseguir la hoja1
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = xssfWorkbook.getSheetAt(0);

        //Codigo para descartar filas vacias y tener solo las filas con valores
        boolean stop = false;
        boolean nonBlankRowFound;
        short c;
        Row lastRow = null;
        Cell cell = null;

        while (!stop) {
            nonBlankRowFound = false;
            lastRow = sheet.getRow(sheet.getLastRowNum());
            for (c = lastRow.getFirstCellNum(); c <= lastRow.getLastCellNum(); c++) {
                cell = lastRow.getCell(c);
                if (cell != null && lastRow.getCell(c).getCellType() != CellType.BLANK) {
                    nonBlankRowFound = true;
                }
            }
            if (nonBlankRowFound) {
                stop = true;
            } else {
                sheet.removeRow(lastRow);
            }
        }
        int filas = sheet.getLastRowNum();
        int cells = sheet.getRow(0).getLastCellNum();
        for (int x =1; x<=filas; x++){
            Row row = sheet.getRow(x);
            for (int j =0; j<cells; j++){
                Cell celda = row.getCell(j);
                System.out.println(celda);
            }
        }
        return "redirect:/import?error001";
        }
}