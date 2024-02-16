package net.grinecraft.etwig.importer;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

public class CSVTemplateGenerator implements TemplateGenerator {

	@Override
	public void generateTemplate(List<String> titles, OutputStream outputStream) throws Exception {
		
		 PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"));
		 
		 for (int i = 0; i < titles.size(); i++) {
			 writer.print(titles.get(i));
			 if (i < titles.size() - 1) {
				 writer.print(",");
				 }
			 }
		 
		 writer.flush();
	}
}
