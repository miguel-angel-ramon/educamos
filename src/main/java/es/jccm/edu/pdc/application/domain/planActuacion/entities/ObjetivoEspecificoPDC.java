package es.jccm.edu.pdc.application.domain.planActuacion.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class ObjetivoEspecificoPDC implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	Integer idAmbito;

	String descripcion;
	
	Integer porcentajeTotal;
	
	@OneToMany
	List<LineaActuacionPDC> actuaciones;
}