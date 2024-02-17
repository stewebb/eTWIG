package net.grinecraft.etwig.importer;

import java.io.InputStream;
import java.util.List;

import net.grinecraft.etwig.dto.events.EventImportDTO;

public class ODSEventImporter implements EventImporter {

	@Override
	public List<EventImportDTO> read(InputStream inputStream) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSuccessfulImports() {
		return 0;
	}

	@Override
	public int getFailedImports() {
		return 0;
	}

}