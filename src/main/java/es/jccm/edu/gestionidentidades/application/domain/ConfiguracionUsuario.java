package es.jccm.edu.gestionidentidades.application.domain;

import javax.persistence.CascadeType;
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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CONF_USUARIOS" , schema = "DELPHOS_MODACC")
public class ConfiguracionUsuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_MODACC.SEQ_CONF_USUARIOS")
	@SequenceGenerator(name = "DELPHOS_MODACC.SEQ_CONF_USUARIOS", sequenceName = "DELPHOS_MODACC.SEQ_CONF_USUARIOS", allocationSize = 1)
    @Column(name = "OID_CONF_USUARIOS")
	private Long oidConfUsuarios;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "OID_USUARIO", referencedColumnName="OID")
	private Usuario oidUsuario;
	
	@Column(name = "DEFAULT_VIEW")
	private String defaultView;
	
	@Column(name = "DEFAULT_ROL")
	private String defaultRol;
	
	@Column(name = "DEFAULT_CEN")
	private String defaultCen;
	
	@Column(name = "LG_SKIPTOUR")
	private String lgSkipTour;
	
	@Column(name = "LG_ACCPILOTO")
	private String lgAccPiloto;

}
