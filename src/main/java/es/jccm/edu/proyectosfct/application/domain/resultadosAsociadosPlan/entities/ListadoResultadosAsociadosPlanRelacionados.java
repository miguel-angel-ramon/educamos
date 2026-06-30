package es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities;

import lombok.Data;
import java.io.Serializable;

@Data
public class ListadoResultadosAsociadosPlanRelacionados implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_resultadoa_modulo;
    private Long x_comesp;
    private String abreviatura;
    private String descripcion;
    private Integer lgres;

}