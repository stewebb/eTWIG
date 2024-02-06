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
		
		// 1 day left
		else if (diffDays == 1) {
			color = "warning";
			text = "1 day left";
		} 
		
		// 2-5 days left
		else if (diffDays <= 5) {
			color = "warning";
			text = diffDays + " days left";
		} 
		
		// 5+ days
		else{
			color = "primary";
			text = diffDays + " days left";
		}
		return `${data}&nbsp;<span class="badge badge-${color}">${text}</span>`;
		
	}	
	return data;
}

function approvedRender(data, type, row){
	return data ? `<i class="fa-solid fa-check text-success bold-text"></i>` : `<i class="fa-solid fa-xmark text-danger bold-text"></i>`;
}

function pendingActionRender(data, type, full){
	return `
		<a href="/graphics/approval/decide?requestId=${full.id}" class="btn btn-outline-primary btn-sm">
			<i class="fa-solid fa-check"></i>&nbsp;Decide
		</a>
	`;
}

function finalizedActionRender(data, type, full){
	return `
		<a href="/graphics/approval/view?eventId=${full.id}" class="btn btn-outline-primary btn-sm">
			<i class="fa-solid fa-eye"></i>&nbsp;Details
		</a>
	`;
}

function selectUpload(){
	$('#etwigModalTitle').text('Select/Upload');
	$("#etwigModalBody").load("/assets/_selector");
	$('#etwigModal').modal('show');
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
	
	//console.log(approvalDecisionObj);
	
	var hasError = true;
	$.ajax({
   		url: '/api/private/approveRequests', 
   		type: "POST",
   		async: false,
   		dataType: "json",
   		contentType: "application/json; charset=utf-8",
   		data: JSON.stringify(approvalDecisionObj),
   		success: function (result) {
			if(result.error > 0){
				dangerPopup("Failed to submit a decision.", result.msg);
				hasError = true;
			}else{
				successPopup("Decision made successfully.");
				hasError = false;
			}	
    	},
    	error: function (err) {
    		dangerPopup("Failed to submit a decision due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    		hasError = true;
    	}
 	});

	// Redirect back
	if(!hasError){
		setTimeout(function() {	$(location).attr('href','/graphics/approval/list'); }, 2500);
	}
	
}