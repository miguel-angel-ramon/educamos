package es.jccm.edu.alumnos.application.domain.alumnosHorario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "TLTELUSU")
public class TlefDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @NotBlank
    @Column(name = "X_TIPTEL")
    private Long idTipo;

    @NotBlank
    @Column(name = "T_TELEFONO")
    private String telefono;

}