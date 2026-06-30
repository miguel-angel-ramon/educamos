package es.jccm.edu.shared.configuration.scheduledtask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.jccm.edu.proyectosfct.application.ports.in.datosgestora.IDatosGestoraService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class ScheduledTask {

    private static final String SINC_NOCTURNA_GESTORA = "SINCRONIZACION_NOCTURNA_GESTORA";

    private static final Logger LOG = LogManager.getLogger(ScheduledTask.class);
    @Autowired
    private IDatosGestoraService datosGestoraService;

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    @Scheduled(cron = "0 30 3 * * ?", zone = "Europe/Madrid")
    public void ejecutarTarea() {

        if (datosGestoraService.trabajoEnProgreso(SINC_NOCTURNA_GESTORA)) {
            //LOG.error("El proceso de sincronización ya está en ejecución en otro nodo. Abortando.");
            return;
        }

        try {
            datosGestoraService.comenzarTrabajo(SINC_NOCTURNA_GESTORA);
            //LOG.info("Iniciando proceso de sincronización nocturno para datos gestora...");

            Future<?> future1 = executor.submit(() -> datosGestoraService.programadaDatosGestora());
            Future<?> future2 = executor.submit(() -> datosGestoraService.programadaDatosGestoraCotizacionesMes());

            future1.get();
            future2.get();
            
        } catch (InterruptedException e) {
            // Volver a interrumpir el hilo actual
            Thread.currentThread().interrupt();  
            LOG.error("Hilo interrumpido durante la ejecución de las tareas programadas.", e);
        } catch (Exception e) {
            LOG.error("Error durante la ejecución de las tareas programadas: ", e);
        } finally {
            //LOG.info("Finalizando proceso de sincronización nocturno para datos gestora...");
            datosGestoraService.finalizarTrabajo(SINC_NOCTURNA_GESTORA);
        }
    }
}
