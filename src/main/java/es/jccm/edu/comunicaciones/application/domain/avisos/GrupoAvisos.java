package es.jccm.edu.comunicaciones.application.domain.avisos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "TLGRUAVI")
public class GrupoAvisos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "X_GRUAVI")
	private Integer idGrupoAvisos;

	@NotBlank
	@Column(name = "C_CLAVE")
	private String clave;

}
