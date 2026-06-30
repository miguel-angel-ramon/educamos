package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Entity
@Schema(name = "CriterioActividadAulaVirtualDTO", description = "DTO Criterio Actividad Aula Virtual")
public class CriterioActividadAulaVirtualDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
	private String itemname;
	private String shortname;
	private String itemtype;
	private String itemmodule;
	private Integer iteminstance;
	private Integer itemnumber;
	private Integer categoryid;
	private Integer outcomeid;
	private Integer scaleid;
	private boolean locked;
	private Integer cmid;
	private Integer graderaw;
	private Integer grademin;
	private Integer grademax;
}