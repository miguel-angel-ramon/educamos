package es.jccm.edu.movil.adapter.in.rest.novedades.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NovedadesPublicoDTO {
    private List<TitleDTO> title;
    private List<BodyDTO> body;
    private List<FieldNoticiaImagenDTO> field_noticia_imagen;
    private List<FieldFechaPublicacionNoticiaDTO> field_fecha_publicacion_noticia;
    private List<FieldEntradillaDTO> field_entradilla;
    private List<NidDTO> nid;
    private List<ArchivoRelacionadoDTO> field_archivos_relacionados;
    private List<EnlaceInteresDTO> field_enlaces_interes;


}
