package es.jccm.edu.gestionidentidades.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TLMOTBLOREGPER", schema="DELPHOS")
public class MotivoBloqueoRegistroPersona {

    @Id
    @Column(name = "X_MOTBLOREGPER")
    private Long xMotivoBloqueoRegistroPersona;

    @ManyToOne
    @JoinColumn(name = "X_PERSONA")
    private Tlpersona persona;

    @ManyToOne
    @JoinColumn(name = "X_MOTBLOREG")
    private MotivoBloqueoRegistro motivoBloqueoRegistro;

    @Column(name = "F_INIBLO")
    private LocalDateTime fechaInicioBloqueo;

    @Column(name = "F_FINBLO")
    private LocalDateTime fechaFinBloqueo;

    @OneToMany(mappedBy = "motivoBloqueoRegistro", fetch = FetchType.LAZY)
    Set<MotivoBloqueoRegistroPersona> motivoBloqueoRegistroPersonas;


}
