package es.jccm.edu.shared.configuration.security.customsecuritymethod;

import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.utils.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    @Autowired
    private ApplicationContext ctx;

    private Object filterObject;
    private Object returnObject;
    private Object target;

    CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    void setThis(Object target) {
        this.target = target;
    }

    @Override
    public Object getThis() {
        return target;
    }

    public boolean hasSimulacionRole(String jwt) {
        IDatosUsuarioJwtService datosUsuarioJwtService = SpringContext.getBean(IDatosUsuarioJwtService.class);
        boolean b = datosUsuarioJwtService.hasSimulacionRole(jwt);
        return b;
    }
}
