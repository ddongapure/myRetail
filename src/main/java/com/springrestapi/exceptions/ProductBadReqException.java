package com.springrestapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ddongapu on 8/22/2018.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Id in request header and body doesn't match.")
public class ProductBadReqException extends RuntimeException{

}
