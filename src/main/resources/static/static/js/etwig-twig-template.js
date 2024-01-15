const CANVAS_WIDTH = 1280;
const CANVAS_HEIGHT = 720;

const RECOMMENDED_WIDTH = 1600;
const RECOMMENDED_HEIGHT = 900;

var templateBackgroundObj = new TemplateBackground();
var templateLogoObj = new TemplateImage(5, 20, 0, 99);

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
	}
	
	// Store background info
	templateBackgroundObj.set(backgroundEnabled, backgroundMode, backgroundValue);
	
	// Logo enabled (checkbox)
	var logoEnabled = $('#logoEnabled').is(':checked');
	
	// Logo image (number input) and integer check.
	var logoImage = $('#templateLogoImageInput').val();
	if (!(logoImage % 1 === 0)){
		warningToast("AssetId for logo image is not an integer.");
		return;
	}
	
	var logoImageWidth = $("#templateLogoImageContent").width();
	var logoImageHeight = $("#templateLogoImageContent").height();
	console.log(logoImageWidth, logoImageHeight);
	
	// Logo size (number input) and null/integer check.
	var logoSize = $('#templateLogoSize').val();
	if(logoSize.length == 0){
		warningToast("Logo size is empty.");
		return;
	}
	
	if (!(logoSize % 1 === 0)){
		warningToast("Logo size is not an integer.");
		return;
	}
	
	// Logo position (number input) and null check
	var logoPosition = $('#templateLogoPosition').val();
	if(logoPosition.length == 0){
		warningToast("Logo position is empty.");
		return;
	}

	// Regex check whether the position is well-formed. (NUM,NUM)	
	const regex = /\((\d+),(\d+)\)/;
	var match = logoPosition.match(regex);
	if(match == undefined || match == null){
		warningToast("Logo position is not well-formed.", "It must be the format of (TWO_DIGIT_NUMBER,TWO_DIGIT_NUMBER)");
		return;
	}

	// Store logo info
	templateLogoObj.set(logoEnabled, logoImage, logoSize, logoPosition);
	//console.log(templateLogoObj);
	
}

function setup() {
	createCanvas(CANVAS_WIDTH, CANVAS_HEIGHT);
	colorMode(HSB, 360, 100, 100); 
}

function draw() {
	
	// The background color is #DFDFDF.
	background(0, 0, 94);
	
	
	strokeWeight(6);	stroke(0, 100, 100);	noFill();
	circle(
		templateLogoObj.getPosX() * CANVAS_WIDTH * 0.01,
		templateLogoObj.getPosY() * CANVAS_HEIGHT * 0.01,
		templateLogoObj.getSize() * CANVAS_HEIGHT * 0.01
	);
	
	//rect(0, 0, WIDTH, HEIGHT);
	
	// Text indicates background (red)
	//noStroke();	textSize(20);	textStyle(BOLD);	fill(0, 100, 100);
	//var backgroundText = `Background: enabled=${templateBackgroundObj.getEnabled()}, mode=${templateBackgroundObj.getMode()}, value=${templateBackgroundObj.getValue()}`;
  	//text(backgroundText, 10, 30);

}
