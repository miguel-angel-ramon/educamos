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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DOCUMENTACIONES" , schema = "DELPHOS_MODACC")
public class Documentaciones {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_MODACC.SEQ_DOCUMENTACIONES")
	@SequenceGenerator(name = "DELPHOS_MODACC.SEQ_DOCUMENTACIONES", sequenceName = "DELPHOS_MODACC.SEQ_DOCUMENTACIONES", allocationSize = 1)
    @Column(name = "OID")
	private Long id;
	
	//TODO Cambiar a entidad tipoDocumentacion
	@Column(name = "OID_TIPO_DOCUMENTACION")
	private Long tipoDocumentacion;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "OID_PERSONA", referencedColumnName="OID")
	private Persona persona;
	
	@Column(name = "T_IDENTIFICACION")
	private String identificacion;
	
	@Column(name = "X_PERSONA")
	private Long xPersona;

}
