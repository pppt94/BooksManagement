package com.example.bookManagement;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping(value="/index", produces = {
            MediaType.TEXT_HTML_VALUE},
            method = RequestMethod.GET)
    public String view () {
        return "index.html";
    }

}
