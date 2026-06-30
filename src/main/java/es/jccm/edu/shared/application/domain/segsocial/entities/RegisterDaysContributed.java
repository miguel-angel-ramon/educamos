package es.jccm.edu.shared.application.domain.segsocial.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class RegisterDaysContributed implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long idAlu;  
    
    @Column(name="afiliation_number")
    private String afiliation_number;
    
    @Column(name="month")
    private Integer month;
    
    @Column(name="year")
    private Integer year;
    
    @Column(name="days")
    private Integer days;
    
    @Column(name="tipoEmpresa")
    private String tipoEmpresa;
    
    @Column(name="absent_by_son")
    private Integer absent_by_son;
    
    @Column(name="absent_it")
    private Integer absent_it;
    
    @Column(name="absent_erasmus")
    private Integer absent_erasmus;
    
    @Column(name="erasmusScholarship")
    private Integer erasmusScholarship;

    @Column(name = "ds_warnings")
    private String warnings;
	
}


