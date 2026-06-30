package es.jccm.edu.documentosGC.application.domain.registrosdocumentosidi;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.Data;

@Data
@Entity
@IdClass(RegSelDocIDIPK.class)
@Table(name="TLREGSELDOC")
public class RegSelDocIDI implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="X_IDENTIFICADOR")
	private Long idIdentificador;
	
	@Id
	@Column(name="N_CLAVE1")
	private Long idClave1;
	
	@Column(name="C_CLAVE1")
	@ColumnDefault("null")
	private String codigoClave1;
	
	@Column(name="N_CLAVE2")
	@ColumnDefault("null")
	private Long idClave2;
	
	@Column(name="C_CLAVE2")
	@ColumnDefault("null")
	private String codigoClave2;
	
	@Column(name="D_CLAVE2")
	@ColumnDefault("null")
	private Date fechaClave2;
	
	@Column(name="N_RANGODESDE")
	@ColumnDefault("null")
	private Long rangoDesde;
	
	@Column(name="N_RANGOHASTA")
	@ColumnDefault("null")
	private Long rangoHasta;
	
	@Column(name="D_RANGODESDE")
	@ColumnDefault("null")
	private Date fechaRangoDesde;

	@Column(name="D_RANGOHASTA")
	@ColumnDefault("null")
	private Date fechaRangoHasta;	
	
	@Column(name="C_DATOADICIONAL1")
	@ColumnDefault("null")
	private String codigoDatoAdicional1;		
	
	@Column(name="C_DATOADICIONAL2")
	@ColumnDefault("null")
	private String codigoDatoAdicional2;		
	
	@Column(name="C_DATOADICIONAL3")
	@ColumnDefault("null")
	private String codigoDatoAdicional3;		

}
