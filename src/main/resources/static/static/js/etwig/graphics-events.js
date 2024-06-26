function eventGraphicsDataTable(){
	var dt = $('#eventGraphics').DataTable({
		"processing": true,
		"serverSide": true,
		"lengthMenu": [[10, 20, 50, 100], [10, 20, 50, 100]],
		"pageLength": 20,
		"searching": false, 
		"order": [[0, "desc"]],
		"ajax": {
			"url": "/api/eventGraphics/summary",
			"type": "GET",
			"data": function(d) {
				return $.extend({}, d, {
					"sortColumn": d.columns[d.order[0].column].data,
					"sortDirection": d.order[0].dir
				});
			}
		},
        columns: [
            { data: "id", orderable: true },
            { data: "eventName", orderable: false },
            { data: "startTime", render: dateWeekWithBadgeRender, orderable: true},
			{ data: "twigComponentNum", render: twigComponentCountRender, orderable: false},
			{ data: "bannerNum", render: bannerCountRender, orderable: false},
			{ data: "pendingApprovalNum", render: pendingApprovalCountRender, orderable: false},
            { data: "lastModified", render: dateWeekRender, orderable: false},
            { mRender: actionRender, orderable: false}
        ]
    });
    return dt;
}

function dateWeekWithBadgeRender(data, type, row){

	// Get dates
	var targetDate = Date.parse(data);
	var dateWeek = targetDate.toString('yyyy-MM-dd HH:mm') + '&nbsp;';

    var today = new Date();
    var dayOfWeek = today.getDay(); 						// Get current day of the week (0 for Sunday, 6 for Saturday)
    var dateWeek = targetDate.toDateString() + '&nbsp;'; 	// Assuming targetDate is a Date object

    // Find Monday of this week
    var monday;

	// If today is Sunday, go back six days to last Monday
    if (dayOfWeek === 0) {
        monday = new Date(today.setDate(today.getDate() - 6));	
    } 
	
	// Otherwise, subtract the current day of week number minus 1 to get to the last Monday
	else {
        monday = new Date(today.setDate(today.getDate() - dayOfWeek + 1));
    }

    // Calculate week differences
    var daysDifference = (targetDate - monday) / (1000 * 60 * 60 * 24);
    var weeksDifference = Math.floor(daysDifference / 7);

    // Weeks left
    if (monday < targetDate) {

        // Current Week
        if (weeksDifference === 0) {
            return dateWeek + `<span class="badge badge-danger">In this week</span>`;
        }

        // Next week
        if (weeksDifference === 1) {
            return dateWeek + `<span class="badge badge-warning">In next week</span>`;
        }

        // More than 1 week left
        return dateWeek + `<span class="badge badge-primary">${weeksDifference + 1} weeks left</span>`;
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
			<a href="/events/edit.do?eventId=${full.id}" class="btn btn-outline-secondary btn-sm" target="_blank">
				<i class="fa-solid fa-lightbulb"></i>&nbsp;Event Info
			</a>
			<a href="/graphics/eventGraphics.do?eventId=${full.id}" class="btn btn-outline-primary btn-sm">
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