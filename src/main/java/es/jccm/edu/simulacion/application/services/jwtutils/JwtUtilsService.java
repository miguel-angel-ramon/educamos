package es.jccm.edu.simulacion.application.services.jwtutils;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import es.jccm.edu.shared.application.domain.datosUsuarioJwt.AplicacionUsuarioJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.NombreUsuarioJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.RolUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.simulacion.application.ports.in.token.IJwtUtilsService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtUtilsService implements IJwtUtilsService {

	@Value("${educamos.security.principal.claim}")
	private String principalClaim;

	@Value("${educamos.security.identity.claim}")
	private String identityClaim;

	private final IDatosUsuarioJwtService datosUsuarioJwtService;

	@Value("${educamos.security.oauth2.resource.jwt.key-value}")
	private String keyValue;

	@Value("${educamos.security.oauth2.resource.jwt.issuer-uri}")
	private String iss;
	
	public JwtUtilsService(IDatosUsuarioJwtService datosUsuarioJwtService) {
		this.datosUsuarioJwtService = datosUsuarioJwtService;
	}

	public String generateToken(String jwt, @Nullable String oidUsuario) {

		String[] jwtChunks = jwt.split(" ")[1].split("\\.");
		JSONObject myJsonObj = getFromEncodedPayload(jwtChunks[1]);

		vitaminarToken(myJsonObj, jwt);

		if (oidUsuario != null) {
			myJsonObj.put("usuarioSimulado", appendDatosSimulacion(oidUsuario));
		}

		/*String encodedPayload = getEncodedPayloadFromJSON(myJsonObj);
		
		String newSignature = getNewSignature(jwtChunks[0], encodedPayload);

		return jwtChunks[0].concat(".").concat(encodedPayload).concat(".").concat(newSignature);*/
		
		return getNewSignatureHS256( myJsonObj);
		
	}

	private JSONObject appendDatosSimulacion(String oidUsuario) {
		JSONObject jsonObject = new JSONObject();

		JSONObject authoritiesObject = new JSONObject();
		JSONArray authoritiesArray = new JSONArray();

		authoritiesObject.put("authorities", authoritiesArray);
		jsonObject.put("authorities", authoritiesObject);

		appendDatosUsuarioByOidUsuario(jsonObject, oidUsuario);
		appendAplicacionesUsuario(jsonObject, oidUsuario);
		appendRolesUsuario(jsonObject, oidUsuario, jsonObject.getString("nif"));
		return jsonObject;
	}

	public String generateToken(String jwt) {
		return generateToken(jwt, null);
	}

	public String generateTokenSimulacion(String jwt, String oidUsuario) {

		String[] jwtChunks = jwt.split(" ")[1].split("\\.");
		JSONObject myJsonObj = getFromEncodedPayload(jwtChunks[1]);

		if (!myJsonObj.has("usuarioSimulador")) {
			myJsonObj.put("usuarioSimulador", new JSONObject(myJsonObj.toString()));
		}

		appendDatosUsuarioByOidUsuario(myJsonObj, oidUsuario);
		appendAplicacionesUsuario(myJsonObj, oidUsuario);
		appendRolesUsuario(myJsonObj, oidUsuario, myJsonObj.getString("nif"));

		myJsonObj.put("oid_username", oidUsuario);
		myJsonObj.put("user_name", oidUsuario);
		myJsonObj.put("iss", "https://educamosclmauthcas-des.cm-pre.jccm.es/oauth/realms/realm");

		NombreUsuarioJwt nombreUsuarioJwt = datosUsuarioJwtService.getUsuarioNombre(oidUsuario);

		myJsonObj.put("email", JSONObject.NULL);

		if (nombreUsuarioJwt.getNombre() != null)
			myJsonObj.put("given_name", nombreUsuarioJwt.getNombre());
		if (nombreUsuarioJwt.getApellido() != null)
			myJsonObj.put("family_name", nombreUsuarioJwt.getApellido());
		if (nombreUsuarioJwt.getCorreo() != null)
			myJsonObj.put("email", nombreUsuarioJwt.getCorreo());

		String encodedPayload = getEncodedPayloadFromJSON(myJsonObj);
		/*
		 * String newSignature = getNewSignature(jwtChunks[0], encodedPayload);
		 * 
		 * return jwtChunks[0].concat(".").concat(encodedPayload).concat(".").concat(
		 * newSignature);
		 */
		return getNewSignatureHS256( myJsonObj);

	}

	/*
	 * public String generateTokenSimulacion(String jwt, String oidUsuario) { //
	 * Generar la clave privada a partir de secretKey (en formato PKCS8) PrivateKey
	 * privateKey = generatePrivateKey(generaPrivateKey());
	 * 
	 * // Configurar los claims del token JWT Map<String, Object> claims = new
	 * HashMap<>();
	 * 
	 * String[] jwtChunks = jwt.split(" ")[1].split("\\."); JSONObject myJsonObj =
	 * getFromEncodedPayload(jwtChunks[1]);
	 * 
	 * if (!myJsonObj.has("usuarioSimulador")) { claims.put("usuarioSimulador", new
	 * JSONObject(myJsonObj.toString())); }
	 * 
	 * appendDatosUsuarioByOidUsuarioClaims(claims, oidUsuario);
	 * 
	 * appendAplicacionesUsuarioClaims(claims, oidUsuario);
	 * 
	 * appendRolesUsuarioClaims(claims, oidUsuario, claims.get("nif").toString());
	 * 
	 * // Generar el token JWT firmado String jwtToken =
	 * Jwts.builder().setClaims(claims) .setIssuer(
	 * "https://educamosclmauthcas-des.cm-pre.jccm.es/oauth/realms/realm")
	 * .setExpiration(new Date(System.currentTimeMillis() + 2073600000))
	 * .signWith(SignatureAlgorithm.RS256, privateKey).compact();
	 * 
	 * return jwtToken;
	 * 
	 * }
	 */
	private static PrivateKey generatePrivateKey(String secretKey) {
		// Convertir secretKey en una instancia de PrivateKey
		// Esto depende de cómo tengas almacenada la clave privada RSA
		// Aquí se muestra un ejemplo utilizando la clase PKCS8EncodedKeySpec
		try {
			byte[] keyBytes = Base64.getDecoder().decode(secretKey);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			throw new IllegalArgumentException("Error al generar la clave privada", e);
		}
	}

	private void vitaminarToken(JSONObject myJsonObj, String jwt) {
		myJsonObj.put("iss", "https://educamosclmauthcas-des.cm-pre.jccm.es/oauth/realms/realm");
		appendDatosUsuarioByJwt(myJsonObj, jwt);
		appendAplicacionesUsuario(myJsonObj, String.valueOf(myJsonObj.getLong(principalClaim)));
		// appendRolesUsuario(myJsonObj, myJsonObj.getString(principalClaim),
		// myJsonObj.getString(identityClaim));
		appendRolesUsuario(myJsonObj, String.valueOf(myJsonObj.getLong(principalClaim)), myJsonObj.getString("nif"));
	}

	private JSONObject getFromEncodedPayload(String encodedPayload) {
		String decodedPayload = new String(Base64.getUrlDecoder().decode(encodedPayload));
		return new JSONObject(decodedPayload);
	}

	private String getEncodedPayloadFromJSON(JSONObject jsonObject) {
		String encodedPayload = Base64.getUrlEncoder().encodeToString(jsonObject.toString().getBytes());
		while (encodedPayload.contains("=")) {
			encodedPayload = encodedPayload.substring(0, encodedPayload.lastIndexOf("="));
		}
		return encodedPayload;
	}

	private static byte[] hexStringToByteArray(String hexString) {
		int len = hexString.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
					+ Character.digit(hexString.charAt(i + 1), 16));
		}
		return data;
	}

	private String generaPrivateKey() {

		try {

			// Convertir la cadena hexadecimal en bytes
			byte[] privateKeyBytes = hexStringToByteArray(keyValue);

			// Generar una especificación de clave privada en formato PKCS8
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes, "RSA");
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

			// Codificar la clave privada en Base64
			String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());

			return privateKeyBase64;

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException("Error al generar la clave privada RSA", e);
		}
	}

	private String getNewSignatureHS256(JSONObject myJson) {
		try {
			byte[] hash = keyValue.getBytes(StandardCharsets.UTF_8);
			SecretKey key = new SecretKeySpec(hash, SignatureAlgorithm.HS256.getJcaName());

			JwtBuilder builder = Jwts.builder().setHeaderParam("alg", "HS256").setHeaderParam("typ", "JWT")
					.setPayload(myJson.toString()).signWith(SignatureAlgorithm.HS256, key);

			String jwtToken = builder.compact();
			return jwtToken;
		} catch (Exception e) {
			throw new RuntimeException("Error al generar la firma del token JWT", e);
		}
	}

	private String getNewSignature(String jwtChunk, String encodedPayload) {
		try {

			byte[] privateKeyBytes = Base64.getDecoder().decode(generaPrivateKey());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

			JwtBuilder builder = Jwts.builder().setHeaderParam("alg", "RS256").setHeaderParam("typ", "JWT")
					.setPayload(encodedPayload).signWith(SignatureAlgorithm.RS256, privateKey);

			String jwtToken = builder.compact();
			return jwtToken;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException("Error al generar la firma del token JWT", e);
		}
	}

	private String getNewSignature2(String jwtChunk, String encodedPayload) {

		try {

			byte[] hash = keyValue.getBytes(StandardCharsets.UTF_8);
			SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");

			Mac sha256Hmac = null;
			sha256Hmac = Mac.getInstance("HmacSHA256");
			sha256Hmac.init(secretKey);
			byte[] signedBytes = sha256Hmac
					.doFinal(jwtChunk.concat(".").concat(encodedPayload).getBytes(StandardCharsets.UTF_8));

			String newSignature = Base64.getUrlEncoder().encodeToString(signedBytes);
			while (newSignature.contains("=")) {
				newSignature = newSignature.substring(0, newSignature.lastIndexOf("="));
			}
			return newSignature;

		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}

	private void appendDatosUsuarioByJwt(JSONObject jsonObject, String jwt) {
		DatosUsuarioJwt datosUsuarioJwt = datosUsuarioJwtService.getDatosUsuarioByJwtUsingRepository(jwt);
		jsonObject.put("idUsuarioDelphos", datosUsuarioJwt.getXUsuarioDelphos());
		jsonObject.put("idUsuarioComunica", datosUsuarioJwt.getXUsuarioComunica());
		jsonObject.put("usuarioDelphos", datosUsuarioJwt.getUsuarioDelphos());
		jsonObject.put("usuarioComunica", datosUsuarioJwt.getUsuarioComunica());
		jsonObject.put("idEmpleadoDelphos", datosUsuarioJwt.getIdEmpleadoDelphos());
		jsonObject.put("idEmpleadoComunica", datosUsuarioJwt.getIdEmpleadoComunica());
		jsonObject.put("nif", datosUsuarioJwt.getNif());
		jsonObject.put("email", datosUsuarioJwt.getEmail());
	}

	private void appendDatosUsuarioByOidUsuario(JSONObject jsonObject, String oidUsuario) {
		DatosUsuarioJwt datosUsuarioJwt = datosUsuarioJwtService.getDatosUsuarioByOidUsuario(oidUsuario);
		jsonObject.put("idUsuarioDelphos", datosUsuarioJwt.getXUsuarioDelphos());
		jsonObject.put("idUsuarioComunica", datosUsuarioJwt.getXUsuarioComunica());
		jsonObject.put("usuarioDelphos", datosUsuarioJwt.getUsuarioDelphos());
		jsonObject.put("usuarioComunica", datosUsuarioJwt.getUsuarioComunica());
		jsonObject.put("idEmpleadoDelphos", datosUsuarioJwt.getIdEmpleadoDelphos());
		jsonObject.put("idEmpleadoComunica", datosUsuarioJwt.getIdEmpleadoComunica());
		jsonObject.put("nif", datosUsuarioJwt.getNif());
		jsonObject.put("email", datosUsuarioJwt.getEmail());
	}

	private void appendDatosUsuarioByOidUsuarioClaims(Map<String, Object> claims, String oidUsuario) {
		DatosUsuarioJwt datosUsuarioJwt = datosUsuarioJwtService.getDatosUsuarioByOidUsuario(oidUsuario);
		claims.put("idUsuarioDelphos", datosUsuarioJwt.getXUsuarioDelphos());
		claims.put("idUsuarioComunica", datosUsuarioJwt.getXUsuarioComunica());
		claims.put("usuarioDelphos", datosUsuarioJwt.getUsuarioDelphos());
		claims.put("usuarioComunica", datosUsuarioJwt.getUsuarioComunica());
		claims.put("idEmpleadoDelphos", datosUsuarioJwt.getIdEmpleadoDelphos());
		claims.put("idEmpleadoComunica", datosUsuarioJwt.getIdEmpleadoComunica());
		claims.put("nif", datosUsuarioJwt.getNif());
		claims.put("email", datosUsuarioJwt.getEmail());
	}

	private void appendAplicacionesUsuario(JSONObject jsonObject, String oidUsuario) {
		List<AplicacionUsuarioJwt> aplicacionesUsuario = datosUsuarioJwtService.getAplicacionesUsuario(oidUsuario);
		jsonObject.put("aplicaciones", aplicacionesUsuario);
	}

	private void appendAplicacionesUsuarioClaims(Map<String, Object> claims, String oidUsuario) {
		List<AplicacionUsuarioJwt> aplicacionesUsuario = datosUsuarioJwtService.getAplicacionesUsuario(oidUsuario);
		claims.put("aplicaciones", aplicacionesUsuario);
	}

	private void appendRolesUsuario(JSONObject jsonObject, String oidUsuario, String nif) {

		JSONObject authoritiesObject = new JSONObject();
		JSONArray authoritiesArray = new JSONArray();
		// Map<String, Object> additionalInfo = new HashMap<String, Object>();

		authoritiesObject.put("authorities", authoritiesArray);

		List<RolUsuarioJwt> rolesUsuario = datosUsuarioJwtService.getRolesUsuario(oidUsuario, nif);
		jsonObject.put("roles", rolesUsuario);
		// JSONObject authoritiesObject = (JSONObject) jsonObject.get("authorities");
		JSONArray authorities = (JSONArray) authoritiesObject.get("authorities");
		rolesUsuario.forEach(rol -> authorities.put("ROLE_" + rol.getCodigoPerfil()));
		jsonObject.put("authorities", authoritiesObject);
	}

	private void appendRolesUsuarioClaims(Map<String, Object> claims, String oidUsuario, String nif) {

		JSONObject authoritiesObject = new JSONObject();
		JSONArray authoritiesArray = new JSONArray();
		// Map<String, Object> additionalInfo = new HashMap<String, Object>();

		authoritiesObject.put("authorities", authoritiesArray);

		List<RolUsuarioJwt> rolesUsuario = datosUsuarioJwtService.getRolesUsuario(oidUsuario, nif);
		claims.put("roles", rolesUsuario);
		// JSONObject authoritiesObject = (JSONObject) jsonObject.get("authorities");
		JSONArray authorities = (JSONArray) authoritiesObject.get("authorities");
		rolesUsuario.forEach(rol -> authorities.put("ROLE_" + rol.getCodigoPerfil()));
		claims.put("authorities", authoritiesObject);
	}

	private long ahora() {
		return new Date().getTime();
	}

	/**
	 * Dado un token, genera un token seguramente caducado , genera uno nuevo
	 * firmado para testing que caduca dentro de x segundos Solo previsible su uso
	 * para testing
	 * 
	 * @param jwt
	 * @param segundosExtraDesdeYa
	 * @return
	 * @throws JSONException
	 */
	public String testingRevitalizarToken(String jwt, int segundosExtraDesdeYa) throws JSONException {
		String[] jwtChunks = jwt.split("\\.");
		JSONObject myJsonObj = this.getFromEncodedPayload(jwtChunks[1]);

		myJsonObj.put("exp", ahora() + segundosExtraDesdeYa * 1000);

		String encodedPayload = this.getEncodedPayloadFromJSON(myJsonObj);
		String newSignature = this.getNewSignature(jwtChunks[0], encodedPayload);

		return jwtChunks[0].concat(".").concat(encodedPayload).concat(".").concat(newSignature);
	}

}