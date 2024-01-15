const CANVAS_WIDTH = 1280;
const CANVAS_HEIGHT = 720;
const CANVAS_SHORT_SIDE = Math.min(CANVAS_WIDTH, CANVAS_HEIGHT);

const RECOMMENDED_WIDTH = 1600;
const RECOMMENDED_HEIGHT = 900;

var templateBackgroundObj = new TemplateBackground();
var templateLogoObj = new TemplateImage(5, 20, 0, 99);
var templateTitleObj = new TemplateImage(5, 20, 0, 99);

//var backgroundImg;

function twigTemplateDataTable(){
	var dt = $('#twigTemplate').DataTable({
        processing: true,
        serverSide: true,
        searching: false, 
        ajax: {
            url: "/api/private/getTwigTemplateList",
            data: function (d) {
                d.page = d.start / d.length;
                d.size = d.length;
                //console.log(d.page)
            },
            type: "GET",
            dataSrc: function (json) {
                return json.content;
            }
        },
        columns: [
            { data: "id" },
            { data: "name" },
            { data: "portfolioName", render: portfolioRender },
            { data: "availableFrom", render: dateRender},
            { data: "availableTo" , render: dateRender},
            {mRender:actionRender}
        ]
    });
    return dt;
}

function portfolioRender(data, type, row){
	return data ? data : 'All portfolios'; 
}

function dateRender(data, type, row){
	return data ? data : 'N/A'; 
}

function actionRender(data, type, full){
	//console.log(full)
	return `
	<div class="btn-group">
		<a href="/graphics/twigTemplate/design?templateId=${full.id}" class="btn btn-outline-primary btn-sm">
			<i class="fa-solid fa-wand-magic-sparkles"></i>
		</a>
		
		<a href="/graphics/twigTemplate/edit?templateId=${full.id}" class="btn btn-outline-secondary btn-sm">
			<i class="fa-solid fa-pencil"></i>
		</a>
			
		<a href="#" class="btn btn-outline-danger btn-sm">
			<i class="fa-solid fa-trash"></i>
		</a>
	</div>
	`;
}

function windowSizeCheck(){
	var width = $(document).width();
	var height = $(document).height();
	console.log(`Window size: ${width}*${height} (px).`)
	
	if(width < RECOMMENDED_WIDTH || height < RECOMMENDED_HEIGHT){
		alert(`For the best user experience, it is recommended to view this page on the window of size greater ${RECOMMENDED_WIDTH}*${RECOMMENDED_HEIGHT} (px).\nYour window size is ${width}*${height} (px).`)
	}
}

function toggleElementsByIdPattern(pattern, checkbox) {
	var enable = $("#"+checkbox).is(':checked');
    var selector = "[id^='" + pattern + "']";
    $(selector).prop('disabled', !enable);
    $(selector).prop('readonly', !enable);
}

function setBackgroundMode(isColor){
	if(isColor){
		$("#templateBackgroundColor").show();
		$("#templateBackgroundImage").hide();
	}else{
		$("#templateBackgroundColor").hide();
		$("#templateBackgroundImage").show();
	}
}

function selectUpload(callback, image){
	
	$('#etwigModalTitle').text('Select/Upload');
	$('#etwigModalBody').html(`
		<div class="embed-responsive embed-responsive-1by1">
			<iframe class="embed-responsive-item" src="/assets/_selector?callback=${callback}&image=${image}" allowfullscreen></iframe>
        </div>`
    );
	
	$('#etwigModal').modal('show');
}

function getCurrentDesign(){
	
	// Background enabled (checkbox)
	var backgroundEnabled = $('#backgroundEnabled').is(':checked');
	
	// Background mode (radio button)
	var backgroundMode = $('input[type=radio][name=backgroundMode]:checked').val();
	var backgroundValue = undefined;
	
	var imageInfo = undefined;
	
	// Background color (color picker via text input)
	if(backgroundMode == "color"){
		var backgroundValue = $('#templateBackgroundColorInput').val();
		
		// Null check.
		if(backgroundValue.length == 0){
			warningToast("Background color is required.");
			return;
		}
	}
	
	// Background image (number input)
	else{
		var backgroundValue = $('#templateBackgroundImageInput').val();
		
		// Integer check.
		if (!(backgroundValue % 1 === 0)){
			warningToast("AssetId for background image is not an integer.");
			return;
		}
		
		imageInfo = getImageInfo(backgroundValue);
		if(imageInfo == null){
			return;
		}
	}
	
	// Store background info
	templateBackgroundObj.set(backgroundEnabled, backgroundMode, backgroundValue, imageInfo);	
	
	//if(backgroundMode == "image"){
	//	backgroundImg = loadImage('/assets/getPublicAsset?assetId=' + backgroundValue);
	//}
	
	getCommonDesign("logo", templateLogoObj)
	getCommonDesign("title", templateTitleObj)
	
	console.log(templateTitleObj);
}

function getCommonDesign(element, templateElementObj){
	
	var elementNameLower = element.toLowerCase();
	var elementNameCapFirst = element[0].toUpperCase() + element.slice(1);
	
	// Enabled (checkbox)
	var enabled = $('#' + elementNameLower + 'Enabled').is(':checked');
	
	// Image (number input) and integer check.
	var image = $('#template' + elementNameCapFirst + 'ImageInput').val();
	if (!(image % 1 === 0)){
		warningToast("AssetId for "+ elementNameLower +" image is not an integer.");
		return;
	}
	
	// Validate image and get the width/height
	var imageInfo = getImageInfo(image);
	if(imageInfo == null){
		return;
	}
	
	// Size (number input) and null/integer check.
	var size = $('#template' + elementNameCapFirst + 'Size').val();
	if(size.length == 0){
		warningToast(elementNameCapFirst + " size is empty.");
		return;
	}
	
	if (!(size % 1 === 0)){
		warningToast(elementNameCapFirst + " size is not an integer.");
		return;
	}
	
	// Position (number input) and null check
	var position = $('#template' + elementNameCapFirst + 'Position').val();
	if(position.length == 0){
		warningToast(elementNameCapFirst + " position is empty.");
		return;
	}

	// Regex check whether the position is well-formed. (NUM,NUM)	
	const regex = /\((\d+),(\d+)\)/;
	var match = position.match(regex);
	if(match == undefined || match == null){
		warningToast(elementNameCapFirst + " position is not well-formed.", "It must be the format of (TWO_DIGIT_NUMBER,TWO_DIGIT_NUMBER)");
		return;
	}

	// Store info
	templateElementObj.set(enabled, image, imageInfo,size, position);	
}

function getImageInfo(assetId){
	var imageInfo = undefined;
	
	$.ajax({
   		url: "/api/private/getImageInfo", 
   		type: "GET",
   		async: false,
   		dataType: "json",
   		data: {
			assetId: assetId
		},
   		success: function (result) {
			if(result.error > 0){
				warningToast("Failed to validate image", result.msg);
			}else{
				imageInfo = result.imageInfo;
			}	
    	},
    	error: function (err) {
    		dangerToast("Failed to validate image due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    	}
 	});
 	
 	return imageInfo;
}

function sizeReMap(rectWidth, rectHeight, canvasShortSide, percentage){

	// Calculate scale factor
	var rectShortSide = min(rectWidth, rectHeight);
  	var desiredSize = canvasShortSide * percentage * 0.01;
  	var scaleFactor = desiredSize / rectShortSide;

	// Re-calculate the new size
	return [rectWidth * scaleFactor, rectHeight * scaleFactor]
}

function prepareBackground(){
	if(templateBackgroundObj.getMode() == "image"){
		backgroundImg = loadImage('/assets/getPublicAsset?assetId=' + templateBackgroundObj.getValue());
	}
	else{
		backgroundImg = templateBackgroundObj.getValue();
	}
	
	logoImg = loadImage('/assets/getPublicAsset?assetId=' + templateLogoObj.getImage());
}

function preload(){
	
	// Prepare the background on load
	prepareBackground();
	$('#previewTpl').click(function() {
		prepareBackground();
  	});
}

//backgroundImg = "#FF0000";

function setup() {
	createCanvas(CANVAS_WIDTH, CANVAS_HEIGHT);
	colorMode(HSB, 360, 100, 100); 
}

function draw() {
	
	// Background is enabled.
	if(templateBackgroundObj.getEnabled()){
		background(backgroundImg);
	}
	
	// Background is disabled, set it to black.
	else{
		background(0,  0,  0);
	}
	
	// Logo size
	var logoWidth = templateLogoObj.getImageInfo().width;
	var logoHeight = templateLogoObj.getImageInfo().height;
	var logoImgSize = sizeReMap(logoWidth, logoHeight, CANVAS_SHORT_SIDE, templateLogoObj.getSize());

	// Logo position
	var logoCircleCenterPosX = templateLogoObj.getPosX() * CANVAS_WIDTH * 0.01;
	var logoCircleCenterPosY = templateLogoObj.getPosY() * CANVAS_HEIGHT * 0.01;
	var logoCircleDiameter = templateLogoObj.getSize() * CANVAS_SHORT_SIDE * 0.01;
	
	// Draw the logo
	image(
		logoImg, 
		logoCircleCenterPosX - logoImgSize[0] * 0.5, 		// posX
		logoCircleCenterPosY - logoImgSize[1] * 0.5, 		// posY
		logoImgSize[0], 														// height
		logoImgSize[1]														// width
	);
	
	// Use a red circle to represent the position.
	strokeWeight(6);	stroke(0, 100, 100);	noFill();
	circle(logoCircleCenterPosX, logoCircleCenterPosY, logoCircleDiameter);
	
	// Draw a horizontal bar if the logo is disabled.
	if(!templateLogoObj.getEnabled()){
		line(logoCircleCenterPosX - 0.5 * logoCircleDiameter, logoCircleCenterPosY, logoCircleCenterPosX +0.5 * logoCircleDiameter, logoCircleCenterPosY);
	}
	
	
	var titleWidth = templateTitleObj.getImageInfo().width;
	var titleHeight = templateTitleObj.getImageInfo().height;
	var titleImgSize = sizeReMap(titleWidth, titleHeight, CANVAS_SHORT_SIDE, templateTitleObj.getSize());
	
	// Title position
	var titleRectPosX = templateTitleObj.getPosX() * CANVAS_WIDTH * 0.01 - titleWidth * 0.5;
	var titleRectPosY = templateTitleObj.getPosY() * CANVAS_HEIGHT * 0.01 - titleHeight * 0.5;
	
	// Template title (orange)
	strokeWeight(6);	stroke(30, 100, 100);	noFill();
	rect(titleRectPosX, titleRectPosY, titleImgSize[0], titleImgSize[1]);
	
	
	//rect(0, 0, WIDTH, HEIGHT);
	
	// Text indicates background (red)
	//noStroke();	textSize(20);	textStyle(BOLD);	fill(0, 100, 100);
	//var backgroundText = `Background: enabled=${templateBackgroundObj.getEnabled()}, mode=${templateBackgroundObj.getMode()}, value=${templateBackgroundObj.getValue()}`;
  	//text(backgroundText, 10, 30);

}
