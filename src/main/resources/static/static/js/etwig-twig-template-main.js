function twigTemplateDataTable(){
	$('#twigTemplate').DataTable({
        "processing": true,
        "serverSide": true,
        "searching": false, 
        "ajax": {
            "url": "/api/private/getTwigTemplateList",
            "data": function (d) {
                d.page = d.start / d.length;
                d.size = d.length;
                console.log(d.page)
            },
            "type": "GET",
            "dataSrc": function (json) {
                return json.content; // Adjust based on your JSON response structure
            }
        },
        "columns": [
            { "data": "id" },
            { "data": "name" },
            { "data": "portfolioName" },
            { "data": "availableFrom", "render": function(data, type, row) {
            return data ? data : ''; 
        } },
            { "data": "availableTo" , "render": function(data, type, row) {
            return data ? data : ''; 
        }},
            {'mRender': function (data, type, full) {
     			return '<a href=\'view.php?id=\'' + full[0] + '\' class=\'btn btn-primary\'>Contact Sales</a>';
     			}
     		}
        ]
    });
}