package es.jccm.edu.documentosGC.application.domain.centrodoc.entities;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;


@Data
@Entity
public class CentroDocInspeccion implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long x_centro;


	private String descripcion;
	
	
}