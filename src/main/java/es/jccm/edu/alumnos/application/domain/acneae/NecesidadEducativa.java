package es.jccm.edu.alumnos.application.domain.acneae;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TLACNEES")
public class NecesidadEducativa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="X_ACNEES")
	private Long id;
	
	@Column(name="D_ACNEES")
	private String descripcionNEE;
	
	@Column(name="C_CLAVE")
	private String clave;
	
	@Column (name="C_CLAVE2")
	private String clave2;
	
	@Column(name="N_ORDEN")
	private int nOrden;
	
	@Column(name="C_ANNO_INICIO")
	private int annoInicio;
	
	@Column(name="C_ANNO_FIN")
	private Integer annoFin;
	
	@Column(name="L_APOYO")
	private String apoyo;
	
	@Column(name="L_ADAPTACION")
	private String adaptacion;
	
	@OneToMany(mappedBy="nEducativa")
	private List<MatriculaNEE> matricula;

}
