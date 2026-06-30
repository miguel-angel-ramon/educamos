package es.jccm.edu.totp.application.ports.in;

public interface ValidarCodigoTotpUC {
    boolean validarCodigoTotp(String codigo, long idUsuario);
}
