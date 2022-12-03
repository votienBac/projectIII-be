package vn.noron.repository.config.annotation;

import org.springframework.stereotype.Repository;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Repository
@Inherited
@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {
    String tableName();

    String idName() default "id";
}
