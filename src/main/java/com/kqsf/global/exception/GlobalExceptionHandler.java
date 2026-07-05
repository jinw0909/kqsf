package com.kqsf.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PageNotReadyException.class)
    public String handlePageNotReady(PageNotReadyException e, Model model) {
        model.addAttribute("eyebrow", "Coming Soon");
        model.addAttribute("title", "준비중입니다.");
        model.addAttribute("message", "아직 공개되지 않았거나 존재하지 않는 페이지입니다.");

        return "site/coming-soon";
    }
}
