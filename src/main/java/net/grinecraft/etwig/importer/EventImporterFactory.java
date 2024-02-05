package net.grinecraft.etwig.importer;

public class EventImporterFactory {

    public EventImporter getFileReader(String fileType) {
    	
        switch (fileType) {
            case "CSV":
                return new CSVEventImporter();
            case "EXCEL":
                return new ExcelEventImporter();
            case "ODS":
                return new ODSEventImporter();
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }
}