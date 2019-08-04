package com.Self.Build.App.Validation;

import com.Self.Build.App.User.Model.User;
import com.Self.Build.App.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, Object> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        String username = (String) o;
        User user = userRepository.findByUsername(username);
        if (user != null) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("{validation.user.username.unique}").addConstraintViolation();
            return false;
        }
        return true;
    }
}
