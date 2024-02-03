/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: High-level Toasts modules that based on AdminLTE Toasts.
 	*/

/**
 * A toast that used in successful results.
 * @param title The title of the toast.
 * @param body The content of the toast (optional).
 */

function successToast(title, body){
	$(document).Toasts('create', {
  		title: title,
  		body: body,
  		autohide: true,
  		delay: 5000,
  		icon: 'fa-solid fa-circle-check',
  		class: 'toast bg-success'
	});
}

/**
 * A toast that used in informative results.
 * @param title The title of the toast.
 * @param body The content of the toast (optional).
 */

function infoToast(title, body){
	$(document).Toasts('create', {
  		title: title,
  		body: body,
  		autohide: true,
  		delay: 5000,
  		icon: 'fa-solid fa-circle-info',
  		class: 'toast bg-info'
	});
}

/**
 * A toast that used in failure results that not caused by an system error.
 * @param title The title of the toast.
 * @param body The content of the toast (optional).
 */

function warningToast(title, body){
	$(document).Toasts('create', {
  		title: title,
  		body: body,
  		autohide: true,
  		delay: 5000,
  		icon: 'fa-solid fa-circle-exclamation',
  		class: 'toast bg-warning'
	});
}

function warningPopup(title, body){
	Swal.fire({
		icon: "warning",
		title: "Warning",
		html: `
			<div style="text-align: left;">
				<strong>${title}:</strong> 
				<p>${body}</p>
			</div>`,
	  });
}
/**
 * A toast that used in errors.
 * @param title The title of the toast.
 * @param body The content of the toast (optional).
 */

function dangerToast(title, body){
	$(document).Toasts('create', {
  		title: title,
  		body: body,
  		autohide: true,
  		delay: 10000,
  		icon: 'fa-solid fa-circle-xmark',
  		class: 'toast bg-danger'
	});
}

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

function formatState(state) {
	var option = $(state.element);
  	var color = option.data("color");
  	var icon = option.data("icon");
  	
  	if (!color) {
    	 return state.text;
  	}
  	
  	if(!icon){
		icon = 'square';
	}
  		
  	return $(`<span style="color: ${color};background-color: #FFF">&nbsp;<i class="fa-solid fa-${icon}"></i>${state.text}&nbsp;</span>`);
};

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

function getMyPositions(){	
	var position = {};
	
	$.ajax({ 
		type: 'GET', 
    	url: '/api/private/getMyPositions', 
    	async: false,
		success: function(json) {
			position = json;
			// Iterate all roles.
			//jQuery.each(json, function(id, value) {
			//	$(selectElem).append(`<option value="${value.userRoleId}">${value.position}, ${value.portfolio.name}</option>`);
			//})
        },
        
        // Toast error info when it happens
    	error: function(err) {   		
			dangerToast("Failed to get user positions due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		}
	});
	
	return position;
}

/**
 * Hide the navbar if the page is in a frame.
 */

$(document).ready(function() {
	if (window.self !== window.top) {
		$('.navbar').hide();
	}
});
           
// Leading zeros for the (positive) integers that below to 10. 
const pad = (num) => (num < 10 ? '0' + num : num);