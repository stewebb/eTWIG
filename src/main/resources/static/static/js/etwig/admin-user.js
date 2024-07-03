function userListTable() {
    $('#usersTable').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "/api/user/list",
            "type": "GET",
            "data": function(d) {
				return $.extend({}, d, {
					"sortColumn": d.columns[d.order[0].column].data,
					"sortDirection": d.order[0].dir
				});
			}
        },
        "columns": [
            { "data": "userId" },
            { "data": "userName" },
            { "data": "userEmail" },
            { "data": "userLastLogin" },
            { 
                "data": "userPositions",
                "orderable": false,
                "render": function(data, type, row) {
                    return data.map(pos => `<span>${pos.positionName}</span>`).join("<br class='mb-1'>");
                }
            },
            { 
                "data": "userPositions",
                "orderable": false,
                "render": function(data, type, row) {
                    return data.map(pos => `<span style="color:#${pos.portfolioColor};">${pos.portfolioName}</span>`).join("<br class='mb-1'>");
                }
            },
            { 
                "data": "userPositions",
                "orderable": false,
                "render": function(data, type, row) {
                    return data.map(pos => `<span>${pos.portfolioEmail}</span>`).join("<br class='mb-1'>");
                }
            },
            {
                "data": null,
                "orderable": false,
                "defaultContent": "<button class='btn btn-outline-primary btn-sm'>Action</button>"
            }
        ]
    });
}