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
				<th scope="col" colspan="2">Event details</th>
			</tr>
	
			<tr>
				<th scope="row">EventId</th>
				<td>${eventInfo.id}</td>
			</tr>
														
			<tr>
				<th scope="row">Name</th>
				<td>${eventInfo.name}</td>
			</tr>
														
			<tr>
				<th scope="row">Location</th>
				<td><#if eventInfo.location?has_content>${eventInfo.location}</#if></td>
			</tr>
														
			<tr>
				<th scope="row">Type</th>
				<td>${(eventInfo.recurrent)?string("Recurring","Single Time")} Event</td>
			</tr>
														
			<tr>
				<th scope="row">Start Time</th>
				<td>${eventInfo.startTime}</td>
			</tr>
														
			<tr>
				<th scope="row">Organizer</th>
				<td>${eventInfo.organizer.fullName}</td>
			</tr>
	
			<tr>
				<th scope="row">Position and Portfolio</th>
				<td id="eventPortfolio" style="background-color:#${eventInfo.portfolio.color}">
					${eventInfo.organizerPosition}, ${eventInfo.portfolio.name}
				</td>
			</tr>
	
			<tr class="table-background">
				<th scope="col" colspan="2">Graphics request details</th>
			</tr>
	
			<tr>
				<th scope="row">RequestId</th>
				<td></td>
			</tr>
	
			<tr>
				<th scope="row">Returning date</th>
				<td>${requestInfo.returningDate}</td>
			</tr>
	
			<tr>
				<th scope="row">Requester</th>
				<td></td>
			</tr>
	
			<tr>
				<th scope="row">Position and Portfolio</th>
				<td id="eventPortfolio"></td>
			</tr>
	
			<tr>
				<th scope="row">Additional Comments</th>
				<td>${requestInfo.requestComment}</td>
			</tr>
			
	</table>

</body>
</html>
