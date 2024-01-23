function pendingApprovalDataTable(){
	var dt = $('#pendingRequestsList').DataTable({
        processing: true,
        serverSide: true,
        searching: false, 
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

                    if (diffDays < 0) {
                        return data + '&nbsp;<span class="badge badge-danger">Overdue</span>';
                    } else if (diffDays < 3) {
                        return data + '&nbsp;<span class="badge badge-warning">3 days left</span>';
                    }
                }
                return data;
            }

function actionRender(data, type, full){
	return `
		<a href="/graphics/approval/action?eventId=${full.id}" class="btn btn-outline-primary btn-sm mr-2">
			<i class="fa-solid fa-check"></i>&nbsp;Approve
		</a>
		
		<a href="/graphics/approval/action?eventId=${full.id}" class="btn btn-outline-danger btn-sm">
			<i class="fa-solid fa-xmark"></i>&nbsp;Reject
		</a>
	`;
}