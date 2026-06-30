package es.jccm.edu.totp.adapter.out.springdata;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.totp.adapter.out.jpamodel.TotpUsuario;


@Transactional
public interface TotpUsuarioRepository extends CrudRepository<TotpUsuario, Long> {

}