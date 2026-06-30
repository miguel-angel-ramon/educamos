package es.jccm.edu.documentosGC.application.domain.documentosGC.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TLOFEMATRGEN")
public class OfertaCursoGenerico implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_OFERTAMATRIG")
	private Long id;
	
	@Column(name="D_OFERTAMATRIG")
	private String descripcion;

}
