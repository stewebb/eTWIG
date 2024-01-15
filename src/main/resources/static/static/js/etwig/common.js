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

// Leading zeros for the (positive) integers that below to 10. 
const pad = (num) => (num < 10 ? '0' + num : num);