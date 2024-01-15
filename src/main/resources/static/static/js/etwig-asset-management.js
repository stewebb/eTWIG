function assetSelectorDataTable(){
	var dt = $('#assetSelector').DataTable({
        processing: true,
        serverSide: true,
        searching: true, 
        ajax: {
            url: "/api/private/getAssetList",
            data: function (d) {
                d.page = d.start / d.length;
                d.size = d.length;
                //console.log(d.page)
            },
            type: "GET",
            dataSrc: function (json) {
                return json.content;
            }
        },
        columns: [
            { data: "id" },
            { data: "name" },
            { data: "mediaType", visible: false},
            { data: "fileCategory", visible: false},
            { data: "size", visible: false},
            { data: "uploader"},
            { data: "lastModified", render: lastModifiedRender},
        ]
    });
	
	dt.on('click', 'tbody tr', function(e) {
    var classList = e.currentTarget.classList;

	// De-select a row
    if (classList.contains('selected')) {
        classList.remove('selected');
        previewAsset(null);
    }
    
    // Select a row
     else {
		
        // Deselect any currently selected row
        dt.rows('.selected').nodes().each((row) => row.classList.remove('selected'));
        classList.add('selected');

        var rowData = dt.row(this).data();
        //console.log(rowData);
        previewAsset(rowData);
    }
});

    return dt;
}

function lastModifiedRender(data, type, row){
	
	//console.log(data.split(".")[0]);
	// Only precise to seconds.
	return timeAgo(data); 
}

function previewAsset(asset){
	
	// Not selected
	if(asset == undefined || asset == null){
		$("#previewContent").html(`
			<div class="d-flex justify-content-center mb-2">
				<i class="fa-regular fa-arrow-pointer big-icons"></i>
			</div>
									
			<div class="d-flex justify-content-center bold-text text-secondary">
				Select an asset to preview by clicking a row in the above table.
			</div>
		`);
		
		$("#downloadBtn").attr("onclick", "");
		$("#downloadBtn").attr("href", "#");
		$("#downloadBtn").attr("disabled", true);
		
		$("#selectBtn").attr("onclick", ``);
		$("#selectBtn").attr("disabled", true);
		return;
	}
	
	var urlParams = new URLSearchParams(window.location.search);
    var callback = urlParams.get('callback');
    var image = urlParams.get('image');
	
	var fileURL = "/assets/getPublicAsset?assetId=" + asset.id;
	var category = asset.fileCategory;
	
	/*
	var commonHTML = `
		<div class="mb-2">
			<span class="bold-text">Name: </span> ${asset.name}, 
			<span class="bold-text">Type: </span> ${asset.mediaType}, 
			<span class="bold-text">Category: </span> ${category}, 
			<span class="bold-text">Size: </span> ${asset.size}
		</div>
	`;
	*/
	
	// File type is IMAGE, show an image on the screen.
	if(category == "IMAGE"){
		$("#previewContent").html(`<img src="${fileURL}" class="img-fluid"></img>`);
	}
	
	// File type is TEXT, show an textarea on the screen.
	else if(category == "TEXT"){
		$.get(fileURL, function(data) {
   			$("#previewContent").html(`<textarea class="form-control" readonly>${data}</textarea>`);
		}, 'text');
	}
	
	// Other file types, no preview available.
	else{
		$("#previewContent").html(`
			<div class="d-flex justify-content-center big-icons mb-2">
				<i class="fa-solid fa-eye-slash"></i>
			</div>
									
			<div class="d-flex justify-content-center bold-text text-secondary">
				Preview is not available, please download the file directly.
			</div>
		`);
	}
	
	$("#downloadBtn").attr("onclick", `window.location.href='${fileURL}&download=true'`);
	$("#downloadBtn").attr("href", "fileURL");
	$("#downloadBtn").attr("disabled", false);
	
	$("#selectBtn").attr("onclick", `
		parent.$("#${callback}").val(${asset.id});
		parent.$("#${image}").attr("src", "${fileURL}");
		parent.$('#etwigModal').modal('hide');
	`);
	$("#selectBtn").attr("disabled", false);
}

function uploadFile(){
	var data = new FormData();
    var file = $('#fileUpload')[0].files[0];
    
    // Null check
    if(file == undefined || file == null){
		warningToast("Please select a file.");
		return;
	}
    
    data.append('file', file);
    
    $.ajax({
		type: 'POST',
        url: '/api/private/upload',
        data: data,
        contentType: false,
        processData: false,
        success: function(result) {
        	if(result.error > 0){
				dangerToast("Failed to upload file.", result.msg);
			}else{
				successToast("File upload successfully.");
				resetFile();
				$('#assetSelector').DataTable().ajax.reload();
			}	
        },
        error: function (err) {
			dangerToast("Failed to upload file due to a HTTP " + err.status + " error.", err.responseJSON.exception);
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
