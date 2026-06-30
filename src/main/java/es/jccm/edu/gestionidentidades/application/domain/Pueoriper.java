package es.jccm.edu.gestionidentidades.application.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TLPUEORIPER" , schema = "DELPHOS")
public class Pueoriper implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "X_PUEORIPER")
	private Long id;
	
	@Column(name = "L_ASIGMAN")
	private String login;
	
	@Column(name = "X_EMPLEADO")
	private Long xEmpleado;
	
	@Column(name = "F_TOMAPOS")
	private Date fTomaPos;
	
	@Column(name = "X_USUARIO")
	private Long xUsuario;
	
	@Column(name = "X_PERFIL")
	private Long xPerfil;
	
	@Column(name = "C_USUCREACION")
	private Long usuCreacion;
	
	@Column(name = "F_CREACION")
	private Date fCreacion;
	
	@Column(name = "C_USUACTUALIZA")
	private Long usuActualiza;
	
	@Column(name = "F_ACTUALIZA")
	private Date fActualiza;
	
	@Column(name = "T_ORIREG")
	private String origeg;

}
