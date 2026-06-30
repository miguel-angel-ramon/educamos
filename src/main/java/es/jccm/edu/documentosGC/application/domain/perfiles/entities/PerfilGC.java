package es.jccm.edu.documentosGC.application.domain.perfiles.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TLPERFILES")
public class PerfilGC {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_PERFIL")
	private Long id;
	
	@Column(name="D_PERFIL")
	private String descripcion;
	
	@Column(name="C_CODIGO")
	private String codigo;
	
	@Column(name="C_AMBVISCEN")
	private String ambito;

}
