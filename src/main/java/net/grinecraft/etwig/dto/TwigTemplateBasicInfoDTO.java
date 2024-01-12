package net.grinecraft.etwig.dto;

import java.time.LocalDate;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TwigTemplateBasicInfoDTO {

	private Long id;
	
	private String name;
	
	private String portfolioName;
	
	private LocalDate availableFrom;
	
	private LocalDate availableTo;
	
}
