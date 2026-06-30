package es.jccm.edu.alumnos.application.domain.historicoDomiciliosAlumno;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name="TLPROVINCIAS")
public class HProvincia implements Serializable {
	private static final long serialVersionUID=1L;

		@Id
		@Column(name="C_PROVINCIA")
		private Long id;
		
		@Column(name="D_PROVINCIA")
		private String descripcion;

}
