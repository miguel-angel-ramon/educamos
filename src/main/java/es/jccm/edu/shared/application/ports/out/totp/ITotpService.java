package es.jccm.edu.shared.application.ports.out.totp;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface ITotpService {

    Boolean enviarCodigoMailQR(Long idUsuario, String email) throws IOException, WriterException;

    Boolean enviarCodigoMail(Long idUsuario, String email);

    Boolean validarCodigoTotp(String codigo, Long idUsuario);

    Boolean validarCodigoTotpQR(String codigo, Long idUsuario);

}
