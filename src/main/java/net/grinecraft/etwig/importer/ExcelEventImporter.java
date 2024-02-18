package net.grinecraft.etwig.importer;

import java.io.InputStream;
import java.util.*;

import net.grinecraft.etwig.dto.events.EventImportDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.commons.io.*;

public class ExcelEventImporter implements EventImporter {

	private Map<Integer, String> status = new HashMap<>();

	@Override
	public List<EventImportDTO> read(InputStream inputStream) throws Exception {

		List<EventImportDTO> events = new ArrayList<>();
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {

			// Skip header row
			if (row.getRowNum() == 0) {
				continue;
			}

			// Add events line by line.
			try{
				EventImportDTO event = new EventImportDTO();

				// Name
				event.setName(row.getCell(0).getStringCellValue());

				// Location
				event.setLocation(row.getCell(1).getStringCellValue());

				// Description
				event.setDescription(row.getCell(2).getStringCellValue());

				// AllDayEvent
				event.setAllDayEvent(row.getCell(3).getBooleanCellValue());

				// StartDateTime
				Date startDateTime = row.getCell(4).getDateCellValue();
				event.setStartDateTime(startDateTime);

				event.setDuration(startDateTime, row.getCell(5).getDateCellValue());
				//event.setStartDateTime(row.getCell(4).getDateCellValue());
				//event.setEndDateTime(row.getCell(5).getDateCellValue());
				events.add(event);

				status.put(row.getRowNum(), null);
			} catch (Exception e){
				//e.printStackTrace();
				status.put(row.getRowNum(), e.toString());
			}

		}
		workbook.close();
		return events;
	}

	@Override
	public Map<Integer, String> status() {
		return this.status;
	}
}