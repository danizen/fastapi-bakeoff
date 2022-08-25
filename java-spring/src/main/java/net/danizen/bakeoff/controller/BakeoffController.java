package net.danizen.bakeoff.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import net.danizen.bakeoff.model.ErrorResponse;
import net.danizen.bakeoff.model.Contact;
import net.danizen.bakeoff.model.ContactTypesResponse;
import net.danizen.bakeoff.model.ContactsResponse;
import net.danizen.bakeoff.model.FibonacciResponse;
import net.danizen.bakeoff.model.VersionResponse;
import net.danizen.bakeoff.service.ContactsService;
import net.danizen.bakeoff.service.FibonacciService;
import net.danizen.bakeoff.validation.PunctuationFree;

@RestController
@Validated
@Profile("!jshell")
public class BakeoffController {

    private FibonacciService fibonacciService;
    private ContactsService contactsService;

    @Value("${bakeoff.version}")
    private String version;

    @Value("${springdoc.swagger-ui.path}")
    private String docsPath;

    @Autowired
    public BakeoffController(FibonacciService fibonacciService, ContactsService contactsService) {
        this.fibonacciService = fibonacciService;
        this.contactsService = contactsService;
    }

    @GetMapping("/")
    public void redirectToDocs(HttpServletResponse response) {
        response.setStatus(301);
        response.setHeader("Location", docsPath);
    }

    @GetMapping("/version")
    public VersionResponse getVersion() {
        return new VersionResponse(version);
    }

    @GetMapping("/fibonacci/{number}")
    public FibonacciResponse getFibonacci(@PathVariable @Min(0) @Max(50) int number) {
        return fibonacciService.getFibonacci(number);
    }

    @GetMapping("/types")
    public ContactTypesResponse getTypes() {
        return contactsService.getTypes();
    }

    @GetMapping("/contacts/{contact_id}")
    public Contact getContact(@PathVariable("contact_id") @Min(0) int contactId) {
        return contactsService.getContact(contactId);
    }

    @GetMapping("/contacts")
    public ContactsResponse getContacts(
            @RequestParam(defaultValue = "100") @Min(0) @Max(200) int limit,
            @RequestParam(defaultValue = "0") @Min(0) int offset,
            @RequestParam(required = false) @PunctuationFree String starts) {
        return contactsService.getContacts(limit, offset, starts);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST);
        ex.getConstraintViolations().stream().forEach(violation -> {
            String field = null;
            for (Node node : violation.getPropertyPath()) {
                field = node.getName();
            }
            error.addMessage(field, violation.getMessage());
        });
        return new ResponseEntity<ErrorResponse>(error, new HttpHeaders(), error.getStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeException(MethodArgumentTypeMismatchException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST);
        String field = ex.getParameter().getParameterName();
        error.addMessage(field, ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, new HttpHeaders(), error.getStatus());
    }

}
