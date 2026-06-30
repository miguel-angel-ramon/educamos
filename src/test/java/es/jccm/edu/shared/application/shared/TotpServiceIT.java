package es.jccm.edu.shared.application.shared;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.zxing.WriterException;

import es.jccm.edu.shared.application.services.totp.TotpService;
import es.jccm.edu.totp.application.domain.Cuenta2faGoogle;
import es.jccm.edu.totp.application.ports.out.Cuenta2faGoogleRepository;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class TotpServiceIT {

	@Autowired
	TotpService sut;
	
	public static final long oid=893991;

	
	@Test
	public void enviarCodigoNormal() {
		//TODO
		//sut.enviarCodigoMail(oid, "jmartind@jccm.es");
	}
	

}
