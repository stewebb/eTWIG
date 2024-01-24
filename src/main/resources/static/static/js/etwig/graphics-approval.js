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
            {mRender:actionRender}
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

function actionRender(data, type, full){
	return `
		<a href="/graphics/approval/decide?eventId=${full.id}" class="btn btn-outline-primary btn-sm">
			<i class="fa-solid fa-check"></i>&nbsp;Decide
		</a>
	`;
}