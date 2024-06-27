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

function timeAgo(dateStr) {
	
	// Remove the milliseconds, if needed.
	dateStr = dateStr.split(".")[0];
	
	// Do not use Date.today(), as the hours, minutes and seconds will be removed!
    var now = new Date();
    var date = Date.parse(dateStr);
    var diff = now - date;

	//console.log("now", now);
	//console.log("date", date);
	
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
 * Format the duration from minutes to day, hour and minutes.
 * e.g., input 70, output 1 hour, 10 minutes.
 * @param {int} minutesTotal The total minutes
 * @returns The duration string that combines with days, hours and minutes
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
 * Hide the navbar if the page is in a frame.
 */

$(document).ready(function() {
	if (window.self !== window.top) {
		$('.navbar').hide();
	}

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

// dangerPopup('Function ' + functionName + ' does not exist.', '');
function functionTwo(param) {
    alert('Function Two is executed with parameter: ' + param);
}