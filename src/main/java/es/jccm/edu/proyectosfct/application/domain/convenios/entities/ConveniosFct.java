package es.jccm.edu.proyectosfct.application.domain.convenios.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import es.jccm.edu.proyectosfct.application.domain.empresas.Empresa;
import es.jccm.edu.proyectosfct.application.domain.empresas.SedeEmpresa;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Centro;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Empleado;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_CONVENIOS")
public class ConveniosFct implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_CONVENIO")
	private Long id;

	@Column(name="F_FIN_VIGENCIA")
	private Date fechaFinVigencia;

	@Column(name="F_FIRMA")
	private Date fechaFirma;
	
	@Column(name="F_INICIO")
	private Date fechaInicio;
	
	@Column(name="F_PRORROGA")
	private Date fechaProrroga;

	@Column(name="CD_CONVENIO")
	private String numeroConvenio;
	
	@Column(name = "L_ACTIVO")
	private Boolean convenioFctActivo;	
	
	@Column(name = "F_FIRMA_PRORROGA")
	private Date fechaFirmaProrroga;
	
	@Column(name = "ID_CONFIR_RODAL")
	private String idConfirRodal;
	
	@Column(name = "TX_CONFIR_FICHERO")
	private String txConfirFichero;	
	
	@Column(name = "ID_CONPRO_RODAL")
	private String idConproRodal;
	
	@Column(name = "TX_CONPRO_FICHERO")
	private String txConproFichero;	
	
	@Column(name = "L_CONVANTE")
	private Integer lconvante;
	
	@Column(name = "LG_LOFP")
	private Integer lgLofp;
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_EMPRESA")
	private Empresa empresa;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_Centro")
	private Centro centro;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_REPRESENTANTE")
	private EmpresaTrabajador trabajador;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_EMPLEADO")
	private Empleado empleado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ID_SEDEMP")
	private SedeEmpresa id_sede;

}