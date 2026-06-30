package es.jccm.edu.shared.application.domain.rodal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class CustomMultipartFile implements MultipartFile {
	
	private final byte[] imgContent;
	private String fileName;
	
	
	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		try (FileOutputStream f = new FileOutputStream(dest)) {
			f.write(imgContent);
		}
		
	}
	
	public CustomMultipartFile(byte[] imgContent, String fileName ) {
        this.imgContent = imgContent;
        this.fileName = fileName;
    }
	
	@Override
	public String getName() {
		return fileName;
	}
	
	@Override
	public String getContentType() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return imgContent == null || imgContent.length == 0;
	}

	@Override
	public byte[] getBytes() throws IOException {
		 return imgContent;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		 return new ByteArrayInputStream(imgContent);
	}
	
	@Override
	public long getSize() {
		return imgContent.length;
	}
	
	@Override
	public String getOriginalFilename() {
		return fileName;
	}



}
