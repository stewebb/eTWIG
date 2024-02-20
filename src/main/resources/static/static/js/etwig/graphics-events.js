function eventGraphicsDataTable(){
	var dt = $('#eventGraphics').DataTable({
        processing: true,
        serverSide: true,
        searching: false, 
        bAutoWidth: false,
        ajax: {
            url: "/api/private/eventGraphicsList",
            data: function (d) {
                d.page = d.start / d.length;
                d.size = d.length;
            },
            type: "GET",
            dataSrc: function (json) {
                return json.content;
            }
        },
        columns: [
            { data: "id" },
            { data: "eventName" },
            { data: "startTime", render: dateWeekRender},
            { data: null, render: statusRender},
            { data: "lastModified", render: dateRender},
            { mRender: actionRender}
        ]
    });
    return dt;
}

function statusRender(data, type, row){

	// Both TWIG component and banner have been made.
	if(row.twigComponentNum > 0 && row.bannerNum > 0) {
	  return '<span class="badge badge-success">All done</span> ';
	}

	// Only TWIG component has been made
	else if(row.twigComponentNum > 0 && row.bannerNum == 0) {
		return '<span class="badge badge-primary">No banner</span> ';
	}

	// Only banner has been made
	else if(row.twigComponentNum == 0 && row.bannerNum > 0) {
		return '<span class="badge badge-danger">No TWIG component</span> ';
	}

	// Nothing has been made.
	else {
		return '<span class="badge badge-danger">No graphics</span>';
	}
  
}


function dateWeekRender(data, type, row){

	// Get dates
	var targetDate = Date.parse(data);
	var dateWeek = targetDate.toString('yyyy-MM-dd HH:mm') + '&nbsp;';
	//var today = Date.today();


	// Find Monday of this week. If today is Sunday (0), Datejs considers it the start of a new week,
	// so we need to adjust for that by going back to the previous week's Monday if today is Sunday.
	//var monday = (today.getDay() === 0) ? today.last().monday() : today.monday()
	var monday = Date.monday();

	// Calculate week differences
	var daysDifference = Math.abs(monday - targetDate) / (1000 * 60 * 60 * 24);
	var weeksDifference = Math.ceil(daysDifference / 7);

	// Weeks left
	if (monday < targetDate) {

		// Current Week
		if(weeksDifference <= 1){
			return dateWeek + `<span class="badge badge-danger">In this week</span>`
		}	

		// Next week
		if(weeksDifference == 2){
			return dateWeek + `<span class="badge badge-warning">In next week</span>`
		}

		// More than 1 week left
		else{
			return dateWeek + `<span class="badge badge-primary">${weeksDifference+1} weeks left</span>`
		}
	} 
	
	// Weeks passed
	else {
		dateWeek += (`<span class="badge badge-secondary">Past event</span>`);
	}

	return dateWeek;
}

function actionRender(data, type, full){
	return `
		<div class="btn-group">
			<a href="/events/edit?eventId=${full.id}" class="btn btn-outline-secondary btn-sm" target="_blank">
				<i class="fa-solid fa-lightbulb"></i>&nbsp;Event Info
			</a>
			<a href="/graphics/events/view?eventId=${full.id}" class="btn btn-outline-primary btn-sm">
				<i class="fa-solid fa-image"></i>&nbsp;Graphics
			</a>
		</div>
	`;
}

function addGraphics(){
	var newGraphicsObj = {}
	
	// eventId
	newGraphicsObj["eventId"] = parseInt($('#eventId').val());
	
	// Role
	newGraphicsObj["operatorRole"] = parseInt($('#operatorRole').find(":selected").val());
	
	// Graphics type option, 1 -> Approved, 0 -> Declined, NaN -> Not Selected
	var graphicsMode = parseInt($('input[type=radio][name=graphicsType]:checked').val());
	if(isNaN(graphicsMode)){
		warningPopup("Selecting an option is required");
		return;
	}
	newGraphicsObj["isBanner"] = (graphicsMode == 0);
		
	// Assets
	var assetId = parseInt($('#uploadCallback').val());
	if(isNaN(assetId)){
		warningPopup("Selecting an asset is required.");
		return;
	}
	newGraphicsObj["asset"] = assetId;

    console.log(newGraphicsObj);
    //return;
		
	var hasError = true;
	$.ajax({
   		url: '/api/private/addGraphicsForEvent', 
   		type: "POST",
   		async: false,
   		dataType: "json",
   		contentType: "application/json; charset=utf-8",
   		data: JSON.stringify(newGraphicsObj),
   		success: function (result) {
			if(result.error > 0){
				dangerPopup("Failed to add graphics.", result.msg);
				hasError = true;
			}else{
				successPopup("Graphics added successfully.");
				hasError = false;
			}	
    	},
    	error: function (err) {
    		dangerPopup("Failed to add graphics due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    		hasError = true;
    	}
 	});

	// Redirect back
	if(!hasError){
		setTimeout(function() {	location.reload(); }, 2500);
	}
	
}