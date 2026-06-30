package es.jccm.edu.shared.application.ports.out.totp.validar;

public interface ValidarCodigoTotpOut {
    boolean validarCodigoTotp(String codigo, long idUsuario);
}
