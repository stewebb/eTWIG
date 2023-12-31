MIN_WINDOW_WIDTH = 1280;
MIN_WINDOW_HEIGHT = 720;
ASPECT_RATIO = [16, 9];

DEFAULT_BACKGROUND = "#000000";

var twigHasTemplate = false;

var backgroundObj;

var backgroundContent;

function preload(){
	getTWIGTemplate();
	if(!twigHasTemplate){
		return;
	}
	
	setBackground();
	
   // backgroundImage = loadImage(backgroundImageURL);
	//backgroundImage = 255;
}

function setup() {
	if(!twigHasTemplate){
	return;
	}
	//var TWIGResulution = setTWIGResolution();
	//createCanvas(TWIGResulution[0], TWIGResulution[1]);
}

function draw() {
	
	//fill(0);
 	//ellipse(100, 100, 100);
 	var TWIGResulution = setTWIGResolution();
 	
 	//console.log(TWIGResulution[0], TWIGResulution[1])
 	
 	createCanvas(TWIGResulution[0], TWIGResulution[1]);
 	
 	//stroke(255, 0, 0);
 	//rect(0, 0, TWIGResulution[0], TWIGResulution[1]);
 	if(!twigHasTemplate){
		return;
	}
 	background(backgroundContent);
}

/**
 * Set the TWIG canvas size based on the current web window resolution, with the following rules:
 * 1. The minimun resolution applies.
 * 2. A fixed aspect ratio applies.
 * @returns A 2-element array that contains TWIG width and TWIG height.
 */

function setTWIGResolution(){
	var TWIGWindowWidth = max(MIN_WINDOW_WIDTH, windowWidth);
	var TWIGWindowHeight =  max(MIN_WINDOW_WIDTH, windowHeight);
	TWIGWindowHeight = TWIGWindowWidth * ASPECT_RATIO [1] / ASPECT_RATIO[0];
	return [TWIGWindowWidth, TWIGWindowHeight];
}

function setBackground(){
	
	// Case 1: Solid color background.
	if(backgroundObj.mode == "color"){
		backgroundContent = backgroundObj.value;
	}
	
	// Case 2: Image background.
	else if (backgroundObj.mode == "image"){
		backgroundContent = loadImage("/twig/assets?assetId=" + backgroundObj.value);
	}
	
	// Case 3: Undefined background, use a default color.
	else{
		backgroundContent = DEFAULT_BACKGROUND;
	}
}

function getTWIGTemplate(){
	var url = '/api/public/getTwigTemplateById';
	
	$.ajax({ 
		type: 'GET', 
    	url: url, 
    	async: false,
    	data: { 
			templateId: 5,
		}, 
    	dataType: 'json',
		success: getTWIGTemplateWhenSuccess,
    	error: function(err) {   		
			alert("Failed to get TWIG content due to a HTTP " + err.status + " error.\n" + err.responseJSON.exception);
		}
	});
}

function getTWIGTemplateWhenSuccess(json){
	
	// No HTTP error, but the input is invalid or other error happens.
	if(json.error > 0){
		alert("Failed to get TWIG template.\n"+ json.msg);
		return;
	}
	
	// Template cannot be found.
	if(json.template == null){
		alert("Cannot find a TWIG template by the given condition.");
		return;
	}
	
	// Step 1: Set the background object.
	backgroundObj = json.template.background;
	
  	twigHasTemplate = true;
}

