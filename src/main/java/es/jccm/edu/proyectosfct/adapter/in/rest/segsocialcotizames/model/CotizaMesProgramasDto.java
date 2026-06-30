package es.jccm.edu.proyectosfct.adapter.in.rest.segsocialcotizames.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "CotizaMesProgramaDto", description = "")
public class CotizaMesProgramasDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "Id cotiza mes proyecto")
    private Long idCotizaMes;

    @Schema(name = "NU_MES")
    private Integer nuMes;

    @Schema(name = "DS_WARNINGS")
    private String warnings;

    @Schema(name = "NU_DIAS_REAL")
    private Integer nuDiasReal;

    @Schema(name = "NU_DIAS_INTE")
    private Integer nuDiasInte;

    @Schema(name = "NU_DIAS_NACU")
    private Integer nuDiasNacu;

    @Schema(name = "1 para el caso de validado alta por parte del tutor 0 en otro caso")
    private Integer lgValTut;

    @Schema(name = "1 para el caso de validado alta por parte del centro 0 en otro caso")
    private Integer lgValCen;

    @Schema(name = "1 para el caso de validado alta por parte de la delegacion 0 en otro caso")
    private Integer lgValDel;
    
    @Schema(name = "1 para el caso de enviado a la SS")
    private Integer lgEnvioEmp;
    
    @Schema(name = "Fecha de envio")
    private Date fechaEnvio;

    @Schema(name = "Numero de dias incapacidad alumno con beca")
    private Integer nuDiasInteEra;

}
