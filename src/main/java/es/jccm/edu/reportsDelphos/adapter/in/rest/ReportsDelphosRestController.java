package es.jccm.edu.reportsDelphos.adapter.in.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.reportsDelphos.application.ports.in.IReportsDelphosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(BasePath.ReportsdelphosBasePath)
@Tag(name = "Servicio genérico", description = "Servicio reports servidor de Delphos")
@CrossOrigin
public class ReportsDelphosRestController {

    @Autowired
    private IReportsDelphosService reportsDelphosService;

    /**
     * Descarga el documento pdf del servidor de reports de delphos
     *
     * @return
     * @throws IOException
     */
    @PreAuthorize("hasAnyRole('C','P','PRO','I','INZ','INC','ICO')")
    @Operation(summary = "Descargar documento delphos", description = "Este metodo descarga el documento del servidor de delphos",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/descargaReportsDelphos/{tParam}/{name}")
    public void descargarDoc(@PathVariable("tParam") String tParam,
                             @PathVariable("name") String name,
                             HttpServletResponse response) throws IOException {


        byte[] documento = reportsDelphosService.descargaReportsDelphos(tParam);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "inline; filename=" + name + "." + "pdf");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setStatus(200);

        InputStream is = new ByteArrayInputStream(documento);
        FileCopyUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
    }



}
