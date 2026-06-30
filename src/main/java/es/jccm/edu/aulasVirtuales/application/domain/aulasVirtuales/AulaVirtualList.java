package es.jccm.edu.aulasVirtuales.application.domain.aulasVirtuales;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class AulaVirtualList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long idAula;

    private String aula;

    private String nombreMateria;

    private String ciclo;

    private String urlAula;
    
    private Long idMoodle;
    
    private String idPlataforma;
    
    private Long idCurso;
    
    private Long idMateriaOMG;
    
    private Long idOfertaMatrig;
    
    private String tokenPlataforma;
    
    private UsuarioAulaVirtual usuarioAulaVirtual;
}
