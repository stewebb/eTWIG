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
            { mRender: summaryActionRender, orderable: false}
        ]
    });
    return dt;
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

    //console.log(newGraphicsObj);
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

function removeGraphics(graphicsId) {
	$.ajax({
   		url: '/api/eventGraphics/remove', 
   		type: "GET",
   		data: {
			graphicsId: graphicsId
		},
   		success: function () {
			successPopup("The graphics is removed successfully.");
			setTimeout(function() {	window.location.reload(); }, 2500);
    	},
    	error: function (err) {
    		dangerPopup("Failed to remove the graphics due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    	}
 	});
}