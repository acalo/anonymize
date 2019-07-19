package com.keepler.anonymize.components;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

@Component
public class CSVGenerateImpl implements ReadWriteFileComponent{

	@Override
	public List<String[]> readFile(Reader reader) {
		CSVReader csvReader = new CSVReader(reader);
	    List<String[]> list = new ArrayList<>();
	    try {
	    	list = csvReader.readAll();
	    }catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (csvReader != null) {
					csvReader.close();
				}
				if (reader != null) {
					reader.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	    
	    
	    return list;
	}

	@Override
	public void writeFile(List<String[]> csvArray, Writer write_out) throws IOException{
		CSVWriter writer = new CSVWriter(write_out);
	    for (String[] array : csvArray) {
	        writer.writeNext(array);
	    }
	    writer.close();
	}

	@Override
	public String readFileDisc(InputStream in) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String writeFileDisc(String text) {
		// TODO Auto-generated method stub
		return null;
	}

}
