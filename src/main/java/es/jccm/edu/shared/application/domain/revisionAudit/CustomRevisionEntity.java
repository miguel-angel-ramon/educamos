package es.jccm.edu.shared.application.domain.revisionAudit;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.RevisionEntity;

import es.jccm.edu.shared.configuration.context.CustomRevisionEntityListener;
import lombok.Data;

@Entity 
@Table(schema = "DELPHOS_MODACC", name = "REVINFO")
@RevisionEntity(CustomRevisionEntityListener.class)
@Data
public class CustomRevisionEntity extends CustomDefaultRevisionEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -30031032425528914L;
	
	
	private String username;
}
