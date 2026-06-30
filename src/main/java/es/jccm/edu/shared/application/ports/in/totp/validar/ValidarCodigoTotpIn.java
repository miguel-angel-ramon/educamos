package es.jccm.edu.shared.application.ports.in.totp.validar;

public interface ValidarCodigoTotpIn {
    boolean validarCodigoTotp(String codigo, long idUsuario);
}
