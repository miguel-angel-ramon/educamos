package es.jccm.edu.alumnos.application.domain.historicoDomiciliosAlumno;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table (name="TLMUNICIPIOS")
@IdClass (HMunicipioId.class)
public class HMunicipio implements Serializable {
	private static final long serialVersionUID=1L;
	
	@Id
	@Column(name="C_MUNICIPIO")
	private Long idMunicipio;
	
	@Id
	@Column(name="C_PROVINCIA")
	private Long idProvincia;
	
	@Column (name="D_MUNICIPIO")
	private String descripcion;
	
}
