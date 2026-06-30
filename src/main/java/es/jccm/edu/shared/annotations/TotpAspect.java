package es.jccm.edu.shared.annotations;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import es.jccm.edu.shared.application.ports.in.totp.generar.GenerarDobleAutenticacionIn;
import es.jccm.edu.shared.application.ports.in.totp.generar.TotpGenErrorIn;
import es.jccm.edu.shared.application.ports.in.totp.generar.TotpGenRequestIn;
import es.jccm.edu.shared.application.ports.in.totp.validar.ValidarCodigoTotpIn;
import es.jccm.edu.shared.configuration.common.Constants;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class TotpAspect {

	private GetTokenJwt getTokenJwt;

    private GenerarDobleAutenticacionIn generar;
    private ValidarCodigoTotpIn validar;

	public TotpAspect(GetTokenJwt getTokenJwt, 
			GenerarDobleAutenticacionIn generar, ValidarCodigoTotpIn validar) {
		super();
		this.getTokenJwt = getTokenJwt;
		this.generar = generar;
		this.validar = validar;
	}



	@Around("@annotation(totp)")
    public Object authorize(ProceedingJoinPoint joinPoint, Totp totp) throws Throwable {

        //BEFORE METHOD EXECUTION
		Long oid=getTokenJwt.getOidDeUsuarioDesdeTokenJwt();

	    		
        //String code = (String) joinPoint.getArgs()[1];
		String code=getCodigoFromHeadder();
        
        log.info("Se inicia comprobación autorización con doble factor TOTP");
        
        log.info("oid "+oid);
        log.info("code "+code);
        

        if (code == null || code.isBlank()) {
        	Optional<TotpGenErrorIn> error=generar.nuevaAutenticacion(
        			TotpGenRequestIn.builder()
        			.oidUsuario(oid)
        			.conAlgoQuePosees(totp.necesitaAlgoQuePosees())
        			.build());
        	if(error.isPresent()) {
        		 return new ResponseEntity<>("MISSING_EMAIL", HttpStatus.BAD_REQUEST);
        	}

            return new ResponseEntity<>("NEED_2FA_TOTP", HttpStatus.UNAUTHORIZED);
        }

        if (!validar.validarCodigoTotp(code, oid)) {
        	 //HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
             //response.sendError(HttpStatus.PRECONDITION_FAILED.value(), "Application Id Unknown!");
             //return null;
            return new ResponseEntity<>("TOTP_2FA_NO_VALID", HttpStatus.UNAUTHORIZED);
        }

        return joinPoint.proceed();

    }

	private String getCodigoFromHeadder() {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    String header = request.getHeader(Constants.TOTP2FA);
	    return header;
	}
}
