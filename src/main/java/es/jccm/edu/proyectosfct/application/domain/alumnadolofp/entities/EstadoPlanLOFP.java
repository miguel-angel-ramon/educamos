package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "FCT_VALIDACIONES_ALUPLAN")
public class EstadoPlanLOFP extends BaseAudited implements Serializable {

private static final long serialVersionUID = 1L;

@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_VALIDACIONES_ALUPLAN")
@SequenceGenerator(name = "SQ_FCT_VALIDACIONES_ALUPLAN", sequenceName = "SQ_FCT_VALIDACIONES_ALUPLAN", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
@Column(name = "ID_VALIDACIONES_ALUPLAN")
private Long id;

@Column(name = "X_MATRICULA")
private Long matricula;

@Column(name = "DS_ESTADO")
private String estado;

@Column(name = "NU_ORDEN")
private Integer orden;

@Column(name = "F_REGISTRO")
private Date fechaRegistro;

@Column(name = "X_USUARIO")
private Long usuario;

@Column(name = "CD_VISTA")
private String vista;

}
