<!DOCTYPE html>
<html>
<head>
    <title>New Graphics Request</title>
    <style>
        .text-theme-color{
            color: #004AAD;
        }
        .table-background{
            background-color: #004AAD;
			color: white;
			text-align: center;
        }
		.no-horizontal-border{
			border: 0;
		}
        .bold-text{
	        font-weight: bold;
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
    <h2 class="bold-text text-theme-color">New Graphics Request</h2>
    	
	<table>
			<tr class="table-background">
				<th scope="col" colspan="2">Graphics Request Details</th>
			</tr>
	
			<tr>
				<th scope="row">Event Name</th>
				<td><#--${requestInfo.eventName}--></td>
			</tr>
	
			<tr>
				<th scope="row">Expect Date</th>
				<td>${requestInfo.expectDate}</td>
			</tr>
				
			<tr>
				<th scope="row">Additional Comments</th>
				<td>${requestInfo.comments}</td>
			</tr>
	
			<tr>
				<th scope="row">Requester</th>
				<td>${requestInfo.requesterName}</td>
			</tr>
	
			<tr>
				<th scope="row">Position and Portfolio</th>
				<td style="background-color:#${requestInfo.requesterPortfolioColor}">
					${requestInfo.requesterPosition}, ${requestInfo.requesterPortfolioName}
				</td>
			</tr>
			
	</table>

</body>
</html>
