package es.jccm.edu.gestionidentidades.application.domain;

import java.util.Date;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIOS_REASIGNA_CLAVE" , schema = "DELPHOS_MODACC")
public class UsuariosReasignaClave {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_MODACC.SEQ_USUARIOS_REASIGNA_CLAVE")
	@SequenceGenerator(name = "DELPHOS_MODACC.SEQ_USUARIOS_REASIGNA_CLAVE", sequenceName = "DELPHOS_MODACC.SEQ_USUARIOS_REASIGNA_CLAVE", allocationSize = 1)
    @Column(name = "OID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "OID_USUARIO", referencedColumnName="OID")
	private Usuario usuario;
	
	@Column(name = "T_CLAVE_ANTERIOR")
	private String viejaClave;
	
	@Column(name = "T_CLAVE_REASIGNADA")
	private String nuevaClave;
	
	@Column(name = "F_REASIGNADA")
	private Date fModificacion;
	
	@Column(name = "C_USUCREACION")
	private Long usuarioCreacion;
	
	@Column(name = "F_CREACION")
	private Date fCreacion;

}
