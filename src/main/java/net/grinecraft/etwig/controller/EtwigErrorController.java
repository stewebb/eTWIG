package net.grinecraft.etwig.controller;

import java.util.Map;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.boot.web.error.ErrorAttributeOptions;

@Controller
public class EtwigErrorController implements ErrorController {
	
    private final ErrorAttributes errorAttributes;

    public EtwigErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(WebRequest webRequest, Model model, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
    	
    	// Error details
    	ErrorAttributeOptions options = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE);
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, options);
        model.addAttribute("error", errorAttributes);
        
        
        // The original request
        String path = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        model.addAttribute("path", path);
        
        // Convention: The location of all apis are start with /api
        if(path.startsWith("/api")) {
        	response.setContentType("application/json");
        	return "errorJson";
        }
        
        // Normal pages
        else {
        	
        	response.setContentType("text/html");
            return "errorPage"; 
        }
        
    }
}