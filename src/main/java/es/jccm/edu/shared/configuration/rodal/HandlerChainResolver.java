package es.jccm.edu.shared.configuration.rodal;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

public class HandlerChainResolver implements HandlerResolver {

	private List<Handler> handlerChain;
	
	public HandlerChainResolver(List<Handler> handlerChain) {
		this.handlerChain = handlerChain;
	}

	@Override
	public List<Handler> getHandlerChain(PortInfo portInfo) {
		return handlerChain;
	}

}
