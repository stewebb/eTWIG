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

function addUser() {
    var newUserObj = {};
    var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

    // User full name
	var userFullName = $('#userFullName').val();
    if(userFullName.length == 0){
		warningPopup("User full name is required.");
		return;
	}
    newUserObj["userFullName"] = userFullName;

    // User email
    var userEmail = $('#userEmail').val();
    if (!emailPattern.test(userEmail)) {
		warningPopup("User email address is invalid.");
		return;
    } 
    newUserObj["userEmail"] = userEmail;

    // User password
    var userPassword = $('#userPassword').val();
	if(!isPasswordComplex(userPassword)){
		warningPopup("User password must be at least 8 characters long and include uppercase, lowercase and numbers.");
		return;	
	}
    newUserObj["userPassword"] = userPassword;

    // Role and portfolio
    newUserObj["userSystemRole"] = $('#userSystemRole').val();
    newUserObj["userPortfolio"] = $('#userPortfolio').val();

    // Portfolio email
    var userEmail = $('#userEmail').val();
    if (!emailPattern.test(userEmail)) {
        warningPopup("User email address is invalid.");
        return;
    } 
    newUserObj["userEmail"] = userEmail;
    
    console.log(newUserObj);
}