package es.jccm.edu.totp.application.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.jccm.edu.totp.application.domain.TotpCaduco;
import es.jccm.edu.totp.application.ports.in.ValidarCodigoTotpUC;
import es.jccm.edu.totp.application.ports.out.RandomTotpGenerator;
import es.jccm.edu.totp.application.ports.out.TotpCaducoRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TotpCaducoService implements ValidarCodigoTotpUC {
	
	private TotpCaducoRepository clave2faRepository;
	private RandomTotpGenerator randomTotpGenerator;
	
    @Value("${duracionEmail}")
	private int duracionEmail;
    
	public TotpCaducoService(TotpCaducoRepository clave2faRepository,RandomTotpGenerator randomTotpGenerator) {
		super();
		this.clave2faRepository = clave2faRepository;
		this.randomTotpGenerator = randomTotpGenerator;
	}

	@Override
	public boolean validarCodigoTotp(String codigo, long idUsuario) {
    	TotpCaduco totpcaduco = clave2faRepository.findByOid(idUsuario).orElseThrow();
    	totpcaduco=totpcaduco.toBuilder().duracion(duracionEmail).build();
    	
    	Date ahora = new Date();
        boolean resultado = totpcaduco.validar(codigo, ahora);
        
        /**
         * Se retira este update debido a que no se puede setear a nulo el código al introducir un código equivocado
         * ya que al intorducir después el código correcto en bbdd está a nulo y es incorrecto eternamente.
         * Además no se puede actualizar la fecha en cada intento erróneo ya que el código no caducaría nunca.
         */
        //clave2faRepository.update(totpcaduco);
        return resultado;
        														  
	}

	public TotpCaduco generaNuevoCodigoCaduco(long idUsuario) {
		String code = randomTotpGenerator.netNewRandomTotp();
	
		TotpCaduco totp=TotpCaduco.builder()
	    		.oidUsuario(idUsuario)
	    		.codigo2FA(code)
	    		.fechaGeneracionCodigo(new Date())
	    		.duracion(duracionEmail)
	    		.build();
	
	    clave2faRepository.update(totp);
		return totp;
	}
	

}
