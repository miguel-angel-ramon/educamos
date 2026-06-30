package es.jccm.edu.shared.application.services.totp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import de.taimos.totp.TOTP;
import es.jccm.edu.gestionidentidades.adapter.out.repository.UsuariosRepository;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.shared.application.ports.out.mail.EmailAttachment;
import es.jccm.edu.shared.application.ports.out.mail.EmailDetails;
import es.jccm.edu.shared.application.ports.out.mail.MailSenderPortOut;
import es.jccm.edu.shared.application.ports.out.totp.ITotpService;

@Service
public class TotpMailService implements ITotpService {

    @Autowired
	private UsuariosRepository usuariosRepository;

    @Autowired
    private MailSenderPortOut mailSenderPortOut;
    
    @Value("${duracionEmail}")
	private int duracionEmail;


    @Override
    public Boolean enviarCodigoMailQR(Long idUsuario, String email) throws IOException, WriterException {

        Usuario usuario = usuariosRepository.findById(idUsuario).orElseThrow();

        if (email == null) return false;

        if (usuario.getSecretKey2FA() == null) {

            usuario.setSecretKey2FA(generateSecretKey());

            usuariosRepository.save(usuario);

        }

        createQRCode(getGoogleAuthenticatorBarCode(usuario.getSecretKey2FA(), usuario.getLogin(), "EDUCAMOS_CLM"), "qr2FA.png", 400, 400);

        sendMailQRCode(email);

        return true;

    }

    @Override
    public Boolean enviarCodigoMail(Long idUsuario, String email) {

        Usuario usuario = usuariosRepository.findById(idUsuario).orElseThrow();

        if (email == null) return false;

        Log.info("Se envía código al mail "+email);
        
        String code = getTOTPCode(generateSecretKey());

        usuario.setCodigo2FA(code);
        usuario.setFechaGeneracionCodigo(new Date());

        usuariosRepository.save(usuario);
        
        sendMailCode(email, code);

        return true;

    }

    @Override
    public Boolean validarCodigoTotpQR(String codigo, Long idUsuario) {

        Usuario usuario = usuariosRepository.findById(idUsuario).orElseThrow();

        if (usuario.getSecretKey2FA() == null) {
            return false;
        }

        return Objects.equals(getTOTPCode(usuario.getSecretKey2FA()), codigo);

    }

    @Override
    public Boolean validarCodigoTotp(String codigo, Long idUsuario) {

        Usuario usuario = usuariosRepository.findById(idUsuario).orElseThrow();

        if (usuario.getCodigo2FA() == null || !usuario.getCodigo2FA().equals(codigo)) {
            return false;
        }

        return usuario.getFechaGeneracionCodigo() != null && !new Date().after(new Date(usuario.getFechaGeneracionCodigo().getTime() + duracionEmail * 60 * 1000));

    }

    private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    private String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
        return "otpauth://totp/"
                + URLEncoder.encode(issuer + ":" + account, StandardCharsets.UTF_8).replace("+", "%20")
                + "?secret=" + URLEncoder.encode(secretKey, StandardCharsets.UTF_8).replace("+", "%20")
                + "&issuer=" + URLEncoder.encode(issuer, StandardCharsets.UTF_8).replace("+", "%20");
    }

    private String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    private void createQRCode(String barCodeData, String filePath, int height, int width)
            throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE,
                width, height);
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            MatrixToImageWriter.writeToStream(matrix, "png", out);
        }
    }

    private void sendMailQRCode(String email) {

        EmailDetails emailDetails = new EmailDetails();

        emailDetails.setRecipient(email);
        emailDetails.setMsgBody("Prueba");
        emailDetails.setSubject("Código QR 2FA Educamos");
        
        EmailAttachment attachment=new EmailAttachment();
        attachment.setFilename("qr2FA.png");
        byte[] datosqr = {};
        attachment.setDatos(datosqr);
        
        emailDetails.setEmailAttachment(Optional.of(attachment));
        

        mailSenderPortOut.sendMailWithAttachment(emailDetails);

        new File("qr2FA.png").delete();

    }

    private void sendMailCode(String email, String codigo) {

        EmailDetails emailDetails = new EmailDetails();

        emailDetails.setRecipient(email);
        emailDetails.setMsgBody("Para acceder a la funcionalidad de EducamosCLM, use el código siguiente para verificar su autorización. El código solamente funcionará durante " + duracionEmail + " minutos. <br/>" +
                "Código de verificación: " + codigo + "<br/> Si no se ha solicitado ningún código, no es necesario leer este correo electrónico."
                + "<br /><br /><img src='cid:"+"imagen_plataforma"+"' />");
        emailDetails.setSubject("Código de verificación");

        Log.info("Send MailCode");
        
        mailSenderPortOut.sendFormatedMailWithImage(emailDetails, "imagen_plataforma");

    }

}
