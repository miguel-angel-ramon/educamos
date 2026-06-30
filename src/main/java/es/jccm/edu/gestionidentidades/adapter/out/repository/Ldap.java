package es.jccm.edu.gestionidentidades.adapter.out.repository;

public interface Ldap {

    /**
     * 
     * @param usNif
     * @param atribLdap
     * @return valor del atributo ldap o vacio si no encuentra nada
     */
	String getAtributoLDAP(String usNif, String atribLdap);

	/**
	 * Servicio que registra en LDAP el atributo enviado para el cNumide del empleado.
	 *
	 * @param cNumide Identificador del nif en el directorio LDAP
	 * @param atribLdap Tipo de atributo solicitado por elservicio
	 * @param vAtribLdap Valor del atributo LDAP
	 */
	void setAtributoLDAP(String cNumide, String atribLdap, String vAtribLdap);

}