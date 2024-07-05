/**
 * Initializes a DataTable on an element with the id 'assetSelector'. This DataTable is configured to
 * fetch data from a server endpoint, supporting sorting, pagination, and a custom length menu.
 * It includes an AJAX setup for dynamic retrieval of data based on the table's state such as sort order.
 * The function also sets up click event handling on table rows to manage row selection, enabling
 * interaction through enabling/disabling a select button and setting its click behavior dynamically.
 *
 * DataTable Configuration:
 * - Processing and serverSide are enabled for server-side operations.
 * - Pagination controls include options for 5, 10, or 20 rows per page, defaulting to 10.
 * - A search placeholder is set specifically for "File Name".
 * - Custom AJAX data handling to include sorting parameters.
 * - Columns configured for asset ID, name, uploader, and a custom renderer for asset previews, 
 *   with only the ID column being sortable.
 *
 * Row Click Event Handling:
 * - Toggles 'selected' class on table rows to indicate selection.
 * - Manages a button with the id 'selectBtn' to trigger an action with data from the selected row.
 * - Sets or clears the 'onclick' attribute of 'selectBtn' based on selection.
 *
 * Example HTML needed:
 * <table id="assetSelector"></table>
 * <button id="selectBtn" disabled>Select</button>
 *
 * Dependencies:
 * - jQuery and DataTables library must be included in the project.
 */

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
			{ "data": "id" },
			{ "data": "name", "orderable": false },
			{ "data": "uploader", "orderable": false },
			{ "mRender": assetPreviewRender, "orderable": false }
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
 * Handles the uploading of files to a specified endpoint via an AJAX POST request.
 * The function supports uploading multiple files contained within a single input element,
 * but only processes uploads one at a time. It updates the specified DataTable and displays
 * a popup message based on the result of the upload.
 * 
 * @param {string} fileElem - The ID of the input element that contains the files to be uploaded.
 * @param {string} selectorElem - The ID of the DataTable element to be reloaded after a successful upload.
 * 
 * Behavior:
 * - Validates file selection and shows a warning popup if no files are selected.
 * - Appends each selected file to FormData for transmission.
 * - Sends a POST request with the files to the backend.
 * - On successful upload: Reloads the DataTable and shows a success popup.
 * - On failure: Displays an error popup with detailed information about the failure.
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
			response ? successPopup("All files uploaded successfully.") : warningPopup("One or more files were empty and not uploaded.");
            //if (response) {
            //    successPopup("All files uploaded successfully.");
            //} else {
            //    warningPopup("One or more files were empty and not uploaded.");
            //}
            $('#' + selectorElem).DataTable().ajax.reload();
        },
        error: function(err) {
            dangerPopup("Failed to upload files due to a HTTP " + err.status + " error.", err.responseJSON ? err.responseJSON.exception : 'Unknown error');
        }
    });
}

/**
 * Attaches a change event handler to input elements with the class 'custom-file-input'.
 * This handler updates the text of the sibling element with class 'custom-file-label' based on the number of files selected.
 * If one file is selected, it displays the file's name. If multiple files are selected, it shows the total number of files.
 *
 * Usage:
 * - Place this script in a document with HTML structure where `.custom-file-input` is used for file inputs
 *   and `.custom-file-label` is used for labeling these inputs.
 *
 * Example HTML:
 * <label class="custom-file-label" for="fileInput">Choose file</label>
 * <input type="file" class="custom-file-input" id="fileInput" multiple>
 *
 * @event change - Triggered when the file selection in the input changes.
 */

$(".custom-file-input").on("change", function() {
    var filesCount = $(this).get(0).files.length;

    // If only one file is selected, show its name
    if (filesCount === 1) {
        var fileName = $(this).val().split("\\").pop();
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    } 

	// If more than one file, show the number of files
	else {
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