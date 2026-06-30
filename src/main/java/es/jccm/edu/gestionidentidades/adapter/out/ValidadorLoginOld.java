package es.jccm.edu.gestionidentidades.adapter.out;

import es.jccm.edu.gestionidentidades.application.domain.MotivoNoValidezCredencialesAcceso;

public class ValidadorLoginOld {
    private int longMinimaLogin;
    private int longMaximaLogin;
    private boolean caracterNumericoObligatorioLogin;
    private boolean caracterAlfanumericoObligatorioLogin;
    
	   public ValidadorLoginOld(int longMinimaLogin, int longMaximaLogin, boolean caracterNumericoObligatorioLogin,
			boolean caracterAlfanumericoObligatorioLogin) {
		super();
		this.longMinimaLogin = longMinimaLogin;
		this.longMaximaLogin = longMaximaLogin;
		this.caracterNumericoObligatorioLogin = caracterNumericoObligatorioLogin;
		this.caracterAlfanumericoObligatorioLogin = caracterAlfanumericoObligatorioLogin;
	}

	/**
     * @param login
     * @return
     */
    public MotivoNoValidezCredencialesAcceso validaLogin(String login) {
        MotivoNoValidezCredencialesAcceso resultado = null;

        //Se comprueba si el login tiene números
        if (this.caracterNumericoObligatorioLogin) {
            boolean conNumero = false;
            char[] caracteres = login.toCharArray();
            for (int i = 0; i < caracteres.length && !conNumero; i++) {
                conNumero = Character.isDigit(caracteres[i]);
            }

            if (!conNumero) { //No tiene número y debería tenerlo
                resultado = MotivoNoValidezCredencialesAcceso.LOGIN_SIN_NUMERO;
            }
        }

        if (resultado == null) {
            //Se comprueba si el login tiene letras
            if (this.caracterAlfanumericoObligatorioLogin) {
                boolean conLetra = false;
                char[] caracteres = login.toCharArray();
                for (int i = 0; i < caracteres.length && !conLetra; i++) {
                    conLetra = Character.isLetter(caracteres[i]);
                }

                if (!conLetra) { //No tiene número y debería tenerlo
                    resultado = MotivoNoValidezCredencialesAcceso.LOGIN_SIN_LETRA;
                }
            }
        }

        if (resultado == null) {
            //Se comprueba que supera la logitud mínima
            if (login.length() < this.longMinimaLogin) {
                resultado = MotivoNoValidezCredencialesAcceso.LOGIN_MENOR_LONGITUD_MINIMA;
            }
        }

        if (resultado == null) {
            //Se comprueba que no supera la logitud máxima
            if (login.length() > this.longMaximaLogin) {
                resultado = MotivoNoValidezCredencialesAcceso.LOGIN_MAYOR_LONGITUD_MAXIMA;
            }
        }

        return resultado;
    }
}
