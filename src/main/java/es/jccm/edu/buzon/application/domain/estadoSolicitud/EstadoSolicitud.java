package es.jccm.edu.buzon.application.domain.estadoSolicitud;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "ESTADO_SOLICITUD", schema = "DELPHOS_MODACC")
public class EstadoSolicitud {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_MODACC.SEQ_ESTADO_SOLICITUD")
    @SequenceGenerator(name = "DELPHOS_MODACC.SEQ_ESTADO_SOLICITUD", sequenceName = "DELPHOS_MODACC.SEQ_ESTADO_SOLICITUD", allocationSize = 1)
    @Column(name = "ID_ESTADO")
    private Long idEstado;
	
	@Column(name = "DS_ESTADO")
    private String descripcionEstado;	
	
}
