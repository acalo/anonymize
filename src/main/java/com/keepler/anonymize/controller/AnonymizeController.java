package com.keepler.anonymize.controller;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.keepler.anonymize.components.EncryptComponent;
import com.keepler.anonymize.components.ReadWriteFileComponent;

@RestController
@RequestMapping("/keepler")
public class AnonymizeController {
	
	@Autowired
	private ReadWriteFileComponent readWrite;
	
	@Autowired
	private EncryptComponent encrypt;
	
	@Value("${anonymizeKeepler.anonymizeElements}")
	private List<String> anonymizeValues;
	
	@PostMapping("/anonymize")
    public void uploadFile(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        String fileName = "anonymize.csv";
        InputStream in = null;
        OutputStream out;
        try {
        	in = file.getInputStream();
        	Reader targetReader = new InputStreamReader(in);
        	List<String[]> csvReadList = readWrite.readFile(targetReader);
        	int i = 0;
        	List<Integer> positions = new ArrayList<Integer>();
        	for (String[] strings : csvReadList) {
				
        		for (int j = 0; j<strings.length;j++) {
        			String value = strings[j];
        			if (i == 0) {
        				if (anonymizeValues.contains(value)) {
        					positions.add(j);
        				}
    				}else {
    					if (positions.contains(j)) {
    						strings[j] = encrypt.encript(value);
    					}
    				}	
        		}
        		
        		i++;
			}
        	out = response.getOutputStream();
        	Writer outputStreamWriter = new OutputStreamWriter(out);
        	readWrite.writeFile(csvReadList, outputStreamWriter);
        	
        	response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".csv");
            response.setContentType("text/csv");
        	
        }catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (in != null) {
					in.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
       
    }
	
	@GetMapping("/ping")
    public String ping() {
		return "ping";
	}
}
