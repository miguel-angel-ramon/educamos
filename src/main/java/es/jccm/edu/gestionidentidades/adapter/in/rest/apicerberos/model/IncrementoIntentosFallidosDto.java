package es.jccm.edu.gestionidentidades.adapter.in.rest.apicerberos.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IncrementoIntentosFallidosDto {

    private String bloqueado;

    private Long numIntentosFallidos;

}
