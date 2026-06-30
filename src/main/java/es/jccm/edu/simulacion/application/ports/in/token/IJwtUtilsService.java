package es.jccm.edu.simulacion.application.ports.in.token;

public interface IJwtUtilsService {

    String generateToken(String jwt);

    String generateToken(String jwt, String oidUsuario);

    String generateTokenSimulacion(String jwt, String oidUsuario);

}