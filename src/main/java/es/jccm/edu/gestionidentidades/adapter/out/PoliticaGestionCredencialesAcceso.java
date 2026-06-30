package es.jccm.edu.gestionidentidades.adapter.out;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import es.jccm.edu.gestionidentidades.application.domain.MotivoNoValidezCredencialesAcceso;
import es.jccm.edu.gestionidentidades.application.domain.ResultadoValidacionCredencialesAcceso;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.ports.in.IPoliticaGestionCredencialesAcceso;


public class PoliticaGestionCredencialesAcceso implements IPoliticaGestionCredencialesAcceso {

   // private GestorCredencialesAcceso gestor;

    private boolean caracterNumericoObligatorioLogin;

    private boolean caracterAlfanumericoObligatorioLogin;

    private boolean caracterNumericoObligatorioClave;

    private boolean caracterAlfanumericoObligatorioClave;

    private int longMinimaLogin;

    private int longMaximaLogin;

    private int longMinimaClave;

    private int longMaximaClave;

    private int numClavesAnterioresDistintasNuevaClave;

    private int numDiasCaducidad;

    private int numIntentosIntroduccionClave;
    
    private ValidadorLoginOld validadorLogin;

    public PoliticaGestionCredencialesAcceso(boolean caracterNumericoObligatorioLogin, boolean caracterAlfanumericoObligatorioLogin, boolean caracterNumericoObligatorioClave, boolean caracterAlfanumericoObligatorioClave, int longMinimaLogin, int longMaximaLogin, int longMinimaClave, int longMaximaClave, int numClavesAnterioresDistintasNuevaClave, int numDiasCaducidad, int numIntentosIntroduccionClave) {

        this.caracterNumericoObligatorioLogin = caracterNumericoObligatorioLogin;

        this.caracterAlfanumericoObligatorioLogin = caracterAlfanumericoObligatorioLogin;

        this.caracterNumericoObligatorioClave = caracterNumericoObligatorioClave;

        this.caracterAlfanumericoObligatorioClave = caracterAlfanumericoObligatorioClave;

        this.longMinimaLogin = longMinimaLogin;

        this.longMaximaLogin = longMaximaLogin;

        this.longMinimaClave = longMinimaClave;

        this.longMaximaClave = longMaximaClave;

        this.numClavesAnterioresDistintasNuevaClave = numClavesAnterioresDistintasNuevaClave;

        this.numDiasCaducidad = numDiasCaducidad;

        this.numIntentosIntroduccionClave = numIntentosIntroduccionClave;

        this.validadorLogin=new ValidadorLoginOld(longMinimaLogin, longMaximaLogin, caracterNumericoObligatorioLogin, caracterAlfanumericoObligatorioLogin);
    }

    public ResultadoValidacionCredencialesAcceso valida(String login, String clave) {
        MotivoNoValidezCredencialesAcceso motivoNoValidez = null;

        motivoNoValidez = validadorLogin.validaLogin(login);

        if (motivoNoValidez == null) {
            motivoNoValidez = validaClave(clave);
        }

        if (motivoNoValidez == null) {
            return new ResultadoValidacionCredencialesAcceso(true, null);
        } else {
            return new ResultadoValidacionCredencialesAcceso(false, motivoNoValidez);
        }
    }

    public ResultadoValidacionCredencialesAcceso valida(String login, String clave, Usuario usuario) {
        ResultadoValidacionCredencialesAcceso resultado = this.valida(login, clave);

        //Se comprueba que la nueva clave es anterior a las n-anteriores
        if (resultado.isValido()) {
            //Se comprueba si la clave nueva debe ser diferente a las
            // n-anteriores
            if (this.getNumClavesAnterioresDistintasNuevaClave() != IPoliticaGestionCredencialesAcceso.SIN_OBLIGATORIEDAD_CLAVES_ANTERIORES_DISTINTAS) {
                //Se comprueba que la nueva clave es distinta a la actual
            	//TODO traer clave cifrada
                //String claveCifrada = this.gestor.cifraClave(clave);
                /*if (!usuario.getCredencialesAcceso().getClave().getClave().equals(claveCifrada)) {
                    List clavesAnteriores = usuario.getClavesAnteriores();
                    
                    Iterator i = clavesAnteriores.iterator();
                    for(int j=1;j<=this.getNumClavesAnterioresDistintasNuevaClave() && i.hasNext(); j++) {
                        IClave claveAnterior = (IClave) i.next();
                        
                        //La clave es igual a una de las anteriores
                        if(claveAnterior.getClave().equals(claveCifrada)) {*/
                            return new ResultadoValidacionCredencialesAcceso(false, MotivoNoValidezCredencialesAcceso.CLAVE_IGUAL_ANTERIOR);
                        /*}
                    }
                }*/
            }
        }
        
        return resultado;
    }

    public boolean isCaracterNumericoObligatorioLogin() {
        return this.caracterNumericoObligatorioLogin;
    }

    public boolean isCaracterAlfanumericoObligatorioLogin() {
        return this.caracterAlfanumericoObligatorioLogin;
    }

    public boolean isCaracterNumericoObligatorioClave() {
        return this.caracterNumericoObligatorioClave;
    }

    public boolean isCaracterAlfanumericoObligatorioClave() {
        return this.caracterAlfanumericoObligatorioClave;
    }

    public int getLogitudMinimaLogin() {
        return this.longMinimaLogin;
    }

    public int getLogitudMaximaLogin() {
        return this.longMaximaLogin;
    }

    public int getLogitudMinimaClave() {
        return this.longMinimaClave;
    }

    public int getLogitudMaximaClave() {
        return this.longMaximaClave;
    }

    public int getNumClavesAnterioresDistintasNuevaClave() {
        return this.numClavesAnterioresDistintasNuevaClave;
    }

    public int getNumDiasCaducidadClave() {
        return this.numDiasCaducidad;
    }

    public GregorianCalendar getFechaCaducidadNuevasClaves() {
        GregorianCalendar fechaCaducidad = new GregorianCalendar();

        fechaCaducidad.add(Calendar.DAY_OF_MONTH, this.numDiasCaducidad);

        return fechaCaducidad;
    }

    public int getNumIntentosBloqueo() {
        return this.numIntentosIntroduccionClave;
    }

   /* public void setGestorCredencialesAcceso(GestorCredencialesAcceso gestor) {
        this.gestor = gestor;
    }*/

 

    private MotivoNoValidezCredencialesAcceso validaClave(String clave) {
        MotivoNoValidezCredencialesAcceso resultado = null;

        //Se comprueba si el login tiene números
        if (this.isCaracterNumericoObligatorioLogin()) {
            boolean conNumero = false;
            char[] caracteres = clave.toCharArray();
            for (int i = 0; i < caracteres.length && !conNumero; i++) {
                conNumero = Character.isDigit(caracteres[i]);
            }

            if (!conNumero) { //No tiene número y debería tenerlo
                resultado = MotivoNoValidezCredencialesAcceso.CLAVE_SIN_NUMERO;
            }
        }

        if (resultado == null) {
            //Se comprueba si el login tiene letras
            if (this.isCaracterAlfanumericoObligatorioLogin()) {
                boolean conLetra = false;
                char[] caracteres = clave.toCharArray();
                for (int i = 0; i < caracteres.length && !conLetra; i++) {
                    conLetra = Character.isLetter(caracteres[i]);
                }

                if (!conLetra) { //No tiene número y debería tenerlo
                    resultado = MotivoNoValidezCredencialesAcceso.CLAVE_SIN_LETRA;
                }
            }
        }

        if (resultado == null) {
            //Se comprueba que supera la logitud mínima
            if (clave.length() < this.getLogitudMinimaLogin()) {
                resultado = MotivoNoValidezCredencialesAcceso.CLAVE_MENOR_LONGITUD_MINIMA;
            }
        }

        if (resultado == null) {
            //Se comprueba que no supera la logitud máxima
            if (clave.length() > this.getLogitudMaximaLogin()) {
                resultado = MotivoNoValidezCredencialesAcceso.CLAVE_MAYOR_LONGITUD_MAXIMA;
            }
        }

        return resultado;
    }

    public boolean tieneBloqueoPorClaveIncorrecta() {
        return this.numIntentosIntroduccionClave!=IPoliticaGestionCredencialesAcceso.SIN_BLOQUEO_POR_CLAVE_INCORRECTA;
    }

    public boolean tieneObligatoriedadClaveDistintaAnteriores() {
        return this.numClavesAnterioresDistintasNuevaClave!=IPoliticaGestionCredencialesAcceso.SIN_OBLIGATORIEDAD_CLAVES_ANTERIORES_DISTINTAS;
    }

    public boolean tieneCaducidadClave() {
        return this.numDiasCaducidad != SIN_CADUCIDAD;
    }
}
