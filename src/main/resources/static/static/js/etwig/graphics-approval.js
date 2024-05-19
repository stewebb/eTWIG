
function bannerRequestListTable(){
	$('#requestsTable').DataTable({
		"processing": true,
		"serverSide": true,
		"lengthMenu": [[10, 20, 50, 100], [10, 20, 50, 100]],
		"pageLength": 20,
		"searching": false, 
		"order": [[5, "desc"]],
		"ajax": {
			"url": "/api/bannerRequest/list?isApproved=na",
			"type": "GET",
			"data": function(d) {
				return $.extend({}, d, {
					"sortColumn": d.columns[d.order[0].column].data,
					"sortDirection": d.order[0].dir
				});
			}
		},
		"columns": [
			{ "data": "id", "orderable": true},
			{ "data": "eventName", "orderable": true},
			{ "data": "requestTime", "orderable": true, "render": dateWeekRender},
			{ "data": "requesterName", "orderable": false},
			{ "data": "expectDate", "orderable": true, render: expectDateRender},
			{ "data": "approved", "orderable": true, "render": approvalStatusRender},
			{ "data": "approverName", "orderable": false, "render": optionalFieldsRender},
			{ "data": "responseTime", "orderable": true, "render": dateWeekRender},
			{ "mRender": requestActionRender, "orderable": false}
			//
		]
	});
}

/*
function pendingApprovalDataTable(){
	var dt = $('#pendingRequestsList').DataTable({
        processing: true,
        serverSide: true,
        searching: false, 
        bAutoWidth: false,
        ajax: {
            url: "/api/private/getPendingRequests",
            data: function (d) {
                d.page = d.start / d.length;
                d.size = d.length;
            },
            type: "GET",
            dataSrc: function (json) {
                return json.content;
            }
        },
        columns: [
            { data: "id" },
            { data: "eventName" },
            { data: "requesterName"},
            { data: "requesterPosition"},
            { data: "expectDate", render: expectDateRender},
            { data: "requestComments"},
            {mRender:pendingActionRender}
        ]
    });
    return dt;
}

function finalizedApprovalDataTable(){
	var dt = $('#finalizedRequestsList').DataTable({
        processing: true,
        serverSide: true,
        searching: false, 
        bAutoWidth: false,
        ajax: {
            url: "/api/private/getFinalizedRequests",
            data: function (d) {
                d.page = d.start / d.length;
                d.size = d.length;
            },
            type: "GET",
            dataSrc: function (json) {
                return json.content;
            }
        },
        columns: [
            { data: "id" },
            { data: "eventName" },
            { data: "requesterName"},
            { data: "requesterPosition"},
            { data: "expectDate"},
            { data: "approved", render: approvedRender},
            { data: "approverName"},
            { data: "approverPosition"},
            { data: "responseTime"},
            {mRender: finalizedActionRender}
        ]
    });
    return dt;
}
*/

//function approvedRender(data, type, row){
//	return data ? `<i class="fa-solid fa-check text-success bold-text"></i>` : `<i class="fa-solid fa-xmark text-danger bold-text"></i>`;
//}

//function pendingActionRender(data, type, full){
//	return `
//		<a href="/graphics/approvalDetails.do?requestId=${full.id}" class="btn btn-outline-primary btn-sm">
//			<i class="fa-solid fa-check"></i>&nbsp;Decide
//		</a>
//	`;
//}

function finalizedActionRender(data, type, full){
	return `
		<a href="/graphics/approvalDetails.do?eventId=${full.id}" class="btn btn-outline-primary btn-sm">
			<i class="fa-solid fa-eye"></i>&nbsp;Details
		</a>
	`;
}

function setAssetsUpload(approvedMode){
	$('#graphicsApprovalAssets').toggle(approvedMode == 1);
}

function decide(){
	var approvalDecisionObj = {}
	
	// requestId
	approvalDecisionObj["id"]  = parseInt($('#requestId').text());
	
	// Role
	approvalDecisionObj["role"]  = parseInt($('#approverRole').find(":selected").val());
	
	// Approval option, 1 -> Approved, 0 -> Declined, NaN -> Not Selected
	var graphicsApprovalOption = parseInt($('input[type=radio][name=graphicsApprovalOption]:checked').val());
	if(isNaN(graphicsApprovalOption)){
		warningPopup("Make a decision is required");
		return;
	}
	approvalDecisionObj["approved"] = graphicsApprovalOption > 0;
	
	// Comments
	approvalDecisionObj["comments"] = $('#graphicsApprovalComments').val();
	
	// Assets
	if(approvalDecisionObj["approved"]){
		var assetId = parseInt($('#uploadCallback').val());
		if(isNaN(assetId)){
			warningPopup("Selecting an asset is the requisite for approving a graphic request.");
			return;
		}
		approvalDecisionObj["asset"] = assetId;
	}
		
	var hasError = true;
	$.ajax({
   		url: '/api/bannerRequest/approve', 
   		type: "POST",
   		async: false,
   		dataType: "json",
   		contentType: "application/json; charset=utf-8",
   		data: JSON.stringify(approvalDecisionObj),
   		success: function (result) {
			successPopup(result.message);
			hasError = false;
    	},
    	error: function (err) {
    		dangerPopup("Failed to submit a decision due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    		hasError = true;
    	}
 	});

	// Redirect back
	if(!hasError){
		setTimeout(function() {	$(location).attr('href','/graphics/approvalList.do'); }, 2500);
	}
	
}