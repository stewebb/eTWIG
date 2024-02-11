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
            { data: "graphicsNum"},
            { data: "bannerNum"},
            { data: "lastModified", render: dateRender},
            { mRender: actionRender}
        ]
    });
    return dt;
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
				successPopup("Decision made successfully.");
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