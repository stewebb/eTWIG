package net.grinecraft.etwig.importer;

public class EventImporterFactory {

    public static EventImporter getEventImporter(String fileType) {

        return switch (fileType) {
            case "EXCEL" -> new ExcelEventImporter();
            case "ODS" -> new ODSEventImporter();
            default -> throw new IllegalArgumentException("Unsupported file type: " + fileType);
        };
    }
}