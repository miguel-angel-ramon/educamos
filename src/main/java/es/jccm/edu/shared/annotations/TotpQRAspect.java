package es.jccm.edu.shared.annotations;

import com.google.zxing.WriterException;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.application.ports.out.totp.ITotpService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Aspect
@Component
public class TotpQRAspect {

    private final IDatosUsuarioJwtService datosUsuarioJwtService;

    private final ITotpService totpService;

    public TotpQRAspect(IDatosUsuarioJwtService datosUsuarioJwtService, ITotpService totpService) {
        this.datosUsuarioJwtService = datosUsuarioJwtService;
        this.totpService = totpService;
    }

    @Around("@annotation(TotpQR)")
    public Object authorize(ProceedingJoinPoint joinPoint) throws Throwable {

        //BEFORE METHOD EXECUTION
        String jwt = (String) joinPoint.getArgs()[0];
        String code = (String) joinPoint.getArgs()[1];

        DatosUsuarioJwt datosUsuarioJwt = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        if (code == null || code.isBlank()) {
            try {
                if (!totpService.enviarCodigoMailQR(datosUsuarioJwt.getOid(), datosUsuarioJwt.getEmail())) {
                    return new ResponseEntity<>("MISSING_EMAIL", HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>("NEED_2FA_TOTP", HttpStatus.UNAUTHORIZED);
            } catch (IOException | WriterException e) {
                throw new RuntimeException(e);
            }
        }

        if (!totpService.validarCodigoTotpQR(code, datosUsuarioJwt.getOid())) {
            return new ResponseEntity<>("TOTP_2FA_NO_VALID", HttpStatus.UNAUTHORIZED);
        }

        return joinPoint.proceed();

    }

}
