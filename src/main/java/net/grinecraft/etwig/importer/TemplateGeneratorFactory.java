package net.grinecraft.etwig.importer;

public class TemplateGeneratorFactory {

    public static TemplateGenerator generateTemplate(String fileType) {

        return switch (fileType) {
            case "CSV" -> new CSVTemplateGenerator();
            case "EXCEL" -> new ExcelTemplateGenerator();
            case "ODS" -> new ODSTemplateGenerator();
            default -> throw new IllegalArgumentException("Unsupported file type: " + fileType);
        };
    }
}