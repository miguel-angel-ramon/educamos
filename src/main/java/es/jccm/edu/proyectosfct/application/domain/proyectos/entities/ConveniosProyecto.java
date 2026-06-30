package es.jccm.edu.proyectosfct.application.domain.proyectos.entities;

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
import javax.persistence.Transient;

import es.jccm.edu.proyectosfct.application.domain.empresas.SedeEmpresa;
import org.hibernate.annotations.Formula;

import es.jccm.edu.proyectosfct.application.domain.convenios.entities.ConveniosFct;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.EmpresaTrabajador;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_CONV_PROY")
public class ConveniosProyecto implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_CONV_PROY")
	private Long id;
	
	@Column(name="FH_INICIO")
	private Date fechaIni;
	
	@Column(name="FH_FIN")
	private Date fechaFin;
	
	@Column(name="TX_OBSERVACIONES")
	private String observaciones;
	
	@Column(name="ID_ANEI_RODAL")
	private String idAneiRodal;
	
	@Column(name="TX_ANEI_FICHERO")
	private String txAneiFichero;
	
	//@Formula("(select count(*) from fct_convproy_anexos con where con.id_conv_proy = id_conv_proy)")
	@Transient
    private Integer nuAnexosII;
	
	@Column(name="CD_PROYECTO")
	private String codProyecto;

	@Column(name="NU_HORAS_TOTALES")
	private Integer nuHorasTotales;
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONVENIO")
	private ConveniosFct convenio;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROYECTO")
	private Proyectos proyecto;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SEDE_RESP")
	private SedeEmpresa sedeResp;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TRAEMP")
	private EmpresaTrabajador trabajador;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ID_SEDE")
	private SedeEmpresa sede;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_REPRESENTANTE")
	private EmpresaTrabajador representante;

}
