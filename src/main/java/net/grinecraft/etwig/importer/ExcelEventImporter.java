package net.grinecraft.etwig.importer;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

import net.grinecraft.etwig.dto.events.EventImportDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.commons.io.*;

public class ExcelEventImporter implements EventImporter {

	@Override
	public List<EventImportDTO> read(InputStream inputStream) throws Exception {

		List<EventImportDTO> events = new ArrayList<>();
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			if (row.getRowNum() == 0) continue; // Skip header row

			EventImportDTO event = new EventImportDTO();
			event.setName(row.getCell(0).getStringCellValue());
			event.setLocation(row.getCell(1).getStringCellValue());
			event.setDescription(row.getCell(2).getStringCellValue());
			event.setAllDayEvent(row.getCell(3).getStringCellValue());
			event.setStartDateTime(row.getCell(4).getStringCellValue());
			event.setEndDateTime(row.getCell(5).getStringCellValue());

			events.add(event);
		}
		workbook.close();
		return events;
	}

}