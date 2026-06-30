package es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "Altas Seguridad Social", description = "Tabla que guarda los valores de un alumno de alta en la Seguridad Social")
public class AltasSegSociProgDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema (name = "Id de la tabla")
    private Long id;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
    @Schema(name = "Fecha prevista finalización de prácticas")
    private Date fechaFin;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
    @Schema(name = "Fecha de inicio de las practicas")
    private Date fechaInicio;

    @Schema(name = "1 para el caso de el alumno/a con erasmus con beca 0 en otro caso")
    private Integer lgErasmusCb;

    @Schema(name = "1 para el caso de validado alta por parte de la delegacion 0 en otro caso")
    private Integer lgValDel;

    @Schema(name = "1 para el caso de validado alta por parte del tutor 0 en otro caso")
    private Integer lgValTut;
    
    @Schema(name = "1 para el caso de el alumno/a con erasmus sin beca 0 en otro caso")
    private Integer lgErasmusSb;

    @Schema(name = "1 para el caso de validado alta por parte del centro 0 en otro caso")
    private Integer lgValCen;

    

}
