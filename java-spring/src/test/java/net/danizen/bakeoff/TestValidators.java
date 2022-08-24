package net.danizen.bakeoff;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import net.danizen.bakeoff.validation.PunctuationFree;
import net.danizen.bakeoff.validation.PunctuationFreeValidator;

public class TestValidators {

    @Mock
    private PunctuationFree punctFree;

    @Mock
    private ConstraintValidatorContext validatorContext;

    @Test
    public void testPunctuationIsBAD() {
        PunctuationFreeValidator validator = new PunctuationFreeValidator();
        validator.initialize(punctFree);
        boolean result = validator.isValid("Dan?", validatorContext);
        assertFalse(result);
    }

    @Test
    public void testNullIsOK() {
        PunctuationFreeValidator validator = new PunctuationFreeValidator();
        validator.initialize(punctFree);
        boolean result = validator.isValid(null, validatorContext);
        assertTrue(result);
    }

    @Test
    public void testNormalIsOK() {
        PunctuationFreeValidator validator = new PunctuationFreeValidator();
        validator.initialize(punctFree);
        boolean result = validator.isValid("Dan", validatorContext);
        assertTrue(result);

    }
}
