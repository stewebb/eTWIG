function portfolioListTable(){
	$('#portfoliosTable').DataTable({
		"processing": true,
		"serverSide": true,
		"lengthMenu": [[10, 20, 50], [10, 20, 50]],
		"pageLength": 20,
		"searching": false, 
		"order": [[0, "asc"]],
		"ajax": {
			"url": "/api/portfolio/list",
			"type": "GET",
			"data": function(d) {
				return $.extend({}, d, {
					"sortColumn": d.columns[d.order[0].column].data,
					"sortDirection": d.order[0].dir
				});
			}
		},
		"columns": [
			{ "data": "id"},
			{ "data": "name"},
			{ 
                "data": "color",
                "orderable": false,
                "render": function (data, type, row) {
                    return `<span style="color:#${data}"><i class="fa-solid fa-square fa-xl"></i>&nbsp;#${data}</span>`;
                }
            },
			{ "data": "abbreviation"},
			{ 
                "data": "separatedCalendar",
                "render": function (data, type, row) {
                    return data ? '<span class="badge badge-primary">Yes</span>' : '<span class="badge badge-secondary">No</span>'
                }
            },
			{ "mRender": requestActionRender, "orderable": false}
		]
	});
}