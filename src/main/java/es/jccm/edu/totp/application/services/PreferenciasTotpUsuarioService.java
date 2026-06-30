package es.jccm.edu.totp.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import es.jccm.edu.totp.application.domain.MetodoDeAutenticacionDobleFactor;
import es.jccm.edu.totp.application.domain.PreferenciasUsuario;
import es.jccm.edu.totp.application.domain.TotpCaduco;
import es.jccm.edu.totp.application.ports.in.GetPreferenciasUsuarioUC;
import es.jccm.edu.totp.application.ports.in.ValidarCodigoTotpUC;
import es.jccm.edu.totp.application.ports.in.generar.GenerarDobleAutenticacion;
import es.jccm.edu.totp.application.ports.in.generar.TotpGenError;
import es.jccm.edu.totp.application.ports.in.generar.TotpGenRequest;
import es.jccm.edu.totp.application.ports.out.PreferenciasUsuarioRepository;

@Service
@Qualifier("preferenciasTotpUsuarioService")
public class PreferenciasTotpUsuarioService implements GetPreferenciasUsuarioUC, GenerarDobleAutenticacion, ValidarCodigoTotpUC {

	private TotpCaducoService totpCaducoService;
	private PreferenciasUsuarioRepository preferenciasUsuarioRepository;
	private TotpCaducoSenderMail totpCaducoSenderMail;


	public PreferenciasTotpUsuarioService(TotpCaducoService totpCaducoService,
			PreferenciasUsuarioRepository preferenciasUsuarioRepository, TotpCaducoSenderMail totpCaducoSenderMail) {
		super();
		this.totpCaducoService = totpCaducoService;
		this.preferenciasUsuarioRepository = preferenciasUsuarioRepository;
		this.totpCaducoSenderMail = totpCaducoSenderMail;
	}


	@Override
	public Optional<TotpGenError> nuevaAutenticacion(TotpGenRequest event) {

		PreferenciasUsuario preferencias=preferenciasUsuarioRepository.load(event.getOidUsuario()).orElseThrow();

		MetodoDeAutenticacionDobleFactor metodoAutenticacion = preferencias.getMetodoAutenticacionFavorito(event.isConAlgoQuePosees());

		if (event.isConAlgoQuePosees() && !metodoAutenticacion.isConAlgoQuePosees()) {
			return Optional.of(TotpGenError.METODO_AUTENTICACION_INSUFICIENTEMENTE_SEGURO);
		}
		if (metodoAutenticacion.isConCodigoCaduco()) { //necesita generar y mandar el código
			if(preferencias.getMail() == null) {
				return Optional.of(TotpGenError.NO_MAIL);
			}
			TotpCaduco nuevoCodigo=totpCaducoService.generaNuevoCodigoCaduco(event.getOidUsuario());
			
			boolean correcto = totpCaducoSenderMail.send(preferencias, nuevoCodigo);
			if(!correcto){
				return Optional.of(TotpGenError.NO_MAIL);
			}
		}
		return Optional.empty();
	}

	
	@Override
	public Optional<PreferenciasUsuario> getPreferenciasUsuario(long oid) {
		return preferenciasUsuarioRepository.load(oid);
	}

	@Autowired
	@Qualifier("totpCaducoService")
	ValidarCodigoTotpUC validadorCodigoCaduco;

	@Autowired
	@Qualifier("totpGoogleService")
	ValidarCodigoTotpUC validadorCodigosGoogle;
	
	@Override
	public boolean validarCodigoTotp(String codigo, long idUsuario) {
		PreferenciasUsuario preferencias=preferenciasUsuarioRepository.load(idUsuario).orElseThrow();
		
		if(preferencias.getMetodoAutenticacionFavorito()==null) {
			return false;
		}
		
		ValidarCodigoTotpUC validador=
				preferencias.getMetodoAutenticacionFavorito().isConCodigoCaduco()?
						validadorCodigoCaduco:validadorCodigosGoogle;
		
		return validador.validarCodigoTotp(codigo, idUsuario);
	}

}
