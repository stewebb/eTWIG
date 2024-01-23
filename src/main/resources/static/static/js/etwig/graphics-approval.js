function pendingApprovalDataTable(){
	var dt = $('#pendingRequestsList').DataTable({
        processing: true,
        serverSide: true,
        searching: false, 
        ajax: {
            url: "/api/private/getTwigTemplateLi",
            data: function (d) {
                d.page = d.start / d.length;
                d.size = d.length;
                //console.log(d.page)
            },
            type: "GET",
            dataSrc: function (json) {
                return json.content;
            }
        },
        columns: [
            { data: "id" },
            { data: "name" },
            { data: "portfolioName", render: portfolioRender },
            { data: "availableFrom", render: dateRender},
            { data: "availableTo" , render: dateRender},
            {mRender:actionRender}
        ]
    });
    return dt;
}

function portfolioRender(data, type, row){
	return data ? data : 'All portfolios'; 
}

function dateRender(data, type, row){
	return data ? data : 'N/A'; 
}

function actionRender(data, type, full){
	return `
	<div class="btn-group">
		<a href="/graphics/twigTemplate/design?templateId=${full.id}" class="btn btn-outline-primary btn-sm">
			<i class="fa-solid fa-wand-magic-sparkles"></i>
		</a>
		
		<a href="/graphics/twigTemplate/edit?templateId=${full.id}" class="btn btn-outline-secondary btn-sm">
			<i class="fa-solid fa-pencil"></i>
		</a>
			
		<a href="#" class="btn btn-outline-danger btn-sm">
			<i class="fa-solid fa-trash"></i>
		</a>
	</div>
	`;
}