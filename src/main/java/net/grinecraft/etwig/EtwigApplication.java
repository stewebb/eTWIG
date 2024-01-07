/**
 	* eTWIG - The event management software for university communities.
 	* @copyright: Copyright (c) 2024 Steven Webb
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The main starting point of the eTWIG platform.
 */

package net.grinecraft.etwig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class EtwigApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtwigApplication.class, args);
	}
	
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(EtwigApplication.class);
    }

}
