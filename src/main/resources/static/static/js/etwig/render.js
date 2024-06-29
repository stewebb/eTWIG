
/**
 * Renders the approval status as an HTML span element with a specific badge class based on the data provided.
 * 
 * @param {boolean|null|undefined} data - The data indicating the approval status, which can be true (approved), 
 * 										  false (declined), or null/undefined (pending).
 * @param {*} type - (not used in the current implementation).
 * @param {*} row -  (not used in the current implementation).
 * @returns {string} HTML string representing a span element with a class and text that reflects the approval status: 
 * 										  'Pending', 'Approved', or 'Declined'.
 */

function approvalStatusRender(data, type, row){

	// null -> Pending
	if(data == undefined || data == null){
		return '<span class="badge badge-warning">Pending</span>';
	}

	// true -> Approved
	else if(data){
		return '<span class="badge badge-success">Approved</span>';
	}

	// false -> Declined
	else{
		return '<span class="badge badge-danger">Declined</span>';
	}
}

/**
 * Generates HTML markup for rendering an image element in a table cell based on the asset ID provided.
 * If the data is undefined or null, it returns 'N/A' indicating that the asset is not available.
 * Otherwise, it constructs an <img> tag with a source URL that includes the asset ID, styling the image
 * for display within a table.
 *
 * @param {string|null|undefined} data - The asset ID used to generate the image source URL.
 * @param {string} type - The type of operation or context in which the function is called (unused in this function).
 * @param {object} row - The data row that contains the asset ID (unused in this function).
 * @returns {string} An HTML string for the image element or 'N/A' if the asset ID is not available.
 */

function assetRender(data, type, row){
	return (data == undefined || data == null) ? 'N/A' : `<img src="/assets/content.do?assetId=${data}" class="table-img">`;
}

/**
 * Converts a date string into a formatted date-time string.
 * This function parses the input date string into a Date object using Date.parse() and then formats it into
 * the 'YYYY-MM-DD HH:mm' format using a date formatting library. It's intended for use in table renderers
 * where consistent date-time formatting is required.
 *
 * @param {string} data - The date string to be converted.
 * @param {string} type - The type of operation or context in which the function is called (unused in this function).
 * @param {object} row - The data row that contains the date string (unused in this function).
 * @returns {string} The formatted date-time string, or 'N/A' if the input is falsy.
 */

function dateWeekRender(data, type, row){
    return data ? Date.parse(data).toString('yyyy-MM-dd HH:mm') : 'N/A'; 
}

/**
 * Formats a date string with an HTML badge indicating the urgency based on the number of days left until the date.
 * The badge's color and text reflect the urgency: overdue, due today, due tomorrow, or a specific number of days left.
 * A badge is added only if the 'row.approved' attribute is null, indicating pending approval.
 *
 * @param {string} data - The date string to be formatted.
 * @param {string} type - The type of operation; currently, 'display' is the only handled type, which triggers formatting.
 * @param {Object} row - An object representing the entire data row, used to check the approval status before adding a badge.
 * @returns {string} The date string with an appended HTML span element styled as a badge if the condition meets. 
 *                   If the 'type' is not 'display', or the 'row.approved' is not null, it returns the unmodified date string.
 */

function expectDateRender(data, type, row) {

    var today = new Date();
    var date = new Date(data);
    var timeDiff = date.getTime() - today.getTime();
    var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));

    var color;
    var text;

    // Overdue
    if (diffDays < 0) {
        color = "danger";
        text = "Overdue";
    } 
        
    // Due today
    else if (diffDays == 0) {
        color = "warning";
        text = "Due today";
    } 
        
    // Due tomorrow
    else if (diffDays == 1) {
        color = "warning";
        text = "Due tomorrow";
    } 
        
    // 2-5 days left
    else if (diffDays <= 5) {
        color = "warning";
        text = diffDays + " days left";
    } 
        
    // 5+ days left
    else {
        color = "primary";
        text = diffDays + " days left";
    }

    // Only add a badge on a pending request.
    var output = data;
    if(row.approved == null) {
        output += `&nbsp;<span class="badge badge-${color}">${text}</span>`;
    }
    
    return output;
}

/**
 * Renders optional fields with a fallback to 'N/A'.
 * This function checks if the provided data is truthy. If it is, the function returns the data as-is;
 * otherwise, it returns 'N/A'. This is useful for table renderers or any display logic where missing
 * optional data should be represented by a placeholder.
 *
 * @param {*} data - The data to be rendered. This can be any type that needs to be displayed.
 * @param {string} type - The type of operation or context in which the function is called (unused in this function).
 * @param {object} row - The data row that contains the optional field (unused in this function).
 * @returns {string} The original data if truthy, or 'N/A' if falsy.
 */

function optionalFieldsRender(data, type, row) {
    return data ? data : 'N/A';
}

/**
 * Generates an HTML string for an action button linked to a request's approval details.
 * This function constructs an anchor tag styled as a button that directs to a detailed approval page for a specific request.
 * The request ID is dynamically inserted into the URL from the 'full' parameter, which is expected to be an object containing the request ID.
 * This button is typically used in data tables or web interfaces where actions can be initiated for specific records.
 *
 * @param {*} data - The data associated with the row, not used directly in generating the link (unused in this function).
 * @param {string} type - The type of operation or context in which the function is called (unused in this function).
 * @param {object} full - The complete data object for the row, expected to include an 'id' property used to construct the URL.
 * @returns {string} An HTML string that renders an action button with a link to the approval details page for the request.
 */

function requestActionRender(data, type, full){
	return `
		<a href="/graphics/approvalDetails.do?requestId=${full.id}" class="btn btn-outline-primary btn-sm">
            <i class="fa-solid fa-eye"></i>&nbsp;Details
		</a>
	`;
}

/**
 * Renders a formatted span element with text color and styling based on the provided data value.
 * This function is used within a DataTable to dynamically style the display of twig component counts.
 *
 * @param {number} data - The numerical count of twig components.
 * @param {string} type - The type of operation (usually 'display').
 * @param {object} row - The data for the current row in the DataTable.
 * @returns {string} HTML string representing a span element with styled twig component count.
 */

function twigComponentCountRender(data, type, row) {
    return `<span class="text-${(data > 0) ? "primary" : "danger"} bold-text">${data}</span> `;
}

/**
 * Renders a formatted span element with text color and styling based on the provided data value.
 * This function is used within a DataTable to dynamically style the display of banner counts.
 *
 * @param {number} data - The numerical count of banners.
 * @param {string} type - The type of operation (usually 'display').
 * @param {object} row - The data for the current row in the DataTable.
 * @returns {string} HTML string representing a span element with styled banner count.
 */

function bannerCountRender(data, type, row) {
    return `<span class="text-${(data > 0) ? "primary" : "info"} bold-text">${data}</span> `;
}

/**
 * Renders a formatted span element with text color and styling based on the provided data value.
 * This function is used within a DataTable to dynamically style the display of pending approval counts.
 *
 * @param {number} data - The numerical count of items pending approval.
 * @param {string} type - The type of operation (usually 'display').
 * @param {object} row - The data for the current row in the DataTable.
 * @returns {string} HTML string representing a span element with styled pending approval count.
 */

function pendingApprovalCountRender(data, type, row) {
    return `<span class="text-${(data > 0) ? "warning" : "primary"} bold-text">${data}</span> `;
}

/**
 * Renders a formatted span element with date and a badge indicating the time relation to the current week.
 * This function is used within a DataTable to provide a visual indicator of an event's timing relative to the current week.
 *
 * @param {string} data - The date string of the event.
 * @param {string} type - The type of operation (usually 'display').
 * @param {object} row - The data for the current row in the DataTable.
 * @returns {string} HTML string representing a formatted date and badge indicating time relation.
 */

function dateWeekWithBadgeRender(data, type, row){

	// Get dates
	var targetDate = Date.parse(data);
	var dateWeek = targetDate.toString('yyyy-MM-dd HH:mm') + '&nbsp;';

    var today = new Date();
    var dayOfWeek = today.getDay(); 						// Get current day of the week (0 for Sunday, 6 for Saturday)
    var dateWeek = targetDate.toDateString() + '&nbsp;'; 	// Assuming targetDate is a Date object

    // Find Monday of this week
    var monday;

	// If today is Sunday, go back six days to last Monday
    if (dayOfWeek === 0) {
        monday = new Date(today.setDate(today.getDate() - 6));	
    } 
	
	// Otherwise, subtract the current day of week number minus 1 to get to the last Monday
	else {
        monday = new Date(today.setDate(today.getDate() - dayOfWeek + 1));
    }

    // Calculate week differences
    var daysDifference = (targetDate - monday) / (1000 * 60 * 60 * 24);
    var weeksDifference = Math.floor(daysDifference / 7);

    // Weeks left
    if (monday < targetDate) {

        // Current Week
        if (weeksDifference === 0) {
            return dateWeek + `<span class="badge badge-danger">In this week</span>`;
        }

        // Next week
        if (weeksDifference === 1) {
            return dateWeek + `<span class="badge badge-warning">In next week</span>`;
        }

        // More than 1 week left
        return dateWeek + `<span class="badge badge-primary">${weeksDifference + 1} weeks left</span>`;
    }
	
	// Weeks passed
	else {
		dateWeek += (`<span class="badge badge-secondary">Past event</span>`);
	}

	return dateWeek;
}

/**
 * Renders a button group with links for editing event information and viewing event graphics.
 * This function is used within a DataTable to provide quick access to detailed views and graphics associated with events.
 *
 * @param {string} data - The placeholder for data, not directly used in the function but required by DataTables format.
 * @param {string} type - The type of operation (usually 'display').
 * @param {object} full - The complete data object for the row, used to access specific properties like event ID.
 * @returns {string} HTML string representing a button group with action links for the event.
 */

function summaryActionRender(data, type, full){
	return `
		<div class="btn-group">
			<a href="/events/edit.do?eventId=${full.id}" class="btn btn-outline-secondary btn-sm" target="_blank">
				<i class="fa-solid fa-lightbulb"></i>&nbsp;Event Info
			</a>
			<a href="/graphics/eventGraphics.do?eventId=${full.id}" class="btn btn-outline-primary btn-sm">
				<i class="fa-solid fa-image"></i>&nbsp;Graphics
			</a>
		</div>
	`;
}

/**
 * Renders the asset type with a corresponding badge based on its category.
 *
 * This function generates a string that includes the asset type and a span element
 * with a badge class styled according to the file category. If the category is unknown
 * or unspecified, it defaults to displaying "Unknown" with a danger badge.
 *
 * @param {Object} data - The data object passed to the DataTables render function.
 * @param {string} type - The type descriptor from DataTables, often used for handling different rendering modes.
 * @param {Object} row - The data for the current row. Expected to have 'fileCategory' and 'type' properties.
 * @returns {string} The HTML string representing the formatted type with a styled badge indicating the category.
 */

function assetTypeRender(data, type, row) {

    // Unknown file category
    if (!row.fileCategory) {
        return `${row.type}&nbsp;<span class="badge badge-danger">Unknown</span>`;
    }

    // File category is Image
    else if (row.fileCategory == "IMAGE") {
        return `${row.type}&nbsp;<span class="badge badge-primary">Image</span>`;
    }

    // File category is Text
    else if (row.fileCategory == "TEXT") {
        return `${row.type}&nbsp;<span class="badge badge-success">Text</span>`;
    }

    // File category is Text
    else if (row.fileCategory == "APPLICATION") {
        return `${row.type}&nbsp;<span class="badge badge-secondary">Application</span>`;
    }

    // Other categories
    else {
        return `${row.type}&nbsp;<span class="badge badge-warning">Other</span>`;
    }
}

function assetPreviewRender(data, type, row){

    if (!row.fileCategory) {
        return `<span class="text-secondary">Unknown file category.</span>`;
    }
    var fileURL = "/assets/content.do?assetId=" + row.id;
    
    // File type is IMAGE, display it
	if(row.fileCategory == "IMAGE"){
		return `<img src="${fileURL}" class="img-fluid table-img"></img>`;
	}
	
	// File type is TEXT, show first 128 characters.
	else if(row.fileCategory == "TEXT"){

        var content = '';
        $.ajax({
            url: fileURL,
            type: 'GET',
            async: false,
            success: function(data) {
                content = `<textarea class="form-control" readonly>${data.substring(0, 128)}</textarea>`;
            },
            error: function() {
                content = `<span class="text-secondary">Failed to load the text file.</span>`;
            }
        });

        return content;
	}
	
    // Other types, the file cannot be previewed.
    else {
        return `<span class="text-secondary">Preview is not available.</span>`;
    }

}

function assetListActionRender(data, type, full){

    var disabledStr = full.canDelete ? '' : 'disabled';

	return `
		<a href="/assets/content.do?assetId=${full.id}&download=true" class="btn btn-outline-secondary btn-sm" target="_blank">
			<i class="fa-solid fa-download"></i>&nbsp;Download
		</a>&nbsp;

		<a href="/assets/content.do?assetId=${full.id}&download=false" class="btn btn-outline-primary btn-sm" target="_blank">
			<i class="fa-solid fa-magnifying-glass-plus"></i>&nbsp;View
		</a>&nbsp;

		<button type="button" class="btn btn-outline-danger btn-sm" ${disabledStr}>
			<i class="fa-solid fa-trash"></i>&nbsp;Delete
		</button>
	`;
}