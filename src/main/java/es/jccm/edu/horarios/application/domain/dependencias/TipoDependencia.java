package es.jccm.edu.horarios.application.domain.dependencias;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class TipoDependencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String nombre;

}