package es.jccm.edu.proyectosfct.application.domain.empresas;
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
@Table(name = "TLTIPOEMPRESA")
public class TipoEmpresa {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "X_TIPOEMPRESA")
	private Long id;

	@NotBlank
	@Column(name = "D_TIPOEMPRESA")
	private String descripcionTipo;

}
