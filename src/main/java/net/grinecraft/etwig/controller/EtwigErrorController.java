package net.grinecraft.etwig.controller;

import java.util.Map;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import org.springframework.boot.web.error.ErrorAttributeOptions;

@Controller
public class EtwigErrorController implements ErrorController {
	
    private final ErrorAttributes errorAttributes;

    public EtwigErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(WebRequest webRequest, Model model) {
    	ErrorAttributeOptions options = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE);
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, options);

        model.addAttribute("error", errorAttributes);
        return "error"; 
    }
}