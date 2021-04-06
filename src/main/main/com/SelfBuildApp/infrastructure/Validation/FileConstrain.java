package com.SelfBuildApp.infrastructure.Validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validation annotation to validate image element.
 *
 *
 * Example
 * @FileConstrain(size = 1500, mimeTypes = {MimeType.IMAGE_JPG, MimeType.IMAGE_PNG})
 */
@Target({TYPE, ANNOTATION_TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = FileValidator.class)
@Documented
public @interface FileConstrain
{
    String message() default "file is incorrect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return The first field
     */
    FileExtension[] extensions() default {FileExtension.IMG};


    /**
     * @return The size of file in kilobytes
     */
    int size() default 1000;

    /**
     * Defines several <code>@FieldMatch</code> annotations on the same element
     *
     * @see FileConstrain
     */
    @Target({TYPE, ANNOTATION_TYPE, FIELD})
    @Retention(RUNTIME)
    @Documented
    @interface List
    {
        FileConstrain[] value();
    }
}