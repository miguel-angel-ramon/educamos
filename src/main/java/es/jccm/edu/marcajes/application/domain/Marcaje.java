package es.jccm.edu.marcajes.application.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "TLMARCAJES")
public class Marcaje implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@SequenceGenerator(name="generator", sequenceName="TLS_MARCXMARCAJE", allocationSize = 1, schema="DELPHOS")
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")


	@Column(name="X_MARCAJE", unique=true, nullable=false, precision=12, scale=0)
	private Long idMarcaje;

	@NotBlank
	@Column(name = "IDEVENTO")
	private String idEvento;
	
	@NotBlank
	@Column(name = "URLMARCAJE")
	private String urlMarcaje;
	
	@Column(name = "FECHAENTRADA")
	private Date fechaEntrada;
	
	
	
}
