package es.jccm.edu.shared.configuration.validadorescadenas;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas.CaracterAlfaNumericoObligatorio;
import es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas.CaracterEnheNoPermitido;
import es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas.CaracterEspacioNoPermitido;
import es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas.CaracterMayusculaObligatorio;
import es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas.CaracterMinusculaObligatorio;
import es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas.CaracterNumericoObligatorio;
import es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas.CaracterSimboloNoPermitido;
import es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas.CaracterSimboloPermitidoObligatorio;
import es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas.LongitudMaxima;
import es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas.LongitudMinima;
import es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas.ValidadorCadenas;
import es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas.ValidadorCadenasMultiple;


@Configuration
public class ValidadoresCadenasConfig {
	
	@Value("${politicaGeneracionClave.longitudMinima}")
	private int claveLongitudMinima;
	
	@Value("${politicaGeneracionClave.longitudMaxima}")
	private int claveLongitudMaxima;

	@Bean(name = "validadorCadenasClaves")
	public ValidadorCadenas validadorCadenasClaves() {
		return new ValidadorCadenasMultiple(
			Arrays.asList(
				 new LongitudMinima(claveLongitudMinima, "El campo contraseña tiene una longitud menor de la permitida. Longitud mínima" + " " + claveLongitudMinima)
				,new LongitudMaxima(claveLongitudMaxima, "El campo contraseña tiene una longitud mayor de la permitida. Longitud máxima" + " " + claveLongitudMaxima)
				,new CaracterEspacioNoPermitido("El campo contraseña no debe contener espacios")
				,new CaracterEnheNoPermitido("El campo contraseña no debe contener ñ o Ñ")
				,new CaracterNumericoObligatorio("El campo contraseña debe contener algún carácter numérico")
				,new CaracterAlfaNumericoObligatorio("El campo contraseña debe contener alguna letra")
				,new CaracterMayusculaObligatorio("El campo contraseña debe contener alguna mayúscula")
				,new CaracterMinusculaObligatorio("El campo contraseña debe contener alguna minúscula")
				,new CaracterSimboloPermitidoObligatorio("No se encontró ningún símbolo permitido")
				,new CaracterSimboloNoPermitido("El campo contraseña contiene caracteres no permitidos")
			));
	}
	
	//------------
	
	@Value("${politicaGeneracionLogin.longitudMinima}")
	private int loginLongitudMinima;
	
	@Value("${politicaGeneracionLogin.longitudMaxima}")
	private int loginLongitudMaxima;

	@Bean(name = "validadorCadenasLogin")
	public ValidadorCadenas validadorCadenasLogin() {
		return new ValidadorCadenasMultiple(
			Arrays.asList(
				 new LongitudMinima(claveLongitudMinima, "El campo usuario tiene una longitud menor de la permitida. Longitud míninima"	+ " " + loginLongitudMinima)
				,new LongitudMaxima(claveLongitudMaxima, "El campo usuario tiene una longitud mayor de la permitida. Longitud máxima" + " " + loginLongitudMaxima)
				,new CaracterNumericoObligatorio("El campo usuario debe contener algún carácter numérico")
				,new CaracterAlfaNumericoObligatorio("El campo usuario debe contener alguna letra")
			));
	}
	
}
