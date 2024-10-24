package br.com.mateusberger.challenge.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid information was requested")
public class InvalidInformationException extends RuntimeException {
}
