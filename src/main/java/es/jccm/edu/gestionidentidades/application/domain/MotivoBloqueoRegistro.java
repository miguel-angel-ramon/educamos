package es.jccm.edu.gestionidentidades.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TLMOTBLOREG", schema="DELPHOS")
public class MotivoBloqueoRegistro {

    @Id
    @Column(name = "X_MOTBLOREG")
    private Long xMotivoBloqueoRegistro;

    @Column(name = "C_MOTBLOREG")
    private String codigoMotivoBloqueoRegistro;

    @Column(name = "D_MOTBLOREG")
    private String motivoBloqueoRegistro;

    @Column(name = "T_FUNCALMOTBLO")
    private String funcionBloqueoRegistro;

    @Column(name = "L_PERMANENTE")
    private Character permanente;

    @Column(name = "F_INIVIG")
    private LocalDateTime fechaInicioVigencia;

    @Column(name = "F_FINVIG")
    private LocalDateTime fechaFinVigencia;

}
