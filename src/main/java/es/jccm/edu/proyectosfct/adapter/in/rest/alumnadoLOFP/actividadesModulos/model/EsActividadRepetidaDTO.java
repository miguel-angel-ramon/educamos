package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.actividadesModulos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@Schema(name = "EsActividadRepetidaDTO", description = "Listado con la validación de datos de actividad")
public class EsActividadRepetidaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name="esNombre")
    private String esNombre;

    @Column(name="esNuOrden")
    private String esNuOrden;

    @Column(name="esTxAbrev")
    private String esTxAbrev;

}
