package es.jccm.edu.shared.configuration.recuperacionclave;

import lombok.Data;

@Data
public class RecuperacionClaveConfig {
	private String urlRecuperacionClave;
	private int maxMinutosValidezToken;
}
