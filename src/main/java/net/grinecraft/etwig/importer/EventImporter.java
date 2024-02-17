package net.grinecraft.etwig.importer;

import java.io.InputStream;
import java.util.List;

import net.grinecraft.etwig.dto.events.EventImportDTO;

public interface EventImporter {
	
    List<EventImportDTO> read(InputStream inputStream) throws Exception;

    int getSuccessfulImports();

    int getFailedImports();
}
