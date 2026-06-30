package es.jccm.edu.buzon.adapter.in.rest.buzon.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ActualizarPermisoUsuariosDTO {
    private String idPermiso;
    private List<Long> listaOidUsuariosAModificar;
}
