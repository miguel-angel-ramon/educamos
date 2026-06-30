package es.jccm.edu.buzon.application.domain.solicitudCorreoAlumno;

import java.util.Date;

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
@Table(name = "SOLICITUD_CORREO_ALUMNADO", schema = "DELPHOS_MODACC")

public class SolicitudCorreoAlumno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_MODACC.SEQ_SOLICITUD_CORREO_ALUMNADO")
    @SequenceGenerator(name = "DELPHOS_MODACC.SEQ_SOLICITUD_CORREO_ALUMNADO", sequenceName = "DELPHOS_MODACC.SEQ_SOLICITUD_CORREO_ALUMNADO", allocationSize = 1)
    @Column(name = "ID_SOLICITUD_CORREO_ALUMNADO")
    private Long idSolicitud;
	
	@Column(name = "ID_OFERTAMATRIG")
    private Long idOfertamatricCurso;
	
	@Column(name = "ID_CENTRO")
    private Long idCentro;
	
	@Column(name = "F_SOLICITUD")
    private Date fechaSolicitud;	
	
	@Column(name = "ID_ESTADO")
    private Long estadoSolicitud;
	
}
