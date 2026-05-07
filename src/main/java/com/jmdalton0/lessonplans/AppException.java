package com.jmdalton0.lessonplans;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Something went wrong.")
public class AppException extends RuntimeException {
    
}
