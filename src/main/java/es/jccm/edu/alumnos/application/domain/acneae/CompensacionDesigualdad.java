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
@Table(name="tlances")
public class CompensacionDesigualdad implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="X_ANCES")
	private Long id;
	
	@Column(name="D_ANCES")
	private String descripcion;
	
	@Column(name="C_CLAVE")
	private String clave;
	
	@Column(name="C_CLAVE2")
	private String clave2;
	
	@Column(name="N_ORDEN")
	private int nOrden;
	
	@Column(name="C_ANNO_INICIO")
	private Integer  annoInicio;
	
	@Column(name="C_ANNO_FIN")
	private Integer annoFin;
	
	@Column(name="L_EDITABLE")
	private String editable;
	
	
	@OneToMany(mappedBy="cEducativa")
	private List<MatriculaNEE> matricula;

	

}
