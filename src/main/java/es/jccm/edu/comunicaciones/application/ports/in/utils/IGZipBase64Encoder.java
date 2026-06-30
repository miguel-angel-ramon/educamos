package es.jccm.edu.comunicaciones.application.ports.in.utils;

import java.io.IOException;

public interface IGZipBase64Encoder {

	public String encode(String original) throws IOException;
	
	public String decode(String coded) throws IOException;
}
