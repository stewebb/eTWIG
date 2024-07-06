/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: High-level popups modules that based on AdminLTE popups.
 	*/

/**
 * Displays a success popup notification.
 *
 * This function triggers a SweetAlert2 popup with a success icon and a customized message.
 * The popup automatically closes after a brief duration without displaying a countdown.
 *
 * @param {string} title - The title displayed in the popup, which is typically a success message.
 */

function successPopup(title) {
    Swal.fire({
        icon: "success",
        title: "Success",
        timer: 2000,
        html: `
            <div style="text-align: left;">
                <strong>${title}</strong>
                <p>This popup will close shortly.</p>
            </div>`
    });
}

/**
 * A popup that used in informative results.
 * @param title The title of the popup.
 * @param body The content of the popup (optional).
 */

function infoPopup(title, body){
	Swal.fire({
		icon: "info",
		title: "Information",
		html: `
			<div style="text-align: left;">
				<strong>${title}:</strong> 
				<p>${body}</p>
			</div>`,
	  });
}

/**
 * A popup that used in failure results that not caused by an system error.
 * @param title The title of the popup.
 * @param body The content of the popup (optional).
 */

function warningPopup(title, body){
	Swal.fire({
		icon: "warning",
		title: "Warning",
		html: `
			<div style="text-align: left;">
				<p><strong>${title}:</strong></p>
				<p>${(body == undefined) ? "" : body}</p>
			</div>`,
	  });
}
/**
 * A popup that used in errors.
 * @param title The title of the popup.
 * @param body The content of the popup (optional).
 */

function dangerPopup(title, body){
	Swal.fire({
		icon: "error",
		title: "Error",
		html: `
			<div style="text-align: left;">
				<strong>${title}:</strong> 
				<p>${body}</p>
			</div>`,
	  });
}

/**
 * Calculates how much time has passed since a given date string.
 * The function parses the date string, computes the difference with the current date and time,
 * and returns a human-readable string representing the time elapsed.
 *
 * @param {string} dateStr - The date string in ISO 8601 format (YYYY-MM-DDTHH:MM:SS). Milliseconds and timezone offset are optional.
 * @returns {string} A human-readable string indicating the time elapsed since the date provided.
 *                   Returns 'In the future' if the date is ahead of the current date.
 *
 * Example usage:
 * timeAgo('2023-07-04T12:00:00'); // Returns 'X hours ago', 'X days ago', etc., depending on the current date and time.
 */

function timeAgo(dateStr) {
	
	// Remove the milliseconds, if needed.
	dateStr = dateStr.split(".")[0];
	
	// Do not use Date.today(), as the hours, minutes and seconds will be removed!
    var now = new Date();
    var date = Date.parse(dateStr);
    var diff = now - date;
	
    if (diff < 0) {
        return "In the future";
    }

    var seconds = Math.floor(diff / 1000);
    var minutes = Math.floor(seconds / 60);
    var hours = Math.floor(minutes / 60);
    var days = Math.floor(hours / 24);
    var months = Math.floor(days / 30);
    var years = Math.floor(months / 12);

    if (years > 0) return years + " years ago";
    if (months > 0) return months + " months ago";
    if (days > 0) return days + " days ago";
    if (hours > 0) return hours + " hours ago";
    if (minutes > 0) return minutes + " minutes ago";
    return "Just now";
}

/**
 * Formats the duration from minutes into a readable string representing the time in days, hours, and minutes.
 * This function computes the total days, hours, and minutes from a given number of total minutes.
 * It formats these durations into a human-readable string.
 * For example, an input of 70 minutes will output "1 hour, 10 minutes".
 *
 * @param {number} minutesTotal - The total number of minutes to format.
 * @returns {string} A string that represents the formatted duration combining days, hours, and minutes as needed.
 *                   Days are added to the string if the total minutes amount to one full day or more.
 *                   Hours are shown unless they are zero, and minutes are shown unless they are zero.
 *                   Each unit is correctly pluralized.
 *
 * Example usage:
 * formatTime(1441); // Returns "1 day, 1 minute"
 */

function formatTime(minutesTotal) {
    const minutesPerHour = 60;
    const minutesPerDay = 1440;

    const days = Math.floor(minutesTotal / minutesPerDay);
    const hours = Math.floor((minutesTotal % minutesPerDay) / minutesPerHour);
    const minutes = minutesTotal % minutesPerHour;
    var formattedTime = "";

    if (days > 0) {
        formattedTime += days + (days === 1 ? " day" : " days");
    }

    if (hours > 0) {
        if (formattedTime.length > 0) formattedTime += ", ";
        formattedTime += hours + (hours === 1 ? " hour" : " hours");
    }

    if (minutes > 0) {
        if (formattedTime.length > 0) formattedTime += ", ";
        formattedTime += minutes + (minutes === 1 ? " minute" : " minutes");
    }

    return formattedTime;
}

/**
 * Constrains a number to be within a specified range.
 * If the number is less than the minimum boundary, it returns the minimum boundary.
 * If the number is more than the maximum boundary, it returns the maximum boundary.
 * If the number is within the range, it returns the number unchanged.
 *
 * @param {number} num - The number to constrain.
 * @param {number} min - The minimum boundary of the range.
 * @param {number} max - The maximum boundary of the range.
 * @returns {number} The constrained number, guaranteed to be within the [min, max] range.
 *
 * Example usage:
 * constrainNumber(5, 1, 10);  // Returns 5
 * constrainNumber(-3, 1, 10); // Returns 1
 * constrainNumber(15, 1, 10); // Returns 10
 */

function constrainNumber(num, min, max){
	if(num < min)	return min;
	if(num > max)	return max;
	return num;
}

/**
 * Combines a Date object with a time string to create a new Date object set to the specified time on the same date.
 * Validates the input to ensure the first parameter is a Date object and the second is a time string in the format 'hh:mm:ss'.
 * If either parameter is invalid, logs an error to the console and terminates execution without returning a value.
 *
 * @param {Date} date - The Date object representing the date.
 * @param {string} timeString - The time string in 'hh:mm:ss' format to set the time of the date.
 * @returns {Date|null} A new Date object with the combined date and time, or null if there is an error with the input.
 */

function combineDateAndTime(date, timeString) {
    // Ensure the input is a Date object
    if (!(date instanceof Date)) {
        console.error("First argument must be a Date object.");
        return;
    }

    // Ensure the time string is in the correct format
    if (!/^\d{2}:\d{2}:\d{2}$/.test(timeString)) {
        console.error("Time string must be in the format 'hh:mm:ss'.");
        return;
    }

    // Split the time string into hours, minutes, and seconds
    const [hours, minutes, seconds] = timeString.split(':').map(Number);

    // Create a new Date object with the same date but with specified time
    const combinedDateTime = new Date(date);
    combinedDateTime.setHours(hours, minutes, seconds);

    return combinedDateTime;
}

/**
 * Retrieves the name of the browser based on the provided user agent string.
 * If no user agent is provided, the function uses the browser's default user agent.
 *
 * The function identifies major browsers like Chrome, Firefox, Safari, Opera,
 * Edge, and Internet Explorer, including distinctions for mobile versions on iOS.
 * If the browser cannot be determined, it defaults to "Unknown Browser".
 *
 * @param {string} [userAgent=navigator.userAgent] - The user agent string to analyze.
 * @returns {string} - The name of the browser identified from the user agent.
 */

function getBrowserName(userAgent) {
    userAgent = userAgent || navigator.userAgent;
    let browserName = "Unknown Browser";

    if (userAgent.match(/chrome|chromium|crios/i)) {
        browserName = "Chrome";
    } else if (userAgent.match(/firefox|fxios/i)) {
        browserName = "Firefox";
    } else if (userAgent.match(/safari/i)) {
        browserName = "Safari";
    } else if (userAgent.match(/opr\//i)) {
        browserName = "Opera";
    } else if (userAgent.match(/edg/i)) {
        browserName = "Edge";
    } else if (userAgent.match(/msie|trident/i)) {
        browserName = "Internet Explorer";
    }

    // Special case for Safari and Chrome on iOS
    if (userAgent.match(/iphone|ipad|ipod/i)) {
        if (userAgent.match(/crios/i)) {
            browserName = "Chrome";
        } else if (userAgent.match(/fxios/i)) {
            browserName = "Firefox";
        } else {
            browserName = "Safari";
        }
    }

    return browserName;
}

/**
 * Opens a modal dialog for selecting or uploading files.
 * This function sets the title of the modal to "Select/Upload" and then
 * loads the file selection and upload interface from a specified URL into
 * the modal's body. Finally, it displays the modal on the screen.
 *
 * Assumes the existence of HTML elements with specific IDs: 'etwigModalTitle',
 * 'etwigModalBody', and 'etwigModal', and that these elements are part of a modal
 * component (e.g., Bootstrap modal). Also assumes jQuery is loaded for DOM manipulation
 * and AJAX capabilities.
 */

function selectUpload(){
	$('#etwigModalTitle').text('Select/Upload');
	$("#etwigModalBody").load("/assets/_selectFile.do");
	$('#etwigModal').modal('show');
}

/**
 * Attempts to change the user's role by sending an asynchronous GET request to the specified API endpoint.
 * The role ID to switch to is fetched from a select element with the ID 'selectRole'.
 * Upon successful change, a popup confirms the new role with a visual color indicator and the page
 * is reloaded after a 2.5-second delay to refresh the page.
 * If the request fails, particularly if the action is forbidden (HTTP 403) or another error occurs,
 * an error popup is displayed with a relevant message.
 */

function selectRole(){
	$.ajax({
   		url: '/api/position/switch', 
   		type: "GET",
   		data: {
            "userRoleId" : $('#selectRole').val()
        },
   		success: function (result) {
            successPopup(`
                You have switched your position to <span style="color:#${result.portfolioColor}">${result.position}</span>.
            `);
			setTimeout(function() {
                window.location.reload();
            }, 2500);
    	},
    	error: function (err) {

            // Special consideration for HTTP 403 and display a user-friendly message.
            if(err.status == 403){
                dangerPopup("You cannot switch to this position.", "");
            }else{
                dangerPopup("Failed to switch position due to a HTTP " + err.status + " error.", err.responseJSON.exception);
            }
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


/**
 * Hide the navbar if the page is in a frame.
 */

$.fn.dataTable.ext.errMode = function (settings, helpPage, message ) { 
    warningPopup('Warning when fetching data', message);
};

$(document).ready(function() {
	//if (window.self !== window.top) {
	//	$('.navbar').hide();
	//}

    $('.select2bs4').select2({
    	theme: 'bootstrap4'
    })

    $('.dropdown-menu').on('click', function(e) {
        e.stopPropagation();
    });

    $('.confirm-btn').each(function() {
        var timeout;
        var originalText = $(this).html(); // Store the original text

        $(this).click(function() {
            var $this = $(this);
            var $textElement = $this.next('.confirmation-text');
            var actionData = JSON.parse($this.attr('data-action')); // Parse the JSON data

            if ($this.data('confirmed') === true) {
                // Dynamically call the function with parameters
                if (typeof window[actionData.functionName] === 'function') {
                    window[actionData.functionName].apply(null, actionData.params);
                } else {
                    dangerPopup('Function ' + actionData.functionName + ' does not exist.', '');
                }

                // Reset to the original text and state
                $this.html(originalText).data('confirmed', false);
                $textElement.hide();
                clearTimeout(timeout);
            } else {
                $this.html('<i class="fa-solid fa-check-double"></i>&nbsp;Click again to confirm');
                $textElement.show();
                $this.data('confirmed', true);

                // Set a timeout to reset the button after 10 seconds
                clearTimeout(timeout); // Clear any previous timeout
                timeout = setTimeout(function() {
                    $this.html(originalText).data('confirmed', false); // Restore original text
                    $textElement.hide();
                }, 10000); // 10 seconds timeout
            }
        });
    });

});
           
// Leading zeros for the (positive) integers that below to 10. 
const pad = (num) => (num < 10 ? '0' + num : num);

/**
 * Validates an email address against a standard pattern.
 * This function checks if the given email address conforms to a common email format,
 * which includes characters before and after an "@" symbol, followed by a domain
 * and a domain extension ranging from 2 to 6 letters.
 *
 * @param {string} email - The email address to be validated.
 * @return {boolean} True if the email matches the pattern, otherwise false.
 *
 * Usage Example:
 * ```javascript
 * let isValidEmail = emailCheck("example@domain.com");
 * console.log(isValidEmail); // Output: true or false
 * ```
 */

function emailCheck(email) {
    var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    return emailPattern.test(email);
}