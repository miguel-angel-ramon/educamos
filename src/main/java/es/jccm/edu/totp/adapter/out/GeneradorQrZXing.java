package es.jccm.edu.totp.adapter.out;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import es.jccm.edu.totp.application.ports.out.generadorqr.GeneracionQrException;
import es.jccm.edu.totp.application.ports.out.generadorqr.GeneradorQr;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GeneradorQrZXing implements GeneradorQr {
	
	private static final int height=400;
	private static final int width=400;

	@Override
	public void generate(String barCodeData, OutputStream outputStream) throws GeneracionQrException {
		BitMatrix matrix;
		try {
			matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE,
			        width, height);
		} catch (WriterException e) {
			log.error("no se pudo generar código qr",e);
			throw new GeneracionQrException(e);
		}
		
		try (OutputStream out = outputStream) {
            MatrixToImageWriter.writeToStream(matrix, "png", out);
        } catch (IOException e) {
			log.error("hubo un problema al pasar la imagen qr al outputstream",e);
			throw new GeneracionQrException(e);
		}
		
	}
    
}
