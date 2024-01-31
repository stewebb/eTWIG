/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
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

// Default background color.
DEFAULT_BACKGROUND = "#000000";

TEXT_LINE_SPACING = 1.5;

/**
 * Classes
 */

/**
 * The class for settings from the URL parameter.
 */
	
class Settings{
	
	/**
	 * Initialize the variables.
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
		
		// Null check
		if(portfolio == null || portfolio == undefined){
			this.portfolio = -1;
		}
		
		// Number check
		else{
			this.portfolio = (portfolio % 1 === 0) ? portfolio : -1;
		}
		
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
var isTwigReady = true;

// All errors that prevent TWIG be displayed.
var errorDescription = "";

// Current portfolio (-1 stands for all portfolios)
var portfolio = -1;

// Template: Background
var backgroundContent;

// Template: Logo
var logoContent;
var logoStyle;


var twigSize;

/**
 * p5.js functions.
 */

function preload(){
	
	// Step 1: Get and apply settings.
	getSettingsFromUrl();
	if(!isTwigReady){
		return;
	}
		
	// Step 2: Get and apply the template.
	getTWIGTemplate();
	if(!isTwigReady){
		return;
	}
	
     isTwigReady = true;
}

function setup() {
	if(!isTwigReady){
		return;
	}
}

function draw() {
	
	// Set the TWIG size.
 	twigSize = setTwigSize();
 	createCanvas(twigSize[0], twigSize[1]);
 	
 	// Draw the TWIG content, or show the error info.
 	isTwigReady ? 	drawTwig() : showError();
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
 * 1. The minimum resolution applies.
 * 2. A fixed aspect ratio applies.
 * @returns A 2-element array that contains TWIG width and TWIG height.
 */

function setTwigSize(){
	
	var twigWindowWidth = 0;
	var twigWindowHeight = 0;
	var resolution = setting.resolution;
	
	// Apply the resolution settings.
	switch (resolution){
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
	
	// Calculate the height and ensure they greater than the minimum resolution.
	twigWindowHeight = twigWindowWidth * ASPECT_RATIO [1] / ASPECT_RATIO[0];
	twigWindowHeight =  max(MIN_WINDOW_HEIGHT, twigWindowHeight);
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
		portfolio = setting.portfolio;		
		var currentPortfolio = getPortfolio(portfolio);
		
		// Portfolio may not exist in the database.
		if(currentPortfolio == undefined){
			errorDescription = "Portfolio with id=" + portfolio + " doesn't exist.";
			isTwigReady = false;
			return;
		}
		
		if(currentPortfolio.separatedCalendar == false){
			errorDescription = "Portfolio " + currentPortfolio.name + " is not allowed to have a separate TWIG.";
			isTwigReady = false;
			return;
		}
	}
}

/**
 * Drawings
 */

/**
 * Show TWIG content if nothing goes wrong.
 */

function drawTwig(){
	
	// Step 1: Draw the background
	background(backgroundContent);
	
	// Step 2: Put the logo on the TWIG
	if(logoContent != undefined && logoStyle != undefined){
		image(
			logoContent, 											// image source
			0.01 * logoStyle.posX * twigSize[0], 		// X coordinate
			0.01 * logoStyle.posY * twigSize[1],		// Y coordinate
			0.01 * logoStyle.size * twigSize[1],			// width
			0.01 * logoStyle.size * twigSize[1]			// height
		);
	}
}

/**
 * Show error information if something goes wrong.
 */

function showError(){
	
	// Black background
	background(DEFAULT_BACKGROUND);
	
	// Error title (yellow)
	var failTitleHeight = twigSize[1] * 0.05;
	textSize(failTitleHeight);
	textStyle(BOLD); 
	fill("#FFFF00");
  	text('Failed to display TWIG', 0, failTitleHeight);
  	
  	// Error description (white)
  	var errorDescriptionHeight = failTitleHeight * 0.50;
  	textSize(errorDescriptionHeight);
  	fill("#FFFFFF");
  	text(errorDescription, 0 , failTitleHeight + 2*errorDescriptionHeight*TEXT_LINE_SPACING, twigSize[0]);
	
	// Display current time at the bottom
	text(Date.today().setTimeToNow(), 0 , twigSize[1] - errorDescriptionHeight*0.5);
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
				errorDescription = "Failed to get portfolio.\n"+ json.msg;
				isTwigReady = false;
				return;
			}
			currentPortfolio = json.portfolio;
			//console.log(json)
		},
    	error: function(err) {   		
			errorDescription = "Failed to get portfolio due to a HTTP " + err.status + " error.\n" + err.responseJSON.exception;
			isTwigReady = false;
		}
	});
	return currentPortfolio;
}

function getTWIGTemplate(){
	$.ajax({ 
		type: 'GET', 
    	url: '/api/public/getTwigTemplateByPortfolioAndDate', 
    	async: false,
    	data: { 
			portfolioId: portfolio,
			date: setting.date.toString("yyyy-MM-dd")
		}, 
    	dataType: 'json',
		success: getTWIGTemplateWhenSuccess,
    	error: function(err) {   
			dangerPopup("Failed to get TWIG content due to a HTTP " + err.status + " error", err.responseJSON.exception)	
			//errorDescription = "F.\n" + err.responseJSON.exception;
			//isTwigReady = false;
		}
	});
}

function getTWIGTemplateWhenSuccess(json){
	
	// No HTTP error, but the input is invalid or other error happens.
	if(json.error > 0){
		errorDescription = "Failed to get TWIG template.\n"+ json.msg;
		isTwigReady = false;
		return;
	}
	
	// Template cannot be found.
	if(json.template == null){
		errorDescription = "Cannot find a TWIG template by the given condition. \nportfolioId=" + portfolio + " ,date=" + setting.date.toString("yyyy-MM-dd");
		isTwigReady = false;
		return;
	}
	
	// Step 1: Set the background object.
	var backgroundObj = json.template.background;
	if(backgroundObj.enabled){
		
		// Case 1.1: Solid color background.
		if(backgroundObj.mode == "color"){
			backgroundContent = backgroundObj.value;
		}
	
		// Case 1.2: Image background.
		else if (backgroundObj.mode == "image"){
			backgroundContent = loadImage("/twig/assets?assetId=" + backgroundObj.value);
		}
	
		// Case 1.3: Undefined background, use a default color.
		else{
			backgroundContent = DEFAULT_BACKGROUND;
		}
	}
	
	// Case 1.0: The background is disabled
	else{
		backgroundContent = DEFAULT_BACKGROUND;
	}
	
	// Step 2: Set the logo.
	var logoObj = json.template.logo;
	if(logoObj.enabled){
		
		// Case 2.1: The logo is enabled.
		
		// Get logo size and position.
		var logoSize = logoObj.size;
		var logoPos = logoObj.position.split(",");
		var logoPosNum = stringArrayToNumberArray(logoPos);
		
		// Case 2.1.1: Validation passed
		if(logoSize > 0 && logoSize < 100 && logoPosNum != undefined && coordinateValidate(logoPosNum, 2, 1, 99)){
			
			// Get logo content, which is always an image
			logoContent = loadImage("/twig/assets?assetId=" + logoObj.image);
			
			// Assign logo position to an object
			logoStyle = {
				size: logoSize,
				posX: logoPosNum[0],
				posY: logoPosNum[1]
			}
		}
		
		// Case 2.1.2: Validation failed.
		else{
			logoContent = undefined;
			logoStyle = undefined;
		}
	}
	
	// Case 2.0: The logo is disabled
	else{
		logoContent = undefined;
		logoStyle = undefined;
	}
	
	//console.log(logoStyle);
}

/**
 * Other helpers
 */

function stringArrayToNumberArray(stringArr){
	var numberArr = [];
	for(var i=0; i<stringArr.length; i++){
		var current = parseInt(stringArr[i], 10);
		
		// If the array contains at least one non-number, return "undefined" immediately.
		if(isNaN(current)){
			return undefined;
		}
		
		numberArr.push(current);
	}
	
	return numberArr;
}

/**
 * Validate a coordinate array (at least 1-dimension).
 * @param arr The coordinate array.
 * @param length the excepted length.
 * @param minVal The minimum accepted value. 
 * @param maxVal The maximum accepted value
 * @note for minVal and maxVal, undefined or null means there has no such restriction.
 * @returns True if the array has at least 1 dimension, has excepted length, and all its values between the minVal and maxVal.
 * 					False otherwise.
 */

 function coordinateValidate(arr, length, minVal, maxVal){
	 
	 // Check the length first. (i.e., dimension)
	 var arrLength = arr.length;
	 if(arrLength <= 0 || length != arrLength){
		 return false;
	 }
	 
	 // Check the minimum value
	 if(minVal != undefined && minVal != null){
		 for(var i=0; i<arrLength; i++){
			 if(arr[i] < minVal){
				  return false;
			  }
		 }
	}
	
	// Check the maximum value
	if(maxVal != undefined && maxVal != null){
		for(var i=0; i<arrLength; i++){
			 if(arr[i] > maxVal){
				  return false;
			  }
		 }
	}
	
	return true;
 }