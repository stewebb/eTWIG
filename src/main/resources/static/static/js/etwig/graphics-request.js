function createDatePicker(){
	var datepicker = new tui.DatePicker("#returningDateWrapper", {
		date: Date.today(),
		type: "date",
		input: {
			element: "#returningDate",
			format: "yyyy-MM-dd",
			usageStatistics: false
		},
	});
}

function requestEvent(embedded){
	
	// Event id: Required but provided.
	var eventId = $('#eventId').val();
	
	// Requester: Required but provided.
	var requester = $('#requester').val();
	
	// Returning Date: Required.
	var returningDate = $('#returningDate').val();
	if(returningDate.length == 0){
		warningToast("Graphics returning date is required");
		return;
	}
	
	// Request comment: Optional.
	var requestComment = $('#requestComment').val();
	
	// Create an object for the new request.
	var newRequestObj = {
		"eventId" : eventId,
		"requester": requester,
		"returningDate": returningDate,
		"requestComment": requestComment
	};
	
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
				dangerToast("Failed to request graphics.", result.msg);
				hasError = true;
			}else{
				successToast("Graphics requested  successfully.");
				hasError = false;
			}	
    	},
    	error: function (err) {
    		dangerToast("Failed to request graphics due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    		hasError = true;
    	}
 	});
	
	setTimeout(
		function() {
			embedded ? parent.location.reload() : window.location.reload();
		}, 
		hasError ? 10000 : 2000
	);
	
}