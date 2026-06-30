package es.jccm.edu.gestionidentidades.application.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name = "APL_ACCESIBLES_USUARIO" , schema = "DELPHOS_MODACC")
public class AplicacionesAccesiblesUsuario extends BaseAudited {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_MODACC.SEQ_APL_ACCESIBLES_USUARIO")
	@SequenceGenerator(name = "DELPHOS_MODACC.SEQ_APL_ACCESIBLES_USUARIO", sequenceName = "DELPHOS_MODACC.SEQ_APL_ACCESIBLES_USUARIO", allocationSize = 1)
    @Column(name = "OID")
	private Long id;
	
	@Column(name = "OID_APLICACION")
	private Long idAplicacion;
	
	@Column(name = "OID_USUARIO")
	private Long idUsuario;
	
	@Column(name = "L_BLOQUEADO")
	private String bloqueado;

}
