package es.jccm.edu.aulasVirtuales.application.domain.aulasVirtuales;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "TLAULAS")
public class AulaVirtual implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "X_AULA")
    private Long idAula;

}
