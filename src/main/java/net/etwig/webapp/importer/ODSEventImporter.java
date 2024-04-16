package net.grinecraft.etwig.importer;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.table.Row;

import java.util.*;
import java.io.InputStream;

import net.grinecraft.etwig.dto.events.EventImportDTO;

public class ODSEventImporter implements EventImporter {

	private Map<Integer, String> status = new HashMap<>();

	@Override
	public List<EventImportDTO> read(InputStream inputStream) throws Exception {
		List<EventImportDTO> events = new ArrayList<>();
		SpreadsheetDocument spreadsheetDocument = SpreadsheetDocument.loadDocument(inputStream);
		Table sheet = spreadsheetDocument.getSheetByIndex(0);

		// Skip header by starting with 1
		for (int rowIndex = 1; rowIndex < sheet.getRowCount(); rowIndex++) {
			Row row = sheet.getRowByIndex(rowIndex);

			// Add events line by line.
			try {
				EventImportDTO event = new EventImportDTO();

				// Name
				event.setName(row.getCellByIndex(0).getStringValue());

				// Location
				event.setLocation(row.getCellByIndex(1).getStringValue());

				// Description
				event.setDescription(row.getCellByIndex(2).getStringValue());

				// AllDayEvent
				event.setAllDayEvent(row.getCellByIndex(3).getBooleanValue());

				// StartDateTime
				Date startDateTime = row.getCellByIndex(4).getDateTimeValue().getTime();
				System.out.println(startDateTime);
				System.out.println(row.getCellByIndex(5).getDateTimeValue().getTime());
				event.setStartDateTime(startDateTime);

				// Duration
				event.setDuration(startDateTime, row.getCellByIndex(5).getDateTimeValue().getTime());

				events.add(event);
				status.put(rowIndex, null); // Make sure 'status' map is defined appropriately
			} catch (Exception e) {
				status.put(rowIndex, e.getMessage()); // Make sure 'status' map is defined appropriately
			}
		}

		return events;
	}

	@Override
	public Map<Integer, String> status() {
		return this.status;
	}
}