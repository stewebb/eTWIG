<!DOCTYPE html>
<html>
<head>
    <title>Graphics Request Approval</title>
    <style>
        .text-theme{
            color: #004AAD;
			font-weight: bold;
        }
        .table-title{
            background-color: #004AAD;
			color: white;
			font-weight: bold;
        }
		.approved{
			background-color: #28A745;
			color: white;
			font-weight: bold;
		}
		.declined{
			background-color: #DC3545;
			color: white;
			font-weight: bold;
		}

		.no-horizontal-border{
			border: 0;
		}
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
    </style>
</head>

<body>
    <h2 class="text-theme">Graphics Request Results</h2>
    <table>
		<tr class="${approvedStr}">
			<th scope="col">Result</th>
			<td></td>
		</tr>

		<tr>
			<th scope="row">Response Time</th>
			<td>${approvalInfo.responseTime}</td>
		</tr>

		<tr>
			<th scope="row">Feedback</th>
			<td>${approvalInfo.responseComment}</td>
		</tr>

		<tr>
			<th scope="row">Approver</th>
			<td>${approvalInfo.approverName}</td>
		</tr>

		<tr>
			<th scope="row">Position and Portfolio</th>
			<td style="background-color:#${approvalInfo.approverPortfolioColor}">
				${approvalInfo.approverPosition}, ${approvalInfo.approverPortfolioName}
			</td>
		</tr>

		<tr>
			<td class="no-horizontal-border">&nbsp;</td>
			<td class="no-horizontal-border">&nbsp;</td>
		</tr>

		<tr class="table-title">
			<th scope="col" colspan="2">Request Details</th>
		</tr>

		<tr>
			<th scope="row">Request Time</th>
			<td>${approvalInfo.requestTime}</td>
		</tr>

		<tr>
			<th scope="row">Expect Date</th>
			<td>${approvalInfo.requestTime}</td>
		</tr>

		<tr>
			<th scope="row">Additional Comments</th>
			<td>${approvalInfo.requestTime}</td>
		</tr>

		<tr>
			<th scope="row">Requester</th>
			<td>${approvalInfo.requesterName}</td>
		</tr>

		<tr>
			<th scope="row">Position and Portfolio</th>
			<td style="background-color:#${approvalInfo.requesterPortfolioColor}">
				${approvalInfo.requesterPosition}, ${approvalInfo.requesterPortfolioName}
			</td>
		</tr>

		<tr>
			<td class="no-horizontal-border">&nbsp;</td>
			<td class="no-horizontal-border">&nbsp;</td>
		</tr>

		<tr class="table-title">
			<th scope="col" colspan="2">Event Details</th>
		</tr>
													
		<tr>
			<th scope="row">Name</th>
			<td>${approvalInfo.eventName}</td>
		</tr>
													
		<tr>
			<th scope="row">Location</th>
			<td>${approvalInfo.eventLocation}</td>
		</tr>
													
		<tr>
			<th scope="row">Type</th>
			<td>${(approvalInfo.eventRecurrent)?string("Recurring","Single Time")} Event</td>
		</tr>
													
		<tr>
			<th scope="row">Start Time</th>
			<td>${approvalInfo.eventStartTime}</td>
		</tr>

		<tr>
			<th scope="row">Duration</th>
			<td>${approvalInfo.eventDuration}</td>
		</tr>
													
		<tr>
			<th scope="row">Organizer</th>
			<td>${approvalInfo.organizerName}</td>
		</tr>

		<tr>
			<th scope="row">Position and Portfolio</th>
			<td style="background-color:#${approvalInfo.organizerPortfolioColor}">
				${approvalInfo.organizerPosition}, ${approvalInfo.organizerPortfolioName}
		</tr>

	</table>
</body>
</html>
