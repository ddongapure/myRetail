package com.springrestapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ddongapu on 8/22/2018.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product not found.")
public class ProductNotFoundException extends RuntimeException {
}
