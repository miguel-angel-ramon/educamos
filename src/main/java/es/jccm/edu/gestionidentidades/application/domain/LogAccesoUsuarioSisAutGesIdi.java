package es.jccm.edu.gestionidentidades.application.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "LOG_ACCESO_USUARIO_SISAUT" , schema = "DELPHOS_MODACC")
public class LogAccesoUsuarioSisAutGesIdi	extends BaseAudited implements Serializable{

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_MODACC.SEQ_LOG_ACCESO_USUARIO_SISAUT")
		@SequenceGenerator(name = "DELPHOS_MODACC.SEQ_LOG_ACCESO_USUARIO_SISAUT", sequenceName = "DELPHOS_MODACC.SEQ_LOG_ACCESO_USUARIO_SISAUT", allocationSize = 1)
	    @Column(name = "ID_LOG_ACCESO_USUARIO_SISAUT")
		private Long id;
		
		@Column(name = "ID_USUARIO")
	    private Long idUsuario;
		
		@Column(name = "TX_LOGIN")
	    private String login;
		
		@Column(name = "CD_SISTEMA_AUT")
	    private String sistemaAutenticacion;
		
		@Column(name = "N_ACCESOS")
	    private Integer numAccesos;
		
		@Column(name = "F_ULTIMO_ACCESO")
	    private Date fechaUltimoAcceso;
		
}
