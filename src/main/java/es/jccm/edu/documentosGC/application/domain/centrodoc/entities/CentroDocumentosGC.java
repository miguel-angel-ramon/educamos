package es.jccm.edu.documentosGC.application.domain.centrodoc.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name="TLCENTROS")
public class CentroDocumentosGC implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_CENTRO")
	private Long id;

	@NotBlank
	@Column(name="C_CODIGO")
	private Long codigoCentro;
	
	// ---------- Relationships -----------
	
}