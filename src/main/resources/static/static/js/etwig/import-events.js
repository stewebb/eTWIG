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
	data.append('role', parseInt($('#uploaderRole').find(":selected").val()));

  	$.ajax({
		type: 'POST',
    	url: '/api/private/importEvents',
    	data: data,
    	contentType: false,
    	processData: false,
    	success: function(result) {
			if (result.error > 0) {
				dangerPopup("Failed to import events", result.msg);
			}

			// Normal conditions
			else{ 
				jQuery.each(result.result, function(rowNum, result) {
					//console.log(rowNum, result);
					var textColor = (result == null) ? 'success' : 'danger';
					var result = (result == null) ? 'Import successfully' : ('Import failed: ' + result);

					$('#importResult').append(`
						<tr><td>${rowNum}</td><td class="text-${textColor}">${result}</td></tr>
					`);
				});
			}

			$('#uploadFileBtn').attr('disabled', true);
		},
    	error: function (err) {
			dangerPopup("Failed to import events due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    	}
  	});
}