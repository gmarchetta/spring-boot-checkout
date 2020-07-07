package com.ecomm.checkout.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Discount type not implemented yet.")
public class DiscountTypeNotImplementedException extends RuntimeException {
}
