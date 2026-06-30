package es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities;

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
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ProgramaFct;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_CONV_PROG")
public class ConveniosProgramasFct implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_CONV_PROG")
	private Long id;
	
	@Column(name="FH_INICIO")
	private Date fechaIni;
	
	@Column(name="FH_FIN")
	private Date fechaFin;
	
	@Column(name="ID_ANEI_RODAL")
	private String idAneiRodal;
	
	@Column(name="TX_ANEI_FICHERO")
	private String txAneiFichero;
	
	//@Formula("(select count(*) from fct_convprog_anexos conv where conv.id_conv_prog = id)")
	@Transient
    private Integer nuAnexosII;

	@Column(name="NU_HORAS_TOTALES")
	private Integer nuHorasTotales;
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONVENIO")
	private ConveniosFct convenio;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROGRAMA")
	private ProgramaFct programa;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SEDE_RESP")
	private SedeEmpresa sedeResp;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TRAEMP")
	private EmpresaTrabajador trabajador;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SEDE")
	private SedeEmpresa sede;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_REPRESENTANTE")
	private EmpresaTrabajador representante;

}
