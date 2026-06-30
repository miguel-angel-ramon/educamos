package es.jccm.edu.documentosGC.application.domain.estadodoc.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="DGC_ESTADOS")
public class EstadoDocumentoGC {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_ESTADO")
	private Long id;
	
	@Column(name="DS_ABREV")
	private String dsAbrev;
	
	@Column(name="DS_NOMBRE")
	private String dsNombre;
	
	@Column(name="FH_INICIO")
	private Date fhInicio;
	
	@Column(name="FH_FIN")
	private Date fhFin;
	
	@Column(name="LG_FINAL")
	private Boolean lgFinal;
	
	@Column(name="TX_AVISO")
	private String txAviso;

}
