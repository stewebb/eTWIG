

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
            { data: "mediaType"},
            { data: "fileCategory"},
            { data: "size"},
            { data: "uploader"},
            { data: "lastModified", render: lastModifiedRender},
        ]
    });

	// Row click highlight
	/*
	dt.on('click', 'tbody tr', (e) => {
   		var classList = e.currentTarget.classList;
 
   		if (classList.contains('selected')) {
        	classList.remove('selected');
        	//alert('Selection cancelled');
    	}else {
        	dt.rows('.selected').nodes().each((row) => row.classList.remove('selected'));
        	classList.add('selected');
        	
    	}
	});
	*/
	
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
        console.log(rowData);
        previewAsset(rowData);
    }
});

	// Get clicked assetId
	//dt.on('click', 'tbody td', function() {
 	//	var assetId = dt.cell({ row: this.parentNode.rowIndex-1, column:0 }).data();
 	//	console.log(assetId)
 	//	//previewAsset(assetId);
	//});
    return dt;
}

function lastModifiedRender(data, type, row){
	return timeAgo(data); 
}

function previewAsset(asset){
	
	if(asset == undefined || asset == null){
		$("#previewContent").html("");
		return;
	}
	
	//var id = asset.id;
	var category = asset.fileCategory;
	
	if(category == "IMAGE"){
		$("#previewContent").html(`
			<img src="/twig/assets?assetId=${asset.id}" class="img-fluid"></img>
		`);
	}
	
	
	//console.log("/twig/assets?assetId=" + assetId);
}
