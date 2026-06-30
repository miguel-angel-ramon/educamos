package es.jccm.edu.gestionidentidades.application.ports.in;

import java.util.GregorianCalendar;

import es.jccm.edu.gestionidentidades.application.domain.ResultadoValidacionCredencialesAcceso;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;

public interface IPoliticaGestionCredencialesAcceso {
    
    public static final int SIN_LOGITUD_MINIMA= -1;
    
    public static final int SIN_LOGITUD_MAXIMA= 999999;
    
    public static final int SIN_CADUCIDAD= 99999999;
    
    public static final int SIN_OBLIGATORIEDAD_CLAVES_ANTERIORES_DISTINTAS=0;
    
    public static final int SIN_BLOQUEO_POR_CLAVE_INCORRECTA=99999;
    
    public ResultadoValidacionCredencialesAcceso valida(String login, String clave);
    
    public ResultadoValidacionCredencialesAcceso valida(String login, String clave, Usuario usuario);
    
    public boolean isCaracterNumericoObligatorioLogin();
    
    public boolean isCaracterAlfanumericoObligatorioLogin();
    
    public boolean isCaracterNumericoObligatorioClave();
    
    public boolean isCaracterAlfanumericoObligatorioClave();
    
    public int getLogitudMinimaLogin();
    
    public int getLogitudMaximaLogin();
    
    public int getLogitudMinimaClave();
    
    public int getLogitudMaximaClave();
    
    public int getNumClavesAnterioresDistintasNuevaClave();
    
    public int getNumDiasCaducidadClave();
    
    public GregorianCalendar getFechaCaducidadNuevasClaves(); 
    
    public int getNumIntentosBloqueo();
    
    //public void setGestorCredencialesAcceso(GestorCredencialesAcceso gestor);
    
    public boolean tieneBloqueoPorClaveIncorrecta();
    
    public boolean tieneObligatoriedadClaveDistintaAnteriores();
    
    public boolean tieneCaducidadClave();
}
