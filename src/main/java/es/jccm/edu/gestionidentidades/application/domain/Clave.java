package es.jccm.edu.gestionidentidades.application.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Data
public class Clave implements Serializable {
    
	private String clave;
	
	private GregorianCalendar fechaCaducidad;

	/**
	 * Aumenta la fecha de caducidad de la password un número de meses
	 * @param numeroMeses - Indica el número de mese a aumnetar la fecha de caducidad de la clave
	 * @return GregorianCalendar - Devuelve la nueva fecha de caducidad 
	 */
	public void renuevaFechaCaducidadEnMeses(int numeroMeses) {
		this.fechaCaducidad.add(Calendar.MONTH,numeroMeses);
	}
	
    public void renuevaFechaCaducidadEnDias(int numeroDias) {
		this.fechaCaducidad.add(Calendar.DAY_OF_MONTH,numeroDias);
    }

	/**
	 * Informa sobre si la clave está caducada o no 
	 * @author jaarjona
	 * @return void
	 */
	public boolean esCaducada() {
		if (this.fechaCaducidad != null) {
			GregorianCalendar calendar = new GregorianCalendar();
			return fechaCaducidad.after(calendar) ? false : true;
		}

		//No tiene fecha de caducidad
		return false;
	}

}
