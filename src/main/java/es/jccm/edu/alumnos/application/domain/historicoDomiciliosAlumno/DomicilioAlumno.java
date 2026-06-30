package es.jccm.edu.alumnos.application.domain.historicoDomiciliosAlumno;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="tlaludomicilios")
public class DomicilioAlumno implements Serializable{
	private static final long serialVersionUID=1L;
	
	@Id
	@Column(name="X_ALUDOMICILIO")
	private Long id;
	
	@Column(name="X_ALUMNO")
	private Long idAlumno;
	
	@OneToOne
	@JoinColumn(name="X_FAMILIA")
	private FamiliaAlumno familia;
	
	
	@ManyToOne (optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="X_TIPOVIA")
	private HTipoVia tipoVia;
		
	@ManyToOne (optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="X_LOCALIDAD")
	private HLocalidad localidad;
	
	
	@ManyToOne (optional=false, fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="C_MUNICIPIO"),
		@JoinColumn(name="C_PROVINCIA"  ) })
	private HMunicipio municipio;
	

	@ManyToOne (optional=false, fetch=FetchType.LAZY)
		@JoinColumn(name="C_PROVINCIA"   , insertable=false, updatable=false)
	private HProvincia  provincia;
	
	@Column(name="T_CALLE")
	private 	String calle;
	
	@Column(name="T_NUMCALLE")
	private String numeroCalle;
	
	@Column(name="T_ESCALERA")
	private String  escalera;
	
	@Column(name="T_PISO")
	private 	String piso;
	
	@Column(name="T_LETRA")
	private String letra;
	
	@Column (name="C_POSTAL")
	private String cPostal;
	
	@Column(name="F_DOMICILIO")
	private Date fechaDomicilio;
	
	
	
	
	
	
	

	

}
