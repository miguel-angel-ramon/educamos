package es.jccm.edu.shared.configuration.rodal;

import java.util.Arrays;

import javax.xml.ws.soap.MTOMFeature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jccm.cstic.clientesws.clienterodal.Rodal2;
import com.jccm.cstic.clientesws.clienterodal.Rodal2ImplService;

import es.jccm.edu.proyectosfct.application.configuration.rodal.HandlerChainResolver;
import es.jccm.edu.proyectosfct.application.configuration.rodal.RodalConfig;
import es.jccm.edu.proyectosfct.application.configuration.rodal.handlers.seguridad.ConfigSeguridadWs;
import es.jccm.edu.proyectosfct.application.configuration.rodal.handlers.seguridad.SeguridadWsHandler;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Rodal2Inicialize {
	
	@Autowired
    private ConfigSeguridadWs configSeguridadWs;
	
    ;
	
    public Rodal2 getRodal2FCT(RodalConfig rodalConfig) {
		Rodal2ImplService service = new Rodal2ImplService();
	
		SeguridadWsHandler configWebService = new SeguridadWsHandler(configSeguridadWs);
		service.setHandlerResolver(new HandlerChainResolver(
				Arrays.asList(
						configWebService
						//,
						//new LogSoapHandler()
						)));
		return getPortRodal(service,rodalConfig.isMtomEnabled());
	}
    


	public static Rodal2 getPortRodal(Rodal2ImplService service, boolean mtomEnabled) {
        if(mtomEnabled) {
            log.debug("RODAL_MTOM_ENABLED");
            return service.getRodal2ImplPort(new MTOMFeature(true, 2048));
        }else {
            log.debug("RODAL_MTOM_DISABLED");
            return service.getRodal2ImplPort();
        }
    }

}
