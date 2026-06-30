package es.jccm.edu.totp.application.ports.out.generadorqr;

import java.io.OutputStream;

public interface GeneradorQr {

	void generate(String barCodeData, OutputStream outputStream) throws GeneracionQrException;

}