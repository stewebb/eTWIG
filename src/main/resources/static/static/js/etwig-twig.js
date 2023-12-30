MIN_WINDOW_WIDTH = 1280;
MIN_WINDOW_HEIGHT = 720;
ASPECT_RATIO = [16, 9];

function setup() {
	//var TWIGResulution = setTWIGResolution();
	//createCanvas(TWIGResulution[0], TWIGResulution[1]);
}

function draw() {
	
	//fill(0);
 	//ellipse(100, 100, 100);
 	var TWIGResulution = setTWIGResolution();
 	
 	//console.log(TWIGResulution[0], TWIGResulution[1])
 	
 	createCanvas(TWIGResulution[0], TWIGResulution[1]);
 	
 	stroke(255, 0, 0);
 	rect(0, 0, TWIGResulution[0], TWIGResulution[1]);
}

/**
 * Set the TWIG resolution based on the current web window resolution, with the following rules:
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