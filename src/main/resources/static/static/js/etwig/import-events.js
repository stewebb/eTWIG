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
        url: '/api',
        data: data,
        contentType: false,
        processData: false,
        success: function(result) {
        	if(result.error > 0){
				dangerPopup("Failed to upload file.", result.msg);
			}else{
				successPopup("File upload successfully.");
				resetFile();
				//$('#assetSelector').DataTable().ajax.reload();
			}	
        },
        error: function (err) {
			dangerPopup("Failed to upload file due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    		hasError = true;
    	}
    });
}

$(".custom-file-input").on("change", function() {
	var fileName = $(this).val().split("\\").pop();
	$(this).siblings(".custom-file-label").addClass("selected").html(fileName);
	$("#uploadFileBtn").prop('disabled', false);
});

function resetFile(){
	$('#fileUpload').val('');
    $('.custom-file-input').siblings(".custom-file-label").removeClass("selected").html("Choose file");
    $("#uploadFileBtn").prop('disabled', true);
}