package es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class EvaUsuarioAulaVirtual implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    protected Long id;
	protected String username;
	protected String password;
	protected String firstname;
	protected String lastname;
	protected String fullname;
	protected String email;
	protected String auth;
	protected String idnumber;
	protected String department;
	protected String institution;
	protected String description;
	protected String city;
	protected String country = "ES";

}