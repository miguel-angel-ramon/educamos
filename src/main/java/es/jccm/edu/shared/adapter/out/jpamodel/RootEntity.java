package es.jccm.edu.shared.adapter.out.jpamodel;

import javax.persistence.Entity;
import javax.persistence.Id;

// Entity que no está mapeada a ninguna tabla pero sirve para poder usar native queries con JpaRepositories sin referirse a ninguna clase-tipo concreta
// https://stackoverflow.com/questions/55513776/create-spring-repository-without-entity

@Entity
public class RootEntity {
    @Id
    private Integer id;
}
