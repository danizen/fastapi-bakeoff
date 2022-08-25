package net.danizen.bakeoff.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ErrorResponse {
    private int status;
    private Map<String, String> errors;

    public ErrorResponse(int status) {
        this.status = status;
        this.errors = new HashMap<String, String>();
    }

    public ErrorResponse(final HttpStatus status) {
        this(status.value());
    }

    public void addMessage(final String field, final String message) {
        this.errors.put(field, message);
    }
}
