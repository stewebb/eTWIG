// TODO: Pending Removal

/*
function createDatePicker(){
	var datepicker = new tui.DatePicker("#eventGraphicsDateWrapper", {
		date: Date.today(),
		type: "date",
		input: {
			element: "#eventGraphicsDate",
			format: "yyyy-MM-dd",
			usageStatistics: false
		},
	});
}

function requestEvent(embedded){
	
	// Event id: Required but provided.
	var eventId = $('#eventId').val();
	
	// Requester: Required but provided.
	var requesterRole = $('#requesterRole').val();
	
	// Returning Date: Required.
	var eventGraphicsDate = $('#eventGraphicsDate').val();
	if(eventGraphicsDate.length == 0){
		warningPopup("Graphics returning date is required");
		return;
	}
	
	// Request comment: Optional.
	var requestComment = $('#requestComment').val();
	
	// Create an object for the new request.
	var newRequestObj = {
		"eventId" : eventId,
		"requesterRole": requesterRole,
		"returningDate": eventGraphicsDate,
		"requestComment": requestComment
	};
	
	//console.log(newRequestObj);
	
	var hasError = true;
	$.ajax({
   		url: "/api/private/requestGraphic", 
   		type: "POST",
   		async: false,
   		dataType: "json",
   		contentType: "application/json; charset=utf-8",
   		data: JSON.stringify(newRequestObj),
   		success: function (result) {
			if(result.error > 0){
				dangerPopup("Failed to request graphics.", result.msg);
				hasError = true;
			}else{
				successPopup("Graphics requested successfully.");
				hasError = false;
			}	
    	},
    	error: function (err) {
    		dangerPopup("Failed to request graphics due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    		hasError = true;
    	}
 	});
	
	// Refresh page only when graphics were requested successfully.
	if(!hasError){
		setTimeout(function() { window.location.reload(); }, 2500);
	}
}
*/