package es.jccm.edu.shared.configuration.rodal.handlers.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogSoapHandler implements SOAPHandler<SOAPMessageContext> {
	
	private static final String ERROR = "ERROR";
    /**
     * Logger for this class
     */
    private static final Logger LOGGER = LogManager.getLogger(LogSoapHandler.class);

    @Override
    public void close(MessageContext arg0) {
    	//Do nothing
    }

    @Override
    public boolean handleFault(SOAPMessageContext arg0) {
        return false;
    }

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    @Override
    public boolean handleMessage(SOAPMessageContext smc) {
        boolean enviando = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        try {
            if(enviando) {
                LOGGER.info("ENVIANDO");
                if(LOGGER.isInfoEnabled()) {
                    SOAPMessage msg = smc.getMessage();
                    String endpointAddress = (String) smc.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
                    LOGGER.log(Level.ALL, "endpointAddress {0}", endpointAddress);

                    // get SOAP-Part
                    dumpSOAPMessage(msg);
                }
            }else {
                LOGGER.info("RECIBIENDO");
                if(LOGGER.isInfoEnabled()) {
                    SOAPMessage msg = smc.getMessage();
                    // get SOAP-Part
                    dumpSOAPMessage(msg);
                }
            }
            if(LOGGER.isInfoEnabled()) {
                smc.getMessage().writeTo(null);
            }
        }catch (SOAPException|IOException e) {
            LOGGER.error(ERROR,
                         e);
        }

        return true;

    }

    private void dumpSOAPMessage(SOAPMessage msg) {
    	
        if(msg == null) {
        	LOGGER.log(Level.ALL, "SOAP Message is null");
            return;
        }
        LOGGER.log(Level.ALL, "");
        LOGGER.log(Level.ALL, "--------------------");
        LOGGER.log(Level.ALL, "DUMP OF SOAP MESSAGE");
        LOGGER.log(Level.ALL, "--------------------");
        
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            msg.writeTo(baos);
            String message = baos.toString(getMessageEncoding(msg));
            LOGGER.log(Level.ALL, message);
            // show included values
            String values = msg.getSOAPBody().getTextContent();
            LOGGER.log(Level.ALL, "Included values: {0}", values);
        }catch (Exception e) {
            LOGGER.error(ERROR,
                         e);
        }
    }

    private String getMessageEncoding(SOAPMessage msg) throws SOAPException {
        String encoding = "utf-8";
        if(msg.getProperty(SOAPMessage.CHARACTER_SET_ENCODING) != null) {
            encoding = msg.getProperty(SOAPMessage.CHARACTER_SET_ENCODING).toString();
        }
        return encoding;
    }

}
