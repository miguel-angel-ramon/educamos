package es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model;

import java.io.Serializable;
import java.util.List;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.miFormacionEmpresas.model.DatosFormacionEmpresaDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;

@Data
@Schema(name = "DatosFormacionDto", description = "Información completa para la formación del alumnado en empresas")
public class DatosFormacionDto implements Serializable{

	private static final long serialVersionUID = 1L;

	public DatosFormacionDto(String anno, String tutor, String curso, String unidad, String descripcion, List<DatosFormacionEmpresaDto> empresas) {
		this.anno = anno;
		this.tutor = tutor;
		this.curso = curso;
		this.unidad = unidad;
		this.descripcion = descripcion;
		this.empresas = empresas;
	}

	@Schema(description = "Curso académico")
	private String anno;

	@Schema(description = "Tutor del alumno")
	private String tutor;

	@Column(name="curso")
	private String curso;

	@Column(name="unidad")
	private String unidad;

	@Column(name="descripcion")
	private String descripcion;

	@Schema(description = "Empresa participantes en el plan")
	private List<DatosFormacionEmpresaDto> empresas;

}
