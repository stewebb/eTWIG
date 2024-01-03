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

/**
 * The class for settings from the URL parameter.
 */
	
class Settings{
	
	/**
	 * Inutialize the variables.
	 */
	
	constructor(){
		this.portfolio = -1;
		this.date = Date.today();
		this.resolution = "-1p";
	}
	
	/**
	 * Set portfolioId, -1 stands for all portfolios (default).
	 */
	
	setPortfolio(portfolio){
		
		// Check the input is a number or not.
		this.portfolio = (portfolio % 1 === 0) ? portfolio : -1;
	}
	
	 
	/**
	  * Set week of the given date, by default is this week.
	  */
	
	setDate(date){
		this.date = Date.parse(date);
	}
		
	/**
	 * SetTWIG size, in a choice of:
	 * -1p: Your browser's resolution (default)
	 *  720p: 1280*720
	 *  1080p: 1920*1080
	 * 2k: 2560*1440
	 * 4k: 3840*2160
	 */
	
	setResolution(resolution){
		switch (resolution){
			case "720p":
			case "1080p":
			case "2k":
			case "4k":
				this.resolution = resolution;
				break;
			default:
				this.resolution = "-1p";
				break;
		}
	}
}

/**
 * Variables
 */

// The default settings
var setting = new Settings();

// Indicate the TWIG is initialized successfully or not.
var isTwigReady = false;

// Background
var backgroundObj;
var backgroundContent;

var portfolio;

/**
 * p5.js functions.
 */

function preload(){
	
	// Step 1: Get and apply settings.
	getSettingsFromUrl();
	if(!isTwigReady){
		return;
	}
	
	// Set the screen resolution
	
	// Step 2: Get the template.
	getTWIGTemplate();
	if(!isTwigReady){
		return;
	}
	
	setBackground();
	
   // backgroundImage = loadImage(backgroundImageURL);
	//backgroundImage = 255;
}

function setup() {
	if(!isTwigReady){
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
 	if(!isTwigReady){
		return;
	}
 	background(backgroundContent);
}



/**
 * Variable assignments
 */

/**
 * Set the TWIG canvas size based on the settings:
 * 1. Fixed resolutions (720p, 1080p, 2k, 4k)
 * 2. The current web window resolution (dynamic when window is dragged),
 * 
 * with the following rules:
 * 1. The minimun resolution applies.
 * 2. A fixed aspect ratio applies.
 * @returns A 2-element array that contains TWIG width and TWIG height.
 */

function setTWIGResolution(){
	
	var twigWindowWidth = 0;
	var twigWindowHeight = 0;
	var resoultion = setting.resolution;
	
	// Apply the resoultion settings.
	switch (resoultion){
		case "720p":
			twigWindowWidth = 1280;
			break;
		case "1080p":
			twigWindowWidth = 1920;
			break;
		case "2k":
			twigWindowWidth = 2560;
			break;
		case "4k":
			twigWindowWidth = 4320;
			break;
		default:
			twigWindowWidth = max(MIN_WINDOW_WIDTH, windowWidth);	// Dynamic
			break;
	}
	
	// Calculate the height and ensure they greater than the minimun resolution.
	twigWindowHeight = twigWindowWidth * ASPECT_RATIO [1] / ASPECT_RATIO[0];
	twigWindowHeight =  max(MIN_WINDOW_WIDTH, twigWindowHeight);
	return [twigWindowWidth, twigWindowHeight];
}

function getSettingsFromUrl(){
	
	// Get settings from URL param.
	var searchParams = new URLSearchParams(window.location.search);
	var portfolioId = searchParams.get('portfolioId');
	var week  = searchParams.get('week');
	var resolution = searchParams.get('resolution');
	
	// Put them into the object
	setting.setPortfolio(portfolioId);
	setting.setDate(week);
	setting.setResolution(resolution);
	
	// Get portfolio, also check them.
	// All portfolios
	if(setting.portfolio < 0){
		portfolio = -1;
	}
	
	// A selected portfolio
	else{
		portfolio = setting.portfolio
		var currentPortfolio = getPortfolio(portfolio);
		
		// Portfolio may not exist in the database.
		if(currentPortfolio == undefined){
			alert("Portfolio with id=" + portfolio + " doesn't exist!");
			isTwigReady = false;
			return;
		}
		
		if(currentPortfolio.seperatedCalendar == false){
			alert("Portfolio " + currentPortfolio.name + " is not allowed to have a seperate TWIG!");
			isTwigReady = false;
			return;
		}
	}
}

/**
 * Drawings
 */

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

function getPortfolio(portfolioId){	
	var currentPortfolio;
	$.ajax({ 
		type: 'GET', 
    	url: '/api/public/getPortfolioById', 
    	async: false,
    	data: {
			portfolioId: portfolioId
		}, 
		success: function(json){
			if(json.error > 0){
				alert("Failed to get TWIG template.\n"+ json.msg);
				return;
			}
			currentPortfolio = json.portfolio;
			//console.log(json)
		},
    	error: function(err) {   		
			alert("Failed to get portfolio due to a HTTP " + err.status + " error.\n" + err.responseJSON.exception);
		}
	});
	return currentPortfolio;
}

function getTWIGTemplate(){
	$.ajax({ 
		type: 'GET', 
    	url: '/api/public/getTwigTemplateById', 
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
	
  	isTwigReady = true;
}

/**
 * Other helpers
 */
