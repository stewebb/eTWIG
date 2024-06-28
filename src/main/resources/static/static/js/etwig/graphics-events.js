/**
 * Initializes and configures a DataTable for managing event graphics. The table is set up to perform server-side processing, 
 * including pagination, sorting, and filtering controlled by the server. Specific column rendering functions are applied to 
 * format dates and counts appropriately within the table.
 *
 * Configuration:
 * - Processing: Enables the visual feedback that the data is being processed.
 * - ServerSide: Enables server-side processing where sorting, pagination, and searching are handled by the server.
 * - LengthMenu and PageLength: Configures the number of entries shown per page and the options available for the user to select.
 * - Searching: Disabled to offload filtering logic to the server or other UI components.
 * - Order: Sets the default column order.
 * - Ajax: Configures the AJAX request for fetching data, including URL, type, and additional data handling to append sorting parameters.
 * - Columns: Specifies column data sources, orderability, and custom render functions for specific columns to enhance the presentation of data.
 *
 * Each column has specific settings:
 * - Orderable determines if the column can be sorted.
 * - Render functions for columns like `startTime`, `twigComponentNum`, `bannerNum`, `pendingApprovalNum`, and `lastModified` provide customized display formats.
 *
 * Returns:
 * - {object} The DataTable instance created and configured.
 */

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

/**
 * Submits a POST request to the server to add a new graphics item based on the user inputs collected from the form. 
 * The function constructs an object with the necessary parameters, validates the inputs, and makes a synchronous AJAX 
 * request. If any required input is missing, it triggers a warning popup and aborts the submission.
 *
 * The parameters for the new graphics item include:
 * - `eventId`: The event identifier, parsed from the input field with ID `eventId`.
 * - `operatorRole`: The role of the operator, derived from the selected dropdown option in `operatorRole`.
 * - `isBanner`: A boolean determined by a radio button input where 0 indicates a banner; other values or no selection results in a warning.
 * - `asset`: The asset identifier, required for creating the graphics item; absence of a valid asset ID triggers a warning.
 *
 * Error Handling:
 * - If the AJAX request encounters a server-side error or the response includes an error flag, it displays an appropriate error popup with details.
 * - On successful addition, displays a success popup and refreshes the page after a brief delay.
 *
 * @returns {void} Does not return a value but initiates UI interactions based on AJAX response.
 */

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

	//var hasError = true;
	$.ajax({
   		url: '/api/eventGraphics/add', 
   		type: "POST",
   		async: false,
   		dataType: "json",
   		contentType: "application/json; charset=utf-8",
   		data: JSON.stringify(newGraphicsObj),
   		success: function (result) {
			if(result.error > 0){
				dangerPopup("Failed to add graphics.", result.msg);
				//hasError = true;
			}else{
				successPopup("Graphics added successfully.");
				setTimeout(function() {	location.reload(); }, 2500);
			}	
    	},
    	error: function (err) {
    		dangerPopup("Failed to add graphics due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    		//hasError = true;
    	}
 	});
}

/**
 * Sends a GET request to the server to remove a specific graphics item identified by the provided `graphicsId`. Upon 
 * successful removal, it displays a success popup and refreshes the current page after a brief delay. If the removal 
 * fails due to a server error, it displays an error popup detailing the issue.
 *
 * @param {number} graphicsId - The unique identifier for the graphics item to be removed.
 *
 * Errors:
 * - If the AJAX request fails (e.g., due to network issues or server errors), it displays a danger popup with an error 
 * message that includes the HTTP status and a specific error description returned by the server.
 *
 * Side Effects:
 * - Triggers UI updates through popups and refreshes the page upon successful operation or error handling.
 */

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