
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
 * Formats a date string with an HTML badge indicating the urgency based on how many days are left until the date.
 *
 * @param {string} data - The date string to be formatted.
 * @param {string} type - The type of operation; currently only 'display' is handled, which formats the date.
 * @param {Object} row - An object representing the entire data row (not used in current implementation).
 * @returns {string} The original date string with an appended HTML span element that includes a styled badge
 * indicating the urgency (e.g., "Overdue", "Due today", "1 day left", "{n} days left"). If the type is not 'display',
 * it returns the unmodified date string.
 */

function expectDateRender(data, type, row) {
    if (type === 'display') {

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
        return `${data}&nbsp;<span class="badge badge-${color}">${text}</span>`;
    }
    return data;
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