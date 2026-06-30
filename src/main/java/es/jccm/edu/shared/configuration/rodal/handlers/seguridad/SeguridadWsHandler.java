package es.jccm.edu.shared.configuration.rodal.handlers.seguridad;

import java.net.URL;
import java.util.Collections;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.ProtocolException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//TODO Quitar este import
import es.jccm.edu.proyectosfct.application.configuration.rodal.handlers.seguridad.ConfigSeguridadWs;

public class SeguridadWsHandler implements SOAPHandler<SOAPMessageContext> {
	/**
     * Logger for this class
     */
    private static final Logger LOGGER = LogManager.getLogger(SeguridadWsHandler.class);

    public static final Integer VALUE_TIMEOUT_DEFAULT = Integer.valueOf(20000);

    private ConfigSeguridadWs config;
    
    public SeguridadWsHandler(ConfigSeguridadWs config){
    	this.config=config;
    }

    @Override
    public void close(MessageContext arg0) {
    	//Do nothing
    }

    @Override
    public Set<QName> getHeaders() {

        return Collections.emptySet();
    }

    @Override
    public boolean handleFault(SOAPMessageContext arg0) {

        return true;
    }


    @Override
    public boolean handleMessage(SOAPMessageContext smc) {

        boolean enviando = ((Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)).booleanValue();
        boolean retorno = true;

        if(enviando) {
            try {

                // Establecemos la securización.... si es que la hay
                if((config.getSecurizacion() != null) && (!config.getSecurizacion().equals(""))) {
                    if(config.getSecurizacion().equals("WSSE")) {

                        LOGGER.info("ESTABLECIENDO WSSE HEADER");
                        this.addWsseSecurityHeader(smc);

                    }else if(config.getSecurizacion().equals("BASIC")) {
                        LOGGER.info("ESTABLECIENDO BASIC");
                        this.addHttpBasicAuthorization(smc);
                    }else if(config.getSecurizacion().equals("NONE")) {
                        LOGGER.info("SIN SECURIZACIÓN");
                    }else {
                        LOGGER.error("SECURIZACIÓN NO SOPORTADA");
                    }
                }

                if(isNotEmpty(config.getUrlBase())) {
                    URL urlEndpointWsdl = new URL((String) smc.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
                    String urlENDPOINT = config.getUrlBase();
                    urlENDPOINT = setUrlEndpoint(urlEndpointWsdl, urlENDPOINT);
                    smc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                            urlENDPOINT);
                }else if(isNotEmpty(config.getUrlEndpoint())) {
                    smc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                            config.getUrlEndpoint());
                }

                setTimeoutDeMultiplesFormas(smc);

            }catch (Exception e) {

                LOGGER.fatal("ERROR INVOCANDO AL WEB SERVICE",
                             e);
                throw new ProtocolException("Error al configurar la llamada al servicio web", e);
            }
        }
        return retorno;
    }

	/**
	 * @param urlEndpointWsdl
	 * @param urlENDPOINT
	 * @return
	 */
	private String setUrlEndpoint(URL urlEndpointWsdl, String urlENDPOINT) {
		urlENDPOINT += (urlEndpointWsdl.getPath() == null ? "" : urlEndpointWsdl.getPath())
		        + (urlEndpointWsdl.getQuery() == null ? "" : "?" + urlEndpointWsdl.getQuery());
		return urlENDPOINT;
	}

    private static boolean isNotEmpty(String str) {
    	return ! (str==null || "".equals(str.trim()));
    }
    
    public static final java.lang.String CLIENT_REQUEST_TIMEOUT = "javax.xml.ws.client.receiveTimeout";
    public static final java.lang.String CLIENT_CONNECT_TIMEOUT = "javax.xml.ws.client.connectionTimeout";
    
    public static final java.lang.String CLIENT_REQUEST_TIMEOUT_OLD = "com.sun.xml.ws.request.timeout";
    public static final java.lang.String CLIENT_CONNECT_TIMEOUT_OLD = "com.sun.xml.ws.connect.timeout";
    
    public static final java.lang.String CLIENT_REQUEST_TIMEOUT_OLD2 = "com.sun.xml.internal.ws.request.timeout";
    public static final java.lang.String CLIENT_CONNECT_TIMEOUT_OLD2 = "com.sun.xml.internal.ws.connect.timeout";
    
    public static final java.lang.String CLIENT_REQUEST_TIMEOUT_OLD3 = "sun.net.client.defaultReadTimeout";
    public static final java.lang.String CLIENT_CONNECT_TIMEOUT_OLD3 = "sun.net.client.defaultConnectTimeout";
    
    private static final String[] timeouts = {
    		CLIENT_REQUEST_TIMEOUT,
            CLIENT_CONNECT_TIMEOUT,
            CLIENT_REQUEST_TIMEOUT_OLD,
            CLIENT_CONNECT_TIMEOUT_OLD,
            CLIENT_REQUEST_TIMEOUT_OLD2,
            CLIENT_CONNECT_TIMEOUT_OLD2,
            CLIENT_REQUEST_TIMEOUT_OLD3,
            CLIENT_CONNECT_TIMEOUT_OLD3
    };
    
	private void setTimeoutDeMultiplesFormas(SOAPMessageContext smc) {
		for(String timeout:timeouts) {
			smc.put(timeout,config.getTimeout());
		}
	}

    private void addWsseSecurityHeader(SOAPMessageContext smc) throws SOAPException {
        SOAPHeader header = smc.getMessage().getSOAPPart().getEnvelope().getHeader();
        if(header == null) {
            header = smc.getMessage().getSOAPPart().getEnvelope().addHeader();
        }
        SOAPElement security = header.addChildElement("Security",
                                                      "wsse",
                                                      "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");

        SOAPElement usernameToken = security.addChildElement("UsernameToken",
                                                             "wsse");
        usernameToken.addAttribute(new QName("xmlns:wsu"),
                                   "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

        SOAPElement usernameElem = usernameToken.addChildElement("Username",
                                                                 "wsse");
        usernameElem.addTextNode(config.getUsuario());

        SOAPElement passwordElem = usernameToken.addChildElement("Password",
                                                                 "wsse");

        passwordElem.addAttribute(new QName("Type"),
                                  "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
        passwordElem.addTextNode(config.getPassword());

    }

    private void addHttpBasicAuthorization(SOAPMessageContext smc) {
        smc.put(BindingProvider.USERNAME_PROPERTY,
                config.getUsuario());
        smc.put(BindingProvider.PASSWORD_PROPERTY,
                config.getPassword());

    }
}
