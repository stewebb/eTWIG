/**
 * Initializes and configures the DataTable for managing banner requests.
 * This function sets up a DataTable on the '#requestsTable' element with specific configurations for server-side processing.
 * It includes settings for pagination, ordering, and custom column rendering to handle date formatting, status rendering,
 * and action buttons. The function configures the DataTable to fetch data from a specified server endpoint with parameters
 * adjusted for sorting. It is designed for admin panels or dashboard interfaces where managing banner requests efficiently is crucial.
 *
 * @returns {void} Does not return a value.
 */

function bannerRequestListTable(){
	$('#requestsTable').DataTable({
		"processing": true,
		"serverSide": true,
		"lengthMenu": [[10, 20, 50, 100], [10, 20, 50, 100]],
		"pageLength": 20,
		"searching": false, 
		"order": [[5, "desc"]],
		"ajax": {
			"url": "/api/bannerRequest/list?isApproved=na",
			"type": "GET",
			"data": function(d) {
				return $.extend({}, d, {
					"sortColumn": d.columns[d.order[0].column].data,
					"sortDirection": d.order[0].dir
				});
			}
		},
		"columns": [
			{ "data": "id", "orderable": true},
			{ "data": "eventName", "orderable": true},
			{ "data": "requestTime", "orderable": true, "render": dateWeekRender},
			{ "data": "requesterName", "orderable": false},
			{ "data": "expectDate", "orderable": true, render: expectDateRender},
			{ "data": "approved", "orderable": true, "render": approvalStatusRender},
			{ "data": "approverName", "orderable": false, "render": optionalFieldsRender},
			{ "data": "responseTime", "orderable": true, "render": dateWeekRender},
			{ "mRender": requestActionRender, "orderable": false}
		]
	});
}

function setAssetsUpload(approvedMode){
	$('#graphicsApprovalAssets').toggle(approvedMode == 1);
}

function decide(){
	var approvalDecisionObj = {}
	
	// requestId
	approvalDecisionObj["id"]  = parseInt($('#requestId').text());
	
	// Role
	approvalDecisionObj["role"]  = parseInt($('#approverRole').find(":selected").val());
	
	// Approval option, 1 -> Approved, 0 -> Declined, NaN -> Not Selected
	var graphicsApprovalOption = parseInt($('input[type=radio][name=graphicsApprovalOption]:checked').val());
	if(isNaN(graphicsApprovalOption)){
		warningPopup("Make a decision is required");
		return;
	}
	approvalDecisionObj["approved"] = graphicsApprovalOption > 0;
	
	// Comments
	approvalDecisionObj["comments"] = $('#graphicsApprovalComments').val();
	
	// Assets
	if(approvalDecisionObj["approved"]){
		var assetId = parseInt($('#uploadCallback').val());
		if(isNaN(assetId)){
			warningPopup("Selecting an asset is the requisite for approving a graphic request.");
			return;
		}
		approvalDecisionObj["asset"] = assetId;
	}
		
	var hasError = true;
	$.ajax({
   		url: '/api/bannerRequest/approve', 
   		type: "POST",
   		async: false,
   		dataType: "json",
   		contentType: "application/json; charset=utf-8",
   		data: JSON.stringify(approvalDecisionObj),
   		success: function (result) {
			successPopup(result.message);
			hasError = false;
    	},
    	error: function (err) {
    		dangerPopup("Failed to submit a decision due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    		hasError = true;
    	}
 	});

	// Redirect back
	if(!hasError){
		setTimeout(function() {	$(location).attr('href','/graphics/approvalList.do'); }, 2500);
	}
	
}

/**
 * Formats the given start and end dates into a human-readable string.
 * If the start and end dates are the same, it formats the time range on that day.
 * If they are different, it includes both date and time for start and end.
 *
 * @param {string} startDate - The ISO 8601 string representation of the start date.
 * @param {string} endDate - The ISO 8601 string representation of the end date.
 * @returns {string} A formatted string representing the date and time range of the event.
 *                   For events on the same day, it returns "Day, Date Month startTime - endTime".
 *                   For multi-day events, it returns "Day, Date Month startTime - Day, Date Month endTime".
 *
 * @example
 * // For a single day event
 * formatEventDates('2024-04-22T12:00:00', '2024-04-22T19:00:00');
 * // returns 'Mon, 22 Apr 12 PM - 7 PM'
 *
 * @example
 * // For a multi-day event
 * formatEventDates('2024-04-22T08:00:00', '2024-04-23T20:00:00');
 * // returns 'Mon, 22 Apr 8 AM - Tue, 23 Apr 8 PM'
 */

function formatEventDates(startDate, endDate) {

    // Create Date objects from the start and end date strings
    const start = new Date(startDate);
    const end = new Date(endDate);

    // Define options for date formatting
    const dateOptions = { weekday: 'short', day: 'numeric', month: 'short' };
    const timeOptions = { hour: 'numeric', minute: '2-digit', hour12: true };

    // Function to format the time based on minute value
    function formatTime(date) {
        let formattedTime = date.toLocaleTimeString('en-US', timeOptions);
        // Remove ':00' for times on the hour
        return formattedTime.replace(':00', '');
    }

    // Format the start and end dates
    const startDateFormat = start.toLocaleDateString('en-US', dateOptions);
    const endDateFormat = end.toLocaleDateString('en-US', dateOptions);
    const startTimeFormat = formatTime(start);
    const endTimeFormat = formatTime(end);

    // Check if the start and end dates are on the same day
    if (startDateFormat === endDateFormat) {
        return `${startDateFormat} ${startTimeFormat} - ${endTimeFormat}`;
    } else {
        return `${startDateFormat} ${startTimeFormat} - ${endDateFormat} ${endTimeFormat}`;
    }
}