package com.number26.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.number26.exception.IDExistException;


public class BaseApiContoller {
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String handleBadRequest(final Exception ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(IDExistException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String handleIDException(final IDExistException ex) {
		return ex.getMessage();
	}
}
