
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
 * This function parses the input data string to a Date object and formats it into a 'YYYY-MM-DD HH:mm' format.
 * It's intended for use in table renderers where consistent date-time formatting is required.
 *
 * @param {string} data - The date string to be converted.
 * @param {string} type - The type of operation or context in which the function is called (unused in this function).
 * @param {object} row - The data row that contains the date string (unused in this function).
 * @returns {string} The formatted date-time string.
 */

function dateWeekRender(data, type, row){
	var targetDate = Date.parse(data);
	return targetDate.toString('yyyy-MM-dd HH:mm');
}