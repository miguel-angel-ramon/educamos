package es.jccm.edu.gestionidentidades.application.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(schema = "DELPHOS_MODACC", name = "APLICACIONES")
public class Aplicacion implements Serializable {

	private static final long serialVersionUID = 4290506597787017877L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_MODACC.SEQ_APLICACIONES")
    @SequenceGenerator(name = "DELPHOS_MODACC.SEQ_APLICACIONES", sequenceName = "DELPHOS_MODACC.SEQ_APLICACIONES", allocationSize = 1)
    @Column(name = "OID")
    private Long id;

    @Column(name = "C_CODIGO")
    private String codigo;

    @Column(name = "T_NOMBRE")
    private String nombre;

    @Column(name = "L_ACTIVA")
    private Character activa;

    @Column(name = "T_MOTIVO_DESACTIVACION")
    private String motivoDesactivacion;

    @Column(name = "INFORMACION")
    private String informacion;

    @Column(name = "D_NOMBRE")
    private String nombre2;

	public Aplicacion(Long id, String codigo) {
		super();
		this.id = id;
		this.codigo = codigo;
	}

	public Aplicacion() {
		super();
	}

}
