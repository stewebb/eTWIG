function eventGraphicsDataTable(){
	var dt = $('#eventGraphics').DataTable({
        processing: true,
        serverSide: true,
        searching: false, 
        bAutoWidth: false,
        ajax: {
            url: "/api/private/eventGraphicsList",
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
            { data: "graphicsNum"},
            { data: "bannerNum"},
            { data: "lastModified", render: dateRender},
            { mRender: actionRender}
        ]
    });
    return dt;
}

function actionRender(data, type, full){
	return `
		<div class="btn-group">
			<a href="/events/edit?eventId=${full.id}" class="btn btn-outline-secondary btn-sm" target="_blank">
				<i class="fa-solid fa-lightbulb"></i>&nbsp;Event Info
			</a>
			<a href="/graphics/events/view?eventId=${full.id}" class="btn btn-outline-primary btn-sm">
				<i class="fa-solid fa-image"></i>&nbsp;Graphics
			</a>
		</div>
	`;
}