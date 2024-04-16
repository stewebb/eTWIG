package net.etwig.webapp.importer;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import net.grinecraft.etwig.dto.events.EventImportDTO;

public interface EventImporter {
	
    List<EventImportDTO> read(InputStream inputStream) throws Exception;

    Map<Integer, String> status();
}
