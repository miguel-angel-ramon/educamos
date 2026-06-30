package es.jccm.edu.gestionidentidades.application.services;

import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.jccm.edu.gestionidentidades.adapter.out.repository.UsuariosRepository;
import es.jccm.edu.gestionidentidades.application.domain.Persona;
import es.jccm.edu.gestionidentidades.application.ports.in.GeneraNewLoginUseCase;

@Service
public class GeneradorLoginService implements GeneraNewLoginUseCase {
	private Log log = LogFactory.getLog(GeneradorLoginService.class);
	
	@Autowired
	UsuariosRepository usuarioRepository;

	@Value("${politicaGeneracionLogin.longitudMinima}")
	private int loginLongitudMinima;
	
	@Value("${politicaGeneracionLogin.longitudMaxima}")
	private int loginLongitudMaxima;

	/* (non-Javadoc)
	 * @see es.jccm.edu.accesoeducamos.application.service.GeneradorLogin#creaLogin(sadiel.rayuela.accesopapas.credencialesacceso.generadorLogin.DatosPersonales)
	 */
	@Override
	public String genera(Persona persona) {

		String iniciales = cadenaSinTildes(inicialesDePalabras(persona.getNombre().toLowerCase()));
		String apellidoJunto = cadenaSinTildes(palabrasJuntas(persona.getApellido1().toLowerCase()));
		String primeraLetraSegundoApellido = primeraLetra(persona.getApellido2());

		String loginSinNumero = iniciales+apellidoJunto+primeraLetraSegundoApellido;
		
		loginSinNumero=limpiarCaracteresNoAlfanumericos(loginSinNumero);
		loginSinNumero = ajustarATamanoDePoliticaDeClaves(loginSinNumero);

		String loginUnico = agregarNumeroParaQueNoCoincidaConOtros(loginSinNumero);

		log.info("nuevo login generado: " + loginUnico);

		return loginUnico;
	}

	private String primeraLetra(String str) {
		String primeraLetra="";
		if(str!=null && !str.equals("")) {
		    primeraLetra = String.valueOf(str.toLowerCase().charAt(0));
		}
		primeraLetra = cadenaSinTildes(primeraLetra);
		return primeraLetra;
	}

	private String agregarNumeroParaQueNoCoincidaConOtros(String loginSinNumero) {
		String loginCreado;
		int contador = 0;

		do{
			contador++;
			if (contador<=9){
				loginCreado = loginSinNumero+'0'+contador;
			}
			else
			{
				loginCreado = loginSinNumero+contador;
			}

		}while (!usuarioRepository.findByLogin(loginCreado).isEmpty());
		return loginCreado;
	}

	private String palabrasJuntas(String str) {
		String apellidoJunto="";
		StringTokenizer stk2 = new StringTokenizer(str, " ");

		while(stk2.hasMoreTokens()) {
			String parte = stk2.nextToken();
			apellidoJunto += parte;
		}
		return apellidoJunto;
	}

	private String inicialesDePalabras(String str) {
		String iniciales = "";
		StringTokenizer stk1 = new StringTokenizer(str, " ");

		while(stk1.hasMoreTokens()) {
			String parte = stk1.nextToken();
			char primerCaracter = parte.charAt(0);
			iniciales += primerCaracter;
		}
		return iniciales;
	}

	public String limpiarCaracteresNoAlfanumericos(String str) {
		return str.replaceAll("[^a-z0-9]", "");
	}

	/** Devuelve el login ajustando su tamaño a la politica de gestión de credenciales
     * @param primeraLetraSegundoApellido
     * @param politica
     * @return
     */
    private String ajustarATamanoDePoliticaDeClaves(String loginSinNumero) {

        //El login está por debajo de la logitud mínima (se le pone +2 porque depues se añadirán dos cifras al login)
        while(loginSinNumero.length()+2<this.loginLongitudMinima) {
            loginSinNumero+="0";
        }

        //El login está por encima de la logitud máxima (se le pone -2 porque depues se añadirán dos cifras al login)
        while(loginSinNumero.length()+2>this.loginLongitudMaxima) {
            loginSinNumero = loginSinNumero.substring(0, loginSinNumero.length()-1);
        }

        return loginSinNumero;
    }


    private String cadenaSinTildes(String cadena) {
	    StringBuffer cadenaConvertida = new StringBuffer();
	    int longitud = cadena.length();
	    for(int i=0; i<longitud;i++ ) {
	        Character caracter = new Character(cadena.charAt(i));
	        if(caracter.toString().equals("á")) {
	            caracter=new Character('a');
	        }else if(caracter.toString().equals("é")) {
	            caracter=new Character('e');
	        }else if(caracter.toString().equals("í")) {
	            caracter=new Character('i');
	        }else if(caracter.toString().equals("ó")) {
	            caracter=new Character('o');
	    	}else if(caracter.toString().equals("ú")) {
	    	    caracter=new Character('u');
	    	}else if(caracter.toString().equals("'")) {
	    		caracter=null;
	    	}else if(caracter.toString().equals("`")) {
	    		caracter=null;
	    	}else if(caracter.toString().equals("´")) {
	    		caracter=null;
	    	}

	        if (caracter != null)
	        {
	        	cadenaConvertida.append(caracter);
	        }
	    }

	    return cadenaConvertida.toString();
	}
    
    
}
