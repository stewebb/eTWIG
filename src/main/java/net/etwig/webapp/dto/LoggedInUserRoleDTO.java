package net.etwig.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;

@Getter
@ToString
@AllArgsConstructor
public class LoggedInUserRoleDTO {

    private HashMap<Long, String> myRoles;
    private Long myCurrentRole;

    // Check against the current role list before changing a new role/
    public boolean changeMyCurrentRole(Long newRole){
        if(myRoles.containsKey(newRole)){
            myCurrentRole = newRole;
            return true;
        }

        else{
            return false;
        }
    }
}
