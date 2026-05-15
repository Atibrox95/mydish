package com.nerea.mydish.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MyDishException extends RuntimeException {
	private static final long serialVersionUID = -2691043631070431641L;
	private final HttpStatus status;

	//Mi propia excepción
    public MyDishException(String mensaje, HttpStatus status) {
        super(mensaje);
        this.status = status;
    }
}