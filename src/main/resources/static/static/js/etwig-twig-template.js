const WIDTH = 1280;
const HEIGHT = 720;

var templateBackgroundObj = new TemplateBackground();

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
                console.log(d.page)
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
	console.log(full)
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
	
	if(width < WIDTH || height < HEIGHT){
		alert(`For the best user experience, it is recommended to view this page on the window of size greater ${WIDTH}*${HEIGHT} (px).\nYour window size is ${width}*${height} (px).`)
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
			warningToast("AssetId is not an integer.");
			return;
		}
	}
	
	// Store background info
	templateBackgroundObj.set(backgroundEnabled, backgroundMode, backgroundValue);
	console.log(templateBackgroundObj);
	
}

function setup() {
	createCanvas(WIDTH, HEIGHT);
	colorMode(HSB, 360, 100, 100); 
}

function draw() {
	background(255);
	
	// The canvas is surronding by a rectangle with the theme color (#004AAD).
	strokeWeight(12);	stroke(214, 100, 68);	noFill();
	rect(0, 0, WIDTH, HEIGHT);
	
	// Text indicates background (red)
	noStroke();	textSize(20);	textStyle(BOLD);	fill(0, 100, 100);
	var backgroundText = `Background: enabled=${templateBackgroundObj.getEnabled()}, mode=${templateBackgroundObj.getMode()}, value=${templateBackgroundObj.getValue()}`;
  	text(backgroundText, 10, 30);
  	
}
