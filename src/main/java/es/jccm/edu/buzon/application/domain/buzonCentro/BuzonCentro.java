package es.jccm.edu.buzon.application.domain.buzonCentro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ESC_BUZON_CENTROS", schema = "DELPHOS")
public class BuzonCentro {
	
	public BuzonCentro(Long oidPersona, Date fechaAlta, Date fechaBaja, String centro, Long usuarioCreacion,
			Long usuarioActualiza, Date fechaCreacion, Date fechaActualiza, Long lgEstado) {
		super();
		this.fechaCreacion = fechaCreacion;
		this.usuarioCreacion = usuarioCreacion;
		this.fechaBaja = fechaBaja;
		
		this.lgEstado = lgEstado;
		this.fechaAlta = fechaAlta;
		this.usuarioActualiza = usuarioActualiza;
		this.oidPersona = oidPersona;
		this.fechaActualiza = fechaActualiza;
		this.centro = centro;

	}
	
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS.SQ_ESC_BUZON_CENTROS")
    @SequenceGenerator(name = "DELPHOS.SQ_ESC_BUZON_CENTROS", sequenceName = "DELPHOS.SQ_ESC_BUZON_CENTROS", allocationSize = 1)
    @Column(name = "OID")
    private Long oid;
    	
    @Column(name = "OID_PERSONA", nullable = false)
    private Long oidPersona;

    @Column(name = "F_ALTA")
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    @Column(name = "F_BAJA")
    @Temporal(TemporalType.DATE)
    private Date fechaBaja;

    @Column(name = "CENTRO", length = 8)
    private String centro;

    @Column(name = "C_USUCREACION")
    private Long usuarioCreacion;

    @Column(name = "C_USUACTUALIZA")
    private Long usuarioActualiza;

    @Column(name = "F_CREACION")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @Column(name = "F_ACTUALIZA")
    @Temporal(TemporalType.DATE)
    private Date fechaActualiza;

    @Column(name = "LG_ESTADO")
    private Long lgEstado;


}
