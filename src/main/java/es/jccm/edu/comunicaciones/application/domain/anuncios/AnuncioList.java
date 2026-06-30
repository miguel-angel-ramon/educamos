package es.jccm.edu.comunicaciones.application.domain.anuncios;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class AnuncioList {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAnuncio;

    private Long idSeccionAnuncio;

    private Date fechaPublicacion;

    private Date fechaFinVigencia;

    private String titulo;

    private String cuerpo;

    private String noEnviadaWeb;

}
