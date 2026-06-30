package es.jccm.edu.gestionidentidades.adapter.out;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.tomcat.util.codec.binary.Base64;


public class CifradorMD5ISO88591  {

    private MessageDigest cifrador;
    
    public CifradorMD5ISO88591(){
        try {
            this.cifrador = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
            throw new IllegalStateException(nsae);
        }
    }

    public String cifra(String cadenaACifrar){

        byte[] bytesEncriptado;
        try {
            bytesEncriptado = cifrador.digest(cadenaACifrar.getBytes("ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }

        String cadenaCifrada = new String(Base64.encodeBase64(bytesEncriptado));

        return cadenaCifrada;
    }

}
