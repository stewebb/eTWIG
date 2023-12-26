/**
 * A toast that used in successful results.
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