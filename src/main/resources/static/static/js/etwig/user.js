/**
 * Attempts to change the user's password through an AJAX call to the server.
 * 
 * This function first validates the current password, new password, and password confirmation input fields.
 * It checks for non-empty input, password match between new password and its confirmation, and complexity
 * requirements for the new password. It uses jQuery to handle input value retrieval and trimming.
 * 
 * Upon successful validation, it packages the current and new passwords into a JSON object and sends
 * them to the server via a POST request. If the server responds positively, it will show a success message and reload the page after a delay.
 * Otherwise, it will display appropriate error messages based on the error type (e.g., incorrect current password or HTTP error).
 * 
 * Dependencies:
 * - jQuery for DOM manipulation and AJAX call
 * - `isPasswordComplex(password)`: Validates password complexity, ensuring it meets the length and character requirements.
 * 
 * @requires jQuery
 * @see isPasswordComplex
 */

function changePassword(){
	
	// Current password
	var currentPassword = $.trim($('#currentPassword').val());
	if(currentPassword.length == 0){
		warningPopup("Current password is required.");
		return;
	}
	
	// New password
	var newPassword = $.trim($('#newPassword').val());
	if(newPassword.length == 0){
		warningPopup("New password is required.");
		return;
	}
	
	// Confirm password
	var confirmNewPassword = $.trim($('#confirmNewPassword').val());
	if(newPassword != confirmNewPassword){
		warningPopup("The new password must equals to the confirm new password.");
		return;
	}
	
	// Password complexity
	if(!isPasswordComplex(newPassword)){
		warningPopup("The new password must be at least 8 characters long and include uppercase, lowercase and numbers.");
		return;	
	}
	
	var passwordObj = {
		currentPassword: currentPassword,
		newPassword: newPassword,
	}
	
	$.ajax({
   		url: '/api/user/changePwd', 
   		type: "POST",
   		contentType: "application/json; charset=utf-8",
   		data: JSON.stringify(passwordObj),
   		success: function (result) {
			if(result){
				successPopup("Password changed successfully.");
				setTimeout(function() {	location.reload(); }, 2500);
			}else{
				warningPopup("Your current password is incorrect.");
			}	
    	},
    	error: function (err) {
    		dangerPopup("Failed to change password due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    	}
 	});
}

/**
 * Checks if a given password meets defined complexity requirements.
 * 
 * This function validates the password based on the following criteria:
 * - Minimum length of 8 characters
 * - Contains at least one uppercase letter
 * - Contains at least one lowercase letter
 * - Contains at least one numerical digit
 *
 * @param {string} password - The password string to be validated.
 * @returns {boolean} Returns `true` if the password meets all complexity requirements, otherwise `false`.
 */

function isPasswordComplex(password) {
    const minLength = 8;
    const hasUpperCase = /[A-Z]/.test(password);
    const hasLowerCase = /[a-z]/.test(password);
    const hasNumbers = /\d/.test(password);
    return password.length >= minLength && hasUpperCase && hasLowerCase && hasNumbers;
}
