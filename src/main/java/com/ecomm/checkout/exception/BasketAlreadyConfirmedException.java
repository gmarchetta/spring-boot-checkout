package com.ecomm.checkout.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 */
@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Can not delete basket since it was already confirmed previously.")
public class BasketAlreadyConfirmedException extends RuntimeException {
}
