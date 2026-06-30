package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TLNOTCALMAT")
@Data
@EqualsAndHashCode(callSuper = true)
public class EvaNotaGlobalAlumnoMateria extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TLS_NOTCALMAT")
    @SequenceGenerator(name = "TLS_NOTCALMAT", sequenceName = "TLS_NOTCALMAT", allocationSize = 1)
    @Column(name = "X_NOTCALMAT", nullable = false)
    private Long id;

    @Column(name = "X_MATMATRICULA", nullable = false)
    private Long idMatMatricula;

    @Column(name = "X_CALIFICA")
    private Long idCalifica;

    @Column(name = "X_CONVCENTROOMC")
    private Long idConvCentroOmc;

    @Column(name = "N_NOTA", precision = 4, scale = 2)
    private Double nota;
}

