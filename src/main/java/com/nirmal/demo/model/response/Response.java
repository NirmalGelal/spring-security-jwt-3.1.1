package com.nirmal.demo.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Response<T> {
    private String message;
    private boolean status;
    private T data;

    public void setSuccessValues(T object, String message) {
        this.setStatus(true);
        this.setData(object);
        this.setMessage(message);
    }

    public void setFailureValues(String message) {
        this.data = null;
        this.setStatus(false);
        this.setMessage(message);
    }
}
