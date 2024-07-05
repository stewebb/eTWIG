/**
 * Handles the import of events from a selected file via an AJAX POST request to a server endpoint.
 * The function first checks if a file is selected. If not, it displays a warning popup.
 * If a file is selected, it sends the file as FormData in the POST request.
 * Upon successful import, it dynamically updates a table with the import results for each row,
 * indicating success or failure. It also disables the upload button after processing.
 * In case of an error during the AJAX call, it displays an error popup with the status code and exception details.
 *
 * Behavior:
 * - Validates that a file is selected and aborts with a warning if no file is found.
 * - Sends the selected file to the backend via a 'POST' request.
 * - On success, iterates over results, appending each result as a table row with styling based on the result's success or failure.
 * - Disables the file upload button after the response is processed.
 * - Handles errors by displaying a detailed error popup.
 *
 * Example HTML:
 * <input type="file" id="fileUpload">
 * <button id="uploadFileBtn">Upload File</button>
 * <table id="importResult"></table>
 *
 * Dependencies:
 * - jQuery for DOM manipulation and AJAX.
 * - A server-side endpoint configured to handle '/api/event/import' with file processing logic.
 */

function importEvents(){
	var data = new FormData();
  	var file = $('#fileUpload')[0].files[0];
    
  	// Null check
  	if(file == undefined || file == null){
		warningPopup("Please select a file.");
		return;
	}

	// Add file and role
  	data.append('file', file);

  	$.ajax({
		type: 'POST',
    	url: '/api/event/import',
    	data: data,
    	contentType: false,
    	processData: false,
    	success: function(result) {

			jQuery.each(result, function(rowNum, result) {
				var textColor = (result == null) ? 'success' : 'danger';
				var result = (result == null) ? 'Import successfully' : ('Import failed: ' + result);

				$('#importResult').append(`
					<tr><td>${rowNum}</td><td class="text-${textColor}">${result}</td></tr>
				`);
			});

			$('#uploadFileBtn').attr('disabled', true);
		},
    	error: function (err) {
			dangerPopup("Failed to import events due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    	}
  	});
}