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
            { data: "size"},
            { data: "uploader"},
            { data: "lastModified", render: lastModifiedRender},
        ]
    });

	// Row click highlight
	dt.on('click', 'tbody tr', (e) => {
   		var classList = e.currentTarget.classList;
 
   		if (classList.contains('selected')) {
        	classList.remove('selected');
    	}else {
        	dt.rows('.selected').nodes().each((row) => row.classList.remove('selected'));
        	classList.add('selected');
    	}
	});
	
	// Get clicked assetId
	dt.on('click', 'tbody td', function() {
 		console.log(dt.cell({ row: this.parentNode.rowIndex-1, column:0 }).data());
	});
    return dt;
}

function lastModifiedRender(data, type, row){
	return timeAgo(data); 
}