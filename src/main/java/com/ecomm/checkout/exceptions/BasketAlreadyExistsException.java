package com.ecomm.checkout.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="A basket already exists for the user")
public class BasketAlreadyExistsException extends RuntimeException {
}
