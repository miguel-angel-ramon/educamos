package es.jccm.edu.shared.application.ports.out.jwtutils;

public interface IJwtUtilsService {

    String generateToken(String jwt);

    String generateToken(String jwt, String oidUsuario);

    String generateTokenSimulacionAlternativo(String jwt, String oidUsuario);

}