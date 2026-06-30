package es.jccm.edu.shared.annotations;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuditoriaEducamosCLMAspect {

    @Around(value = "@annotation(AuditoriaEducamosCLM)")
    public Object modifyResponse(ProceedingJoinPoint joinPoint) throws Throwable {

        Object response = joinPoint.proceed();

        if (!(response instanceof ResponseEntity)) {
            return response;
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String header = null;

        if (request.getHeader("id_log_auditoria_simulacion") != null) {
            header = new String("id_log_auditoria_simulacion");
        }

        if (request.getHeader("id_log_auditoria") != null) {
            header = new String("id_log_auditoria");
        }

        if (header == null) {
            return response;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.addAll(((ResponseEntity<?>) response).getHeaders());
        headers.add(header, request.getHeader(header));

        return new ResponseEntity<>(((ResponseEntity<?>) response).getBody(), headers, ((ResponseEntity<?>) response).getStatusCode());

    }

}
