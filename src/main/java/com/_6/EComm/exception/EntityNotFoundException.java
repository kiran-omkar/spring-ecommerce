package com._6.EComm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
  public EntityNotFoundException(String entityName, Object id) {
    super(String.format("%s with id %s not found", entityName, id));
  }
  public EntityNotFoundException(String message) {
    super(message);
  }
}
