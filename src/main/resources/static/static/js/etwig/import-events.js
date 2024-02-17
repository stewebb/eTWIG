function importEvents(){
	var data = new FormData();
    var file = $('#fileUpload')[0].files[0];
    
    // Null check
    if(file == undefined || file == null){
		warningPopup("Please select a file.");
		return;
	}
    
    data.append('file', file);
    
    $.ajax({
		type: 'POST',
        url: '/api/private/importEvents',
        data: data,
        contentType: false,
        processData: false,
        success: function(result) {
        	(result.error > 0) ? dangerPopup("Failed to import events", result.msg) : infoPopup("Event import results", result.msg);
        },
        error: function (err) {
			dangerPopup("Failed to import events due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    	}
    });
}