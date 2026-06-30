package es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="FCT_TUTORFCTDUAL")
public class TutorFctDual implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_TUTORFCTDUAL")
	private Long id;

	@Column(name="F_BAJA")
	private Date fechaBaja;

	@Column(name="F_INITUTORIA")
	private Date fechaInicioTutoria;
	
	// ---------- Relationships -----------	
	
	//bi-directional many-to-one association to Tlptotraemp
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="F_TOMAPOS", referencedColumnName="F_TOMAPOS"),
		@JoinColumn(name="X_EMPLEADO", referencedColumnName="X_EMPLEADO")
		})
	private PuestoTrabajoEmpleado puestoTrabajoEmpleado;

}