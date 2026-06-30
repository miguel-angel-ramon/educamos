package es.jccm.edu.shared.configuration.context;

import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import es.jccm.edu.shared.application.domain.revisionAudit.CustomRevisionEntity;


public class CustomRevisionEntityListener implements RevisionListener {

    @Autowired
    private SecurityContextHolder securityContextHolder;

	@Override
	public void newRevision(Object revisionEntity) {
		
		CustomRevisionEntity customRevisionEntity = (CustomRevisionEntity) revisionEntity;
		customRevisionEntity.setUsername(securityContextHolder.getContext().getAuthentication().getName());
	}
}
