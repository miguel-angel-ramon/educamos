package es.jccm.edu.gestionidentidades.application.domain;

public class MotivoNoValidezCredencialesAcceso {
    
    public static final MotivoNoValidezCredencialesAcceso LOGIN_SIN_NUMERO = new MotivoNoValidezCredencialesAcceso(1, "Login debe contener al menos un dígito");
    
    public static final MotivoNoValidezCredencialesAcceso LOGIN_SIN_LETRA = new MotivoNoValidezCredencialesAcceso(2, "Login debe contener al menos una letra");
    
    public static final MotivoNoValidezCredencialesAcceso LOGIN_MENOR_LONGITUD_MINIMA = new MotivoNoValidezCredencialesAcceso(3, "Login con longitud menor a la permitida");
    
    public static final MotivoNoValidezCredencialesAcceso LOGIN_MAYOR_LONGITUD_MAXIMA = new MotivoNoValidezCredencialesAcceso(4, "Login con longitud mayor a la permitida");
    
    public static final MotivoNoValidezCredencialesAcceso LOGIN_REPETIDO = new MotivoNoValidezCredencialesAcceso(5, "Login usado por otro usuario");
    
    public static final MotivoNoValidezCredencialesAcceso CLAVE_SIN_NUMERO = new MotivoNoValidezCredencialesAcceso(6, "Clave debe contener al menos un dígito");
    
    public static final MotivoNoValidezCredencialesAcceso CLAVE_SIN_LETRA = new MotivoNoValidezCredencialesAcceso(7, "Clave debe contener al menos una letra");
    
    public static final MotivoNoValidezCredencialesAcceso CLAVE_MENOR_LONGITUD_MINIMA = new MotivoNoValidezCredencialesAcceso(8, "Clave con longitud menor a la permitida");
    
    public static final MotivoNoValidezCredencialesAcceso CLAVE_MAYOR_LONGITUD_MAXIMA = new MotivoNoValidezCredencialesAcceso(9, "Clave con longitud mayor a la permitida");
    
    public static final MotivoNoValidezCredencialesAcceso CLAVE_IGUAL_ANTERIOR = new MotivoNoValidezCredencialesAcceso(10, "Clave es igual a alguna anterior");

    /**
     * @param arg0
     * @param arg1
     */
    private MotivoNoValidezCredencialesAcceso(int valor, String descripcion) {
        super();
    }

}
