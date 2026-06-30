package es.jccm.edu.pdc.application.domain.cuestionarios.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class ValoresAmbitoCinco implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private Long countNivel;
	private String nivel;
	private String subdimension;



}