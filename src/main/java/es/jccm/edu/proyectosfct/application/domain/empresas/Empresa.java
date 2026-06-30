package es.jccm.edu.proyectosfct.application.domain.empresas;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name = "TLEMPRESAS")
public class Empresa extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "X_EMPRESA")
	private Long id;

	@NotBlank
	@Column(name = "L_TIPIDE")
	private String tipoIdentificador;

	@NotBlank
	@Column(name = "C_NUMIDE")
	private String numIde;

	@NotBlank
	@Column(name = "D_EMPRESA")
	private String nombreEmpresa;

	@NotBlank
	@Column(name = "L_ACTIVA")
	private String empresaActiva;
	
	@Column(name = "N_TELEFONOS")
	private String telefonoContacto;
	
	@Column(name = "T_OBSERVACIONES")
	private String observaciones;
	
	@Column(name = "X_SECTOR")
	private Long idSectorProductivo;
	
	@Column(name = "T_WEB")
	private String paginaWeb;
	
	@Column(name = "L_PUBLICO")
	private String empresaPublica;	
	
	@Column(name = "L_SESCAM")
	private String sesCam;
	
	@Column(name = "L_SSCEN")
	private String ssCen;
}
