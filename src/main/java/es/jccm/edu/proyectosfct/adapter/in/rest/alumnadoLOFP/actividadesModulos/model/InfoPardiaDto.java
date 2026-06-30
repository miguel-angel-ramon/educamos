package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.actividadesModulos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@Schema(name = "InfoPardiaDto", description = "Información sobre el parte diario")
public class InfoPardiaDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name="horas")
    private Integer horas;

    @Column(name="observaciones")
    private String observaciones;

}
