package webshop.controllers.http_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class Http404Exception extends RuntimeException {
    public Http404Exception() {
        super("Entity not found");
    }

    public Http404Exception(String s) {
        super(s);
    }
}
