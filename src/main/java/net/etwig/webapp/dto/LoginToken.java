package net.etwig.webapp.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginToken {

    private String controlID;
    private Long timestamp;
    private String userEmail;
    private String userName;
}
