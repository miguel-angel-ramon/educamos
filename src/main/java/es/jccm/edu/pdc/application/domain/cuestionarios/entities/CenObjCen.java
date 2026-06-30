package es.jccm.edu.pdc.application.domain.cuestionarios.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import es.jccm.edu.shared.application.domain.baseAudited.*;
import lombok.Data;

@Data
@Entity
@Table(schema= "DELPHOS_SEGEDU" ,name="TLPDCENOBJCEN")
public class CenObjCen extends BaseAudited {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_SEGEDU.TLS_OBJCXPDCENOBJCEN")
	@SequenceGenerator(name = "DELPHOS_SEGEDU.TLS_OBJCXPDCENOBJCEN", sequenceName = "DELPHOS_SEGEDU.TLS_OBJCXPDCENOBJCEN", allocationSize = 1)
	@Column(name="X_PDCENOBJCEN")
	private Long id;
	
	@Column(name="X_CENTRO")
	private Long centro;
	
	@Column(name="C_ANNO")
	private Long cAnno;
	
	@Column(name="X_OBJETIVO")
	private Long objetivo;
	
	@Column(name="L_ACTIVO")
	private String activo;
	
	@Column(name="D_OBJESPECIFICO")
	private String descripcion;
	
	@Column(name="N_ORDENPRES")
	private Long orden;
	
}
