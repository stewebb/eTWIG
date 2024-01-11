/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The main start point of the eTWIG platform.
 */

package net.grinecraft.etwig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import net.grinecraft.etwig.util.UserSession;

@SpringBootApplication
//@ComponentScan(basePackageClasses = {UserSession.class})
public class EtwigApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtwigApplication.class, args);
	}
	
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(EtwigApplication.class);
    }

}
