package es.jccm.edu.comunicaciones.adapter.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import es.jccm.edu.comunicaciones.application.ports.in.utils.IGZipBase64Encoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Component
public class GZipBase64Encoder implements IGZipBase64Encoder{

	/**
	 * Encode.
	 *
	 * @param original the original
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public final String encode(String original) throws IOException {
		byte[] originalBytes = original.getBytes();
		try (var baos = new ByteArrayOutputStream();
				var gzos = new GZIPOutputStream(baos)) {
			gzos.write(originalBytes);
			byte[] originalCompressed = baos.toByteArray();
			byte[] originalEncoded = Base64.getEncoder().encode(originalCompressed);
			
			log.debug("Tamaño de la cadena original: " + originalBytes.length);
			log.debug("Tamaño de la cadena comprimida con gzip: " + originalCompressed.length);
			log.debug("Tamaño de la cadena comprimida y codificada en Base 64: " + originalEncoded.length);
			return Base64.getEncoder().encodeToString(originalCompressed);
		}
	}
	
	/**
	 * Decode.
	 *
	 * @param coded the coded
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public final String decode(String coded) throws IOException {
		try (var gzis = new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(coded)))) {
			return IOUtils.toString(gzis, StandardCharsets.UTF_8);
		}
	}
	
}
