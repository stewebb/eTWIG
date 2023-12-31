/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
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