package com.SelfBuildApp.infrastructure.Validation;


import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;

public class FileValidator implements ConstraintValidator<FileConstrain, Object>
{
    private FileExtension[] extensions;
    private int size;

    private Locale locale;


    @Override
    public void initialize(final FileConstrain constraintAnnotation)
    {
        extensions = constraintAnnotation.extensions();
        size = constraintAnnotation.size();

    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context)
    {
        boolean toReturn = false;
        MultipartFile multipartFile = (MultipartFile) value;


        if (multipartFile == null) {
            return true;
        }

        if (multipartFile.isEmpty()) {
            return true;
        }
        context.disableDefaultConstraintViolation();

        if (!mimeTypeFileIsCorrect(multipartFile)) {
            context.buildConstraintViolationWithTemplate("{validation.user.file.mimeType}").addConstraintViolation();
            return false;
        }

        if (!sizeCorrect(multipartFile)) {
            context.buildConstraintViolationWithTemplate("{validation.user.file.size}").addConstraintViolation();
            return false;
        }

        return true;
    }

    private boolean sizeCorrect(MultipartFile multipartFile) {
        return (multipartFile.getSize() / 1000) <= size;
    }

    private boolean mimeTypeFileIsCorrect(MultipartFile multipartFile) {
        String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        for (int i = 0; i < extensions.length; i++) {
//            System.out.println(mimeTypes[i].mimeType);

            if (extensions[i].extension.equals(ext)) {
                return true;
            }
        }
        return false;
    }

}