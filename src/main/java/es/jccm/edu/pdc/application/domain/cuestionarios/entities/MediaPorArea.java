package es.jccm.edu.pdc.application.domain.cuestionarios.entities;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Data
@Entity
public class MediaPorArea implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	String nombreArea;
	
	String cNivel;

	Double media;
}
