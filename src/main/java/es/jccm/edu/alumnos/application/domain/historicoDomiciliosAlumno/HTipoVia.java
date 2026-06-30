package es.jccm.edu.alumnos.application.domain.historicoDomiciliosAlumno;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TLTIPOVIAS")
public class HTipoVia implements Serializable{
	private static final long serialVersionUID=1L;

	@Id
	@Column(name="X_TIPOVIA")
	private Long id;
	
	@Column(name="D_TIPOVIA")
	private String descripcion;

}
