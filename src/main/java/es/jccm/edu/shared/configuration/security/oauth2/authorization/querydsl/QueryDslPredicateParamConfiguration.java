package es.jccm.edu.shared.configuration.security.oauth2.authorization.querydsl;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.PathImpl;
import com.querydsl.core.types.PredicateOperation;
import com.querydsl.core.types.dsl.BooleanOperation;

@Configuration
public class QueryDslPredicateParamConfiguration {
    
    interface Predicate {

        Object param(BooleanBuilder operation, String parameter);

        Object param(BooleanOperation operation, String parameter);

        Object param(PredicateOperation operations, String parameter);

    }
    
    @Bean("predicate")
    Predicate predicate() {
        return new Predicate() {
            
            @Override
            public Object param(BooleanBuilder operation, String parameter) {
                return null;
            }
            
            @Override
            public Object param(BooleanOperation operation, String parameter) {
                List<Expression<?>> expression = operation.getArgs();
                if (contains(expression, parameter)) {
                    return get(expression);
                }
                return null;
            }

            @Override
            public Object param(PredicateOperation operations, String parameter) {
                for (Expression<?> operation : operations.getArgs()) {
                    List<Expression<?>> expression = ((PredicateOperation) operation).getArgs();
                    if (contains(expression, parameter)) {
                        return get(expression);
                    }
                }
                return null;

            }
            
            private boolean contains(List<Expression<?>> expression, String parameter) {
                PathImpl<?> path = (PathImpl<?>) expression.get(0);
                return path.getMetadata().getElement().equals(parameter);
            }
            
            private Object get(List<Expression<?>> expression) {
                ConstantImpl<?> value = (ConstantImpl<?>) expression.get(1);
                return value.getConstant();
            }
        };
    }


}
