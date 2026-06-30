package es.jccm.edu.gestionidentidades.application.ports.in;

import java.util.Date;

public interface ClockProvider {
	Date getNow();
}
