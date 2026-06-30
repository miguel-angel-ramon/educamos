package es.jccm.edu.documentosGC.application.domain.registrodocumentosarte;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@IdClass(RegSelDocArtePK.class)
@Table(name="TLREGSELDOC")
public class RegSelDocArte implements Serializable{
private static final long serialVersionUID = 1L;
@Id
@Column(name="X_IDENTIFICADOR")
private Long idIdentificador;

@Id
@Column(name="N_CLAVE1")
private Long idClave1;

@Column(name="C_CLAVE1")
private String codigoClave1;

@Column(name="N_CLAVE2")
private Long idClave2;

@Column(name="C_CLAVE2")
private String codigoClave2;

@Column(name="D_CLAVE2")
private Date fechaClave2;

@Column(name="N_RANGODESDE")
private Long rangoDesde;

@Column(name="N_RANGOHASTA")
private Long rangoHasta;

@Column(name="D_RANGODESDE")
private Date fechaRangoDesde;

@Column(name="D_RANGOHASTA")
private Date fechaRangoHasta;	

@Column(name="C_DATOADICIONAL1")
private String codigoDatoAdicional1;		

@Column(name="C_DATOADICIONAL2")
private String codigoDatoAdicional2;		

@Column(name="C_DATOADICIONAL3")
private String codigoDatoAdicional3;			
}
