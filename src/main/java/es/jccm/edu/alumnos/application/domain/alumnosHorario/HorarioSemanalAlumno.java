package es.jccm.edu.alumnos.application.domain.alumnosHorario;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;

import lombok.Data;

@Data
@Entity
public class HorarioSemanalAlumno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id; // Este ID es ficticio, ya que tu consulta no tiene campo ID. Debes asignar uno válido si persistes esta entidad.

    @Column(name = "n_diasemana")
    private Integer n_DiaSemana;

    @Column(name = "x_tramo")
    private Long x_Tramo;

    @Column(name = "n_orden")
    private Integer n_Orden;

    @Column(name = "d_tramo")
    private String d_Tramo;

    @Column(name = "profesor")
    private String profesor;

    @Column(name = "n_inicio")
    private Integer n_Inicio;

}
