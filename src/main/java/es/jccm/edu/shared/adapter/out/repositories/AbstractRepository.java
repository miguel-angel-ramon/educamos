package es.jccm.edu.shared.adapter.out.repositories;

import org.bitbucket.gt_tech.spring.data.querydsl.value.operators.ExpressionProviderFactory;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.querydsl.core.types.EntityPath;

@NoRepositoryBean
public interface AbstractRepository<E, T, Q extends EntityPath<E>>
		extends PagingAndSortingRepository<E, T>, QuerydslPredicateExecutor<E>, QuerydslBinderCustomizer<Q> {

	@Override
	public default void customize(QuerydslBindings bindings, Q root) {
	
		bindings.bind(String.class).all((path, values) -> ExpressionProviderFactory.getPredicate(path, values));
        bindings.bind(Integer.class).all((path, values) -> ExpressionProviderFactory.getPredicate(path, values));
		

	}

}
