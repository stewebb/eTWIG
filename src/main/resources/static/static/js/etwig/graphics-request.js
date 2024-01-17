function createDatePicker(){
	var datepicker = new tui.DatePicker("#returningDateWrapper", {
		date: Date.today(),
		type: "date",
		input: {
			element: "#returningDate",
			format: "yyyy-MM-dd",
			usageStatistics: false
		},
	});
}