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

function uploadFiles(fileElem, selectorElem) {
    var data = new FormData();
    var files = $('#' + fileElem)[0].files;

    // Check if any files are selected
    if (files.length === 0) {
        warningPopup("Please select files to upload.");
        return;
    }

    // Append each selected file to FormData
    for (let i = 0; i < files.length; i++) {
        data.append('files', files[i]);
    }

    // AJAX request to upload the files
    $.ajax({
        type: 'POST',
        url: '/api/asset/add',
        data: data,
        contentType: false,
        processData: false,
        success: function(response) {
            if (response) {
                successPopup("All files uploaded successfully.");
            } else {
                warningPopup("One or more files were empty and not uploaded.");
            }
            $('#' + selectorElem).DataTable().ajax.reload();
        },
        error: function(err) {
            dangerPopup("Failed to upload files due to a HTTP " + err.status + " error.", err.responseJSON ? err.responseJSON.exception : 'Unknown error');
        }
    });
}


/*
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
*/

/*
$(".custom-file-input").on("change", function() {
	var fileName = $(this).val().split("\\").pop();
	$(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});
*/

$(".custom-file-input").on("change", function() {
    var filesCount = $(this).get(0).files.length;

    if (filesCount === 1) {
        // If only one file is selected, show its name
        var fileName = $(this).val().split("\\").pop();
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    } else {
        // If more than one file, show the number of files
        $(this).siblings(".custom-file-label").addClass("selected").html(filesCount + " files selected");
    }
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
			{ "data": "lastModified", "render": dateWeekRender },
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