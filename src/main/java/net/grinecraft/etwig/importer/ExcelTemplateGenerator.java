package net.grinecraft.etwig.importer;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.util.List;

public class ExcelTemplateGenerator implements EventTemplateGenerator {

    @Override
    public void generateTemplate(List<String> titles, OutputStream outputStream) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Template");
        Row row = sheet.createRow(0);

        for (int i = 0; i < titles.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(titles.get(i));
        }
        
        workbook.write(outputStream);
        
        //try (FileOutputStream outputStream = new FileOutputStream("template.xlsx")) {
        //    workbook.write(outputStream);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }


}
