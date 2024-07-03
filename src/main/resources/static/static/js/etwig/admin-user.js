/**
 * Initializes the DataTable for displaying the user list.
 * 
 * This function sets up a DataTable with server-side processing for listing users. 
 * It configures the AJAX request to fetch user data from the server, handles sorting, 
 * and defines how each column should be rendered. The table includes user ID, name, email, 
 * last login, positions, and a custom action button.
 * 
 * @function userListTable
 */

function userListTable() {
    $('#usersTable').DataTable({
        "processing": true,
        "serverSide": true,
        "language": {
            "searchPlaceholder": "Search User",
        },
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
            { "data": "userLastLogin", "render": dateWeekRender },
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