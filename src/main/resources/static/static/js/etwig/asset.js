function assetSelectorDataTable(){
	var dt = $('#assetSelector').DataTable({
		"processing": true,
		"serverSide": true,
		"lengthMenu": [[5, 10, 20], [5, 10, 20]],
		"pageLength": 10,
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
			//{ "data": "type", "orderable": false, "render": assetTypeRender },
			//{ "data": "size", "orderable": true, "render": fileSizeRender },
			{ "data": "uploader", "orderable": false },
			//{ "data": "lastModified", "orderable": true, "render": dateWeekRender },
			{ "mRender": assetPreviewRender, "orderable": false }//,
			//{ "mRender": assetListActionRender, "orderable": false }
		]
	});

	dt.on('click', 'tbody tr', function(e) {
		var classList = e.currentTarget.classList;
	
		// De-select a row
		if (classList.contains('selected')) {
			classList.remove('selected');

			$("#selectBtn").attr("onclick", ``);
			$("#selectBtn").attr("disabled", true);
		}
		
		// Select a row
		else {
			
			// Deselect any currently selected row
			dt.rows('.selected').nodes().each((row) => row.classList.remove('selected'));
			classList.add('selected');
	
			var rowData = dt.row(this).data();
			var fileURL = "/assets/content.do?assetId=" + rowData.id;
			//console.log(rowData);
			//previewAsset(rowData);
			console.log(rowData);

			$("#selectBtn").attr("onclick", `
				parent.$("#uploadCallback").val(${rowData.id});
				parent.$("#uploadImage").attr("src", "${fileURL}");
				parent.$('#etwigModal').modal('hide');
			`);
			$("#selectBtn").attr("disabled", false);
		}
	});
}

function lastModifiedRender(data, type, row){
	
	// Only precise to seconds.
	return timeAgo(data); 
}

/*
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
*/

/**
 * Handles the uploading of a file to a specified endpoint via AJAX POST request.
 * This function supports both single and multiple file upload modes, but only processes one file per call.
 * Upon successful upload, it reloads a specified DataTable and shows a success popup.
 * If the upload fails, it displays an error popup with detailed information.
 *
 * @param {boolean} isMultiple - Indicates if the upload is meant to handle multiple files.
 *                               This function logic currently supports one file, but this parameter
 *                               can be used for backend processing logic differentiation.
 * @param {string} fileElem - The ID of the input element that contains the file to be uploaded.
 * @param {string} selectorElem - The ID of the DataTable element to be reloaded after successful upload.
 */

function uploadFile(isMultiple, fileElem, selectorElem){
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
    
    $.ajax({
		type: 'POST',
        url: '/api/asset/add',
        data: data,
        contentType: false,
        processData: false,
        success: function() {
			successPopup("File upload successfully.");
			$('#' + selectorElem).DataTable().ajax.reload();
        },
        error: function (err) {
			dangerPopup("Failed to upload file due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    	}
    });
}

$(".custom-file-input").on("change", function() {
	var fileName = $(this).val().split("\\").pop();
	$(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});

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
		"lengthMenu": [[5, 10, 20], [5, 10, 20]],
		"pageLength": 10,
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
			{ "data": "id" },
			{ "data": "name", "orderable": false },
			{ "data": "type", "orderable": false, "render": assetTypeRender },
			{ 
				"data": "size", 
				"render": function (data, type, row) {

					// Renders file size from numeric data into a human-readable string with appropriate units.
					// This function handles conversion of file size data to a string with units such as Bytes, KB, MB, or GB.
					if (data < 1024) return data + " Bytes";
					else if (data < 1024 * 1024) return (data / 1024).toFixed(2) + " KB";
					else if (data < 1024 * 1024 * 1024) return (data / 1024 / 1024).toFixed(2) + " MB";
					else return (data / 1024 / 1024 / 1024).toFixed(2) + " GB";
				} 
			},
			{ "data": "uploader", "orderable": false },
			{ "data": "lastModified", "orderable": true, "render": dateWeekRender },
			{ "mRender": assetPreviewRender, "orderable": false },
			{ 
				// Action
				"mRender": function (data, type, full) {
					var disabledStr = full.canDelete ? '' : 'disabled';
					return `
						<div class="btn-group" role="group">
							<a href="${$('#assetContentLink').val()}?assetId=${full.id}&download=true" class="btn btn-outline-secondary btn-sm" target="_blank">
								<i class="fa-solid fa-download"></i>&nbsp;Download
							</a>&nbsp;
				
							<a href="${$('#assetContentLink').val()}?assetId=${full.id}&download=false" class="btn btn-outline-primary btn-sm" target="_blank">
								<i class="fa-solid fa-magnifying-glass-plus"></i>&nbsp;View
							</a>&nbsp;
				
							<button type="button" class="btn btn-outline-danger btn-sm" ${disabledStr}>
								<i class="fa-solid fa-trash"></i>&nbsp;Delete
							</button>
						</div>
					`;
				}, 
				"orderable": false 
			}
		]
	});
}