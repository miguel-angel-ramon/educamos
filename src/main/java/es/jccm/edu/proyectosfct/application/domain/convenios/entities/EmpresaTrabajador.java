package es.jccm.edu.proyectosfct.application.domain.convenios.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import es.jccm.edu.proyectosfct.application.domain.empresas.Empresa;
import es.jccm.edu.proyectosfct.application.domain.empresas.SedeEmpresa;
import lombok.Data;

@Data
@Entity
@Table(name="EMP_TRAEMP")
public class EmpresaTrabajador implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_TRAEMP")
	private Long id;

//	@NotBlank
//	@Column(name="C_NUMIDE")
//	private String numeroDocumento;

//	@NotBlank
//	@Column(name="L_TIPIDE")
//	private String tipoDocumento;

	@NotBlank
	@Column(name="LG_REPEMP")
	private Boolean esRepresentante;
	
	@NotBlank
	@Column(name="LG_RESFCT")
	private Boolean esResponsable;

//	@NotBlank
//	@Column(name="TX_APELLIDO1")
//	private String apellido1;

//	@Column(name="TX_APELLIDO2")
//	private String apellido2;

//	@NotBlank
//	@Column(name="TX_NOMBRE")
//	private String nombre;

	@Column(name="DS_DEPARTAMENTO")
	private String departamento;


	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_EMPRESA")
	private Empresa empresa;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMPLEADO")
	private DatosEmpresaTrabajador datosEmpresaTrabajador;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SEDEMP")
	private SedeEmpresa sede;
	
	

	
}