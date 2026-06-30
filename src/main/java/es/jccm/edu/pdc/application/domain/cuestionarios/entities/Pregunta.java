package es.jccm.edu.pdc.application.domain.cuestionarios.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class Pregunta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idPregunta;

	private String titulo;

	private String descripcion;

	private Long orden;

	private String obligatoria;

	private String permiteTexto;

	private String codTipo;

	private String tipo;

	@OneToMany(mappedBy = "idRespuesta")
	private List<Respuesta> listaRespuesta;

}
