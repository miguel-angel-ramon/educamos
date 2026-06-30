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
public class RegisterTraineeStudent  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idAlu")
    private Long idAlu;
    
    @Column(name="afiliation_number")
    private String afiliation_number;
    
    @Column(name = "nif")
    private String nif;
    
    @Column(name = "real_register_date")
    private String real_register_date;

    @Column(name = "endDateCompletion")
    private String endDateCompletion;
    
    @Column(name = "erasmusfpdualScholarship")
    private Integer erasmusfpdualScholarship;
    
    @Column(name = "regime")
    private String regime;
    
    @Column(name = "contract_type")
    private String contract_type;   
    
    @Column(name = "cif")
    private String company_cif; 
    
    @Column(name = "tipoEmpresa")
    private String tipoEmpresa; 
    
    @Column(name = "contribution_group")
    private String contribution_group;  
    
    @Column(name = "cn_occupation")
    private String cn_occupation;      
    
    @Column(name = "cancel_registration")
    private Integer cancel_registration;
    
    @Column(name = "ccodigo")
    private String ccodigo;  
   
    @Column(name = "ds_warnings")
    private String warnings;

    @Column(name = "id")
    private Long id;    
    
    @Column(name = "idGestora")
    private Long idGestora;
    
    @Column(name = "inicioHist")
    private String inicioHist; 
    
    @Column(name = "finHist")
    private String finHist; 
    
    @Column(name = "ds_errors")
    private String errors;

    @Column(name = "name")
    private String name;
    
    @Column(name = "worker_id_ext")
    private Long worker_id_ext;

    @Column(name = "birth_date")
    private String birth_date;

    @Column(name = "gender")
    private String gender;
}
