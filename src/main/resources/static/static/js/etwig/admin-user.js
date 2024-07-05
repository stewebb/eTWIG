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
                "orderable": false,
                "mRender": function(data, type, row) {
                    return `
                        <a class='btn btn-outline-primary btn-sm' href='${$('#userDetailsLink').val()}?userId=${row.userId}'>
                            <i class="fa-solid fa-user-pen"></i>&nbsp;Details
                        </a>
                    `;
                }
            }
        ]
    });
}

/**
 * Adds a new user to the system by collecting input from HTML form fields,
 * validating the inputs, and then sending the data to a server endpoint via AJAX.
 * 
 * The function collects values for the user's full name, email, password, system role,
 * portfolio, portfolio email, and position. It performs validation on the full name,
 * email addresses, and password to ensure they meet specific criteria before submission.
 * 
 * Errors during the input collection or AJAX request are displayed using popup notifications.
 * 
 * ## Form Validation:
 * - **Full Name**: Must not be empty.
 * - **Email Addresses (User and Portfolio)**: Must match a standard email format.
 * - **Password**: Must be at least 8 characters and include uppercase, lowercase, and numbers.
 * - **Position**: Must not be empty.
 *
 * ## AJAX Submission:
 * - **Endpoint**: `/api/user/add`
 * - **Type**: POST
 * - **Content Type**: `application/json; charset=utf-8`
 * - **Data**: JSON string of user details.
 *
 * ## Popups:
 * - **Success**: If the user is added successfully, a success message is shown and the page is reloaded after 2.5 seconds.
 * - **Error**: Errors during validation or the AJAX request display an appropriate message. Specific error popups are shown 
 * for invalid inputs and server-side issues (like existing email).
 *
 * @returns {void} This function does not return a value but initiates an asynchronous operation.
 */

function addUser() {
    var newUserObj = {};
    //var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

    // User full name
	var userFullName = $('#userFullName').val();
    if(userFullName.length == 0){
		warningPopup("User full name is required.");
		return;
	}
    newUserObj["userFullName"] = userFullName;

    // User email
    var userEmail = $('#userEmail').val();
    if (!emailCheck(userEmail)) {
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
    var userPortfolioEmail = $('#userPortfolioEmail').val();
    if (!emailCheck(userPortfolioEmail)) {
        warningPopup("Portfolio email address is invalid.");
        return;
    } 
    newUserObj["userPortfolioEmail"] = userPortfolioEmail;

    // Position
    var userPosition = $('#userPosition').val();
    if(userPosition.length == 0){
		warningPopup("User position name is required.");
		return;
	}
    newUserObj["userPosition"] = userPosition;

    // Submit
    $.ajax({
        url: '/api/user/add', 
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(newUserObj),
        success: function (result) {

            // Backend return true indicates successfully add.
            if(result) {
                successPopup("User added successfully.")
                setTimeout(function() { window.location.reload();}, 2500);
            } else {
                dangerPopup("Failed to add user" , "User email already exists.");
            }
        },
            error: function (err) {
            dangerPopup("Failed to add user due to a HTTP " + err.status + " error.", err.responseJSON.exception);
        }
    });
}

/**
 * Collects user details from the form, validates them, and sends an AJAX request to update the user information.
 * This function constructs an object with user details and performs client-side validation for each field:
 * - Ensures the full name is provided.
 * - Validates the email address format.
 * - Checks password complexity if a password is provided.
 * If validation passes, it sends a POST request to update the user details.
 *
 * The function displays warnings for validation errors and notifies the user of the operation's success or failure.
 * 
 * Fields collected and validated:
 * - userId: Extracted from the DOM element with ID 'userId'.
 * - userFullName: Must not be empty. Extracted from the DOM element with ID 'userFullName'.
 * - userEmail: Validated against a standard email pattern. Extracted from the DOM element with ID 'userEmail'.
 * - userPassword: Optionally validated for complexity. Extracted from the DOM element with ID 'userPassword'.
 *
 * On success, it displays a message indicating whether the password was changed and reloads the page.
 * On failure, it shows an error message with details about the failure.
 *
 * Usage:
 * This function is intended to be called when a form is submitted for updating user details.
 */

function updateUserDetails() {
    var userObj = {};

    // User ID
    userObj["userId"] = $('#userId').text();

    // User full name
	var userFullName = $('#userFullName').val();
    if(userFullName.length == 0){
		warningPopup("User full name is required.");
		return;
	}
    userObj["userFullName"] = userFullName;

    // User email
    var userEmail = $('#userEmail').val();
    if (!emailCheck(userEmail)) {
		warningPopup("User email address is invalid.");
		return;
    } 
    userObj["userEmail"] = userEmail;

    // User password
    var userPassword = $('#userPassword').val();
    if(userPassword && userPassword.length > 0 && !isPasswordComplex(userPassword)){
        warningPopup(
            "User password must be at least 8 characters long and include uppercase, lowercase and numbers.",
            "If you don't want to change user's password, you can keep this field blank."
        );
        return;	
    }
    userObj["userPassword"] = userPassword;

    // Submit
    $.ajax({
        url: '/api/user/edit', 
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(userObj),
        success: function (result) {

            // Backend return true indicates changed password.
            successPopup("User added successfully with password <i>" + (result ? "changed" : "unchanged") + "</i>.")
            setTimeout(function() { window.location.reload();}, 2500);
        },
            error: function (err) {
            dangerPopup("Failed to add user due to a HTTP " + err.status + " error.", err.responseJSON.exception);
        }
    });
}