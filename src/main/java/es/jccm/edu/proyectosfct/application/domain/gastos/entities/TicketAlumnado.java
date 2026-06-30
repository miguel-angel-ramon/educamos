package es.jccm.edu.proyectosfct.application.domain.gastos.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
@Table(name="FCT_TICKETS_ALUMNADO")
public class TicketAlumnado extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticketalu_seq")
    @SequenceGenerator(name = "ticketalu_seq", sequenceName = "SQ_FCT_TICKETS_ALUMNADO", allocationSize = 1)
	@Column(name="ID_TICKET_ALUMNADO")
	private Long id;
	
	@Column(name="FH_REGISTRO")
	private Date fechaRegistro;

	@Column(name="ID_TICKETALU_RODAL")
	private String idTicketAluRodal;
	
	@Column(name="TX_TICKETALU_FICHERO")
	private String nombreFichero;
	
	// ---------- Relationships -----------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_GASTOS_ALUMNADO")
	private GastoAlumnado gastoAlumnado;
	
	@Column(name="X_USUARIO")
	private Long idUsuario;

}