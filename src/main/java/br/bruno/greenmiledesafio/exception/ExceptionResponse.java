package br.bruno.greenmiledesafio.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String error;
    private String message;

    public static final class Builder{
        private HttpStatus status;
        private String error;
        private String message;

        private Builder(){
        }

        public static Builder anExceptionResponseBuilder(){
            return new Builder();
        }

        public Builder withStatus(HttpStatus status){
            this.status = status;
            return this;
        }

        public Builder withError(String error){
            this.error = error;
            return this;
        }

        public Builder withMessage(String message){
            this.message = message;
            return this;
        }

        public ExceptionResponse build(){
            return new ExceptionResponse(LocalDateTime.now(), this.status, this.error, this.message);
        }
    }

    public ExceptionResponse(LocalDateTime timestamp, HttpStatus status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
