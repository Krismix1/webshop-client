package dk.cristi.app.webshop.client.models.domain;

import java.util.Date;

public class ExceptionResponse {
    private String message;
    private String details;
    private Date timestamp;

    public ExceptionResponse(Date timestamp, String message, String details) {
        super();
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}