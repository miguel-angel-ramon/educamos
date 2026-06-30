package es.jccm.edu.shared.application.domain.baseAudited;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Audited
public class BaseAudited {

	private static String noUser = "NO USER";
	/* Comunes */

	@CreatedBy
	@Column(name = "C_USUCREACION", updatable = false)
	private Long idUsuarioCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@Column(name = "F_CREACION", updatable = false)
	private Date fechaCreacion;

	@LastModifiedBy
	@Column(name = "C_USUACTUALIZA")
	private Long idUsuarioModificacion;

	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	@Column(name = "F_ACTUALIZA")
	private Date fechaModificacion;
}
