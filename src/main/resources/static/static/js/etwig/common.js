/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: High-level popups modules that based on AdminLTE popups.
 	*/

/**
 * A popup that used in successful results.
 * @param title The title of the popup.
 */

function successPopup(title){
	Swal.fire({
		icon: "success",
		title: "Success",
        timer: 2000,
		html: `
            <div style="text-align: left;">
                <strong>${title}</strong>
                <p>This popup will be closed in <b></b> seconds.</p>
            </div>`,
        didOpen: () => {
            //Swal.showLoading();
            const timer = Swal.getPopup().querySelector("b");
            timerInterval = setInterval(() => {
              timer.textContent = (Swal.getTimerLeft()*0.001).toFixed(1);
            }, 100);
          },
          willClose: () => {
            clearInterval(timerInterval);
          }
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

/*
function getMyPositions(){	
	var position = {};
	
	$.ajax({ 
		type: 'GET', 
    	url: '/api/private/getMyPositions', 
    	async: false,
		success: function(json) {
			position = json;
        },
        
        // popup error info when it happens
    	error: function(err) {   		
			dangerPopup("Failed to get user positions due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		}
	});
	
	return position;
}
*/

/**
 * Get the browser name by a given user agent.
 * @param {string} userAgent 
 * @returns The browser name
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
            // This is a simplification, as identifying Safari on iOS just from the user agent can be tricky due to the web view component
            browserName = "Safari";
        }
    }

    return browserName;
}

// TODO READY TO BE REMOVED
function dateRender(data, type, row){
	return data ? Date.parse(data).toString('yyyy-MM-dd HH:mm:ss') : 'N/A'; 
}

function selectUpload(){
	$('#etwigModalTitle').text('Select/Upload');
	$("#etwigModalBody").load("/assets/_selectFile.do");
	$('#etwigModal').modal('show');
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

});
           
// Leading zeros for the (positive) integers that below to 10. 
const pad = (num) => (num < 10 ? '0' + num : num);