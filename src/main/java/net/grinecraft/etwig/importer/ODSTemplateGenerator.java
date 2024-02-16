package net.grinecraft.etwig.importer;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import java.io.OutputStream;
import java.util.List;

public class ODSTemplateGenerator implements TemplateGenerator {

	@Override
	public void generateTemplate(List<String> titles, OutputStream outputStream) throws Exception {
		 SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument.newSpreadsheetDocument();
         Table sheet = spreadsheetDoc.getSheetByIndex(0);

         for (int i = 0; i < titles.size(); i++) {
             sheet.getCellByPosition(i, 0).setStringValue(titles.get(i));
         }

         spreadsheetDoc.save(outputStream);
		
	}

}
