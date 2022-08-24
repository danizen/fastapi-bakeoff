package net.danizen.bakeoff.validation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PunctuationFreeValidator implements ConstraintValidator<PunctuationFree, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || Pattern.matches("[\\p{Punct}\\p{IsPunctuation}]", value))
            return true;
        return false;
    }
}
