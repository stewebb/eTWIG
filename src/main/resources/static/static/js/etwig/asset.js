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

function uploadFile(){
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
        url: '/api/private/upload',
        data: data,
        contentType: false,
        processData: false,
        success: function(result) {
        	if(result.error > 0){
				dangerPopup("Failed to upload file.", result.msg);
			}else{
				successPopup("File upload successfully.");
				resetFile();
				$('#assetSelector').DataTable().ajax.reload();
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

function assetListTable(){
	$('#assetsList').DataTable({
		"processing": true,
		"serverSide": true,
		"lengthMenu": [[10, 20, 50, 100], [10, 20, 50, 100]],
		"pageLength": 20,
		"searching": false, 
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

			//{ "data": "requestTime", "orderable": true, "render": dateWeekRender},
			,
			//{ "data": "expectDate", "orderable": true, render: expectDateRender},
			//{ "data": "approved", "orderable": true, "render": approvalStatusRender},
			//{ "data": "approverName", "orderable": false, "render": optionalFieldsRender},
			
			
		]
	});
}