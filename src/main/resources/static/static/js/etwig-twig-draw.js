/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: Draw the public TWIG! Based on p5.js.
 */

/**
 * Constants
 */

// Minimum window size.
MIN_WINDOW_WIDTH = 1280;
MIN_WINDOW_HEIGHT = 720;

// Fixed aspect ratio.
ASPECT_RATIO = [16, 9];

// Default backgroun color.
DEFAULT_BACKGROUND = "#000000";

/**
 * Classes
 */

class Settings{
	
	/**
	 * The class for settings from the URL parameter.
	 */
	
	constructor(){
		
		/**
		 * portfolioId, -1 stands for all portfolios (default).
		 */
		
		this.portfolio = -1;
		this.portfolioDefault = -1;
		
		/**
		 * Week of the given date, by default is this week.
		 */
		
		this.date = Date.today();
		this.dateDefault = Date.today();
		
		/**
		 * TWIG size, in a choice of:
		 * -1p: Your browser's resolution (default)
		 * 720p: 1280*720
		 * 1080p: 1920*1080
		 * 2k: 2560*1440
		 * 4k: 3840*2160
		 */
		
		this.resolution = "-1p";
		this.resolutionDefault = "-1p";
	}
	
	setPortfolio(portfolio){
		this.portfolio = Number.isInteger(portfolio) ? portfolio : -1;
	}
	
	setDate(date){
		this.date = date;
	}
	
	setResolution(resolution){
		switch (resolution){
			case "720p":
			case "1080p":
			case "2k":
			case "4k":
				this.resolution = resolution;
			default:
				this.resolution = "-1p";
		}
	}
	
	reset(){
		this.portfolio = this.portfolioDefault;
		this.date = this.dateDefault;
		this.resolution = this.portfolioDefault;
	}
}

/**
 * Variables
 */

var setting = new Settings();

var twigHasTemplate = false;

// Background
var backgroundObj;
var backgroundContent;


/**
 * p5.js functions.
 */

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
 * Drawings
 */

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

/**
 * Data sources
 */

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

/**
 * Other helpers
 */
