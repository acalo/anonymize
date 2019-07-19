package com.keepler.anonymize.components;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

public interface ReadWriteFileComponent {

	public List<String[]> readFile(Reader reader);

	public void writeFile(List<String[]> csvArray, Writer write) throws IOException;

	public String readFileDisc(InputStream in);

	public String writeFileDisc(String text);

}
