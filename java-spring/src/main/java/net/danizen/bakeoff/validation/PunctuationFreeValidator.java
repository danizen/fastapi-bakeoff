package net.danizen.bakeoff.validation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PunctuationFreeValidator implements ConstraintValidator<PunctuationFree, String> {

    private Pattern pattern = Pattern.compile("\\p{Punct}", Pattern.UNICODE_CHARACTER_CLASS);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && pattern.matcher(value).find())
            return false;
        return true;
    }
}
