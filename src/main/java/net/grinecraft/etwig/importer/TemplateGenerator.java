package net.grinecraft.etwig.importer;

import java.io.OutputStream;
import java.util.List;

public interface TemplateGenerator {
	
    void generateTemplate(List<String> titles, OutputStream outputStream) throws Exception;
}
