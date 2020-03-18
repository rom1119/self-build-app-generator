package com.SelfBuildApp.infrastructure.Validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = OldPasswordConstraintValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidChangePassword {

    String message() default "Invalid old Password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String oldPasswordField() ;
    String passwordField() ;
    String confirmPasswordField() ;
}
