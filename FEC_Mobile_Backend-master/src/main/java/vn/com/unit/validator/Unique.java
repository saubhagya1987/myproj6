package vn.com.unit.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

	@Constraint(validatedBy = UniqueValidator.class)
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Unique {
	    /** Query string to execute to determine uniqueness. */
	    String hql();
	    String idField();
	    String errorAtField();
	    /** The message to display if validation fails. */
	    String message() default "The specified field value is not unique.";
	    
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
	}
