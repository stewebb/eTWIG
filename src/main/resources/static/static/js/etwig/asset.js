function assetSelectorDataTable(){
	var dt = $('#assetSelector').DataTable({
        processing: true,
        serverSide: true,
        searching: true, 
        bAutoWidth: false,
        ajax: {
            url: "/api/private/getAssetList",
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
	
	var fileURL = "/assets/content.do?assetId=" + asset.id;
	var category = asset.fileCategory;
	
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
		parent.$("#uploadCallback").val(${asset.id});
		parent.$("#uploadImage").attr("src", "${fileURL}");
		parent.$('#etwigModal').modal('hide');
	`);
	$("#selectBtn").attr("disabled", false);
}

function uploadFile(isMultiple, fileElem){
	//var data = new FormData();
    //var file = $('#' + fileUploadSingle)[0].files[0];
    
    // Null check
    //if(file == undefined || file == null){
	//	warningPopup("Please select a file.");
	//	return;
	//}

	var data = new FormData();
	var file = $('#' + fileElem)[0].files[0];
  
	// Null check
	if(!file){
		warningPopup("Please select a file.");
	  	return;
  	}

  	// Add file and mode
	data.append('file', file);
  	data.append('isMultiple', isMultiple);
    
    //data.append('file', file);
    
    $.ajax({
		type: 'POST',
        url: '/api/asset/add',
        data: data,
        contentType: false,
        processData: false,
        success: function() {

			successPopup("File upload successfully.");

        	//if(result.error > 0){
			//	dangerPopup("Failed to upload file.", result.msg);
			//}else{
			//	successPopup("File upload successfully.");
				//resetFile();
				//$('#assetSelector').DataTable().ajax.reload();
			//}	
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

/**
 * Initializes a DataTable on the #assetsList element. This table is configured to
 * handle server-side processing and sorting. It fetches asset data asynchronously
 * from the "/api/asset/list" endpoint using a GET request. Additional configurations
 * include custom length menu options, default page length, custom language settings
 * for the search placeholder, default ordering, and specific column rendering functions.
 *
 * The server response is expected to handle sorting based on passed parameters for
 * the column index and direction of sort. The table defines several columns such as
 * asset ID, name, type, uploader, last modified date, and custom renders for preview
 * and actions which are not orderable.
 */

function assetListTable(){
	$('#assetsList').DataTable({
		"processing": true,
		"serverSide": true,
		"lengthMenu": [[10, 20, 50, 100], [10, 20, 50, 100]],
		"pageLength": 20,
		"language": {
            "searchPlaceholder": "File Name",
        },
		"order": [[0, "desc"]],
		"ajax": {
			"url": "/api/asset/list",
			"type": "GET",
			"data": function(d) {
				return $.extend({}, d, {
					"sortColumn": d.columns[d.order[0].column].data,
					"sortDirection": d.order[0].dir
				});
			}
		},
		"columns": [
			{ "data": "id", "orderable": true },
			{ "data": "name", "orderable": false },
			{ "data": "type", "orderable": false, "render": assetTypeRender },
			{ "data": "uploader", "orderable": false },
			{ "data": "lastModified", "orderable": true, "render": dateWeekRender },
			{ "mRender": assetPreviewRender, "orderable": false },
			{ "mRender": assetListActionRender, "orderable": false }
		]
	});
}