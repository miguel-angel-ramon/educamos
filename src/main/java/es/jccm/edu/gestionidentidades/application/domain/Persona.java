package es.jccm.edu.gestionidentidades.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;

import java.io.Serializable;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "PERSONAS" , schema = "DELPHOS_MODACC")
@Audited
public class Persona extends BaseAudited implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_MODACC.SEQ_PERSONAS")
	@SequenceGenerator(name = "DELPHOS_MODACC.SEQ_PERSONAS", sequenceName = "DELPHOS_MODACC.SEQ_PERSONAS", allocationSize = 1)
    @Column(name = "OID")
	private Long id;
	
	@Column(name = "T_NOMBRE")
    private String nombre;
	
	@Column(name = "C_SEXO")
	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	
	@Column(name = "X_PERSONA")
	private Long xPersona;
	
	@Column(name = "T_APELLIDO1")
	private String apellido1;
	
	@Column(name = "T_APELLIDO2")
	private String apellido2;
	
	@Column(name = "F_NACIMIENTO")
	private Date fechaNacimiento;	
	

}
