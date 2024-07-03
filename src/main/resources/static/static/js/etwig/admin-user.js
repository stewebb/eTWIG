function userListTable() {
    $('#usersTable').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "/api/user/list",
            "type": "GET",
            "data": function(d) {
                // Add any additional parameters here
                //d.portfolioId = $('#portfolioId').val();
                //d.roleId = $('#roleId').val();
                d.start = d.start;
                d.length = d.length;
                d.draw = d.draw;
                d.sortColumn = d.columns[d.order[0].column].data;
                d.sortDirection = d.order[0].dir;
            }
        },
        "columns": [
            { "data": "userId" },
            { "data": "userName" },
            { "data": "userEmail" },
            { "data": "userLastLogin" },
            { 
                "data": "userPositions",
                "render": function(data, type, row) {
                    return data.map(pos => pos.positionName).join("<br>");
                }
            },
            { 
                "data": "userPositions",
                "render": function(data, type, row) {
                    return data.map(pos => pos.portfolioName).join("<br>");
                }
            },
            { 
                "data": "userPositions",
                "render": function(data, type, row) {
                    return data.map(pos => pos.portfolioEmail).join("<br>");
                }
            },
            {
                "data": null,
                "defaultContent": "<button class='btn btn-outline-primary btn-sm'>Action</button>"
            }
        ]
    });
}