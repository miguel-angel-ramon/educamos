package es.jccm.edu.documentosGC.application.configuration.rodal;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

public class HandlerChainResolverDGC implements HandlerResolver {

	private List<Handler> handlerChainDGC;
	
	public HandlerChainResolverDGC(List<Handler> handlerChain) {
		this.handlerChainDGC = handlerChain;
	}

	@Override
	public List<Handler> getHandlerChain(PortInfo portInfo) {
		return handlerChainDGC;
	}

}
