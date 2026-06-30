package es.jccm.edu.simulacion.application.domain.usuarios;


import lombok.Data;

import javax.persistence.*;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name="cursoAca")
@Table(name="TLCURSOACA")
public class CursoAcademico extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="C_ANNO")
    private Integer anno;

    @Column(name="F_INICIO")
    private Date fechaInicio;

    @Column(name="F_FINAL")
    private Date fechaFinal;

}
