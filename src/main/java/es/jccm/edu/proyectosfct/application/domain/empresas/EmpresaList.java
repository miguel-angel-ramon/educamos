package es.jccm.edu.proyectosfct.application.domain.empresas;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

@Data
@Entity
public class EmpresaList extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String tipoIdentificador;

	private String numIde;

	private String nombreEmpresa;

	private String domicilio;

	private String numero;

	private String escalera;

	private String piso;
	
	private String codigoPostal;
	
	private Long telefono;
	
	private Integer otroTelefono;
	
	private Integer fax;
	
	private String correo;
	
	private String empresaActiva;
	
	private String telefonoContacto;
	
	private String observaciones;
	
	private String tipoEmpresa;
	
	private String letra;
	
	private Long idSectorProductivo;

	private String paginaWeb;
	
	private String localidadExtranjera;
	
	private String empresaPublica;
	
	private String familiaProfesional;
	
	private String dTipoVia;
	
	private String dLocalidad;

	private String dMunicipio;

	private String dProvincia;

	private String dPais;
	
	private String sesCam;

}
