

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
		return;
	}
	
	var fileURL = "/twig/assets?assetId=" + asset.id;
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
	
}
