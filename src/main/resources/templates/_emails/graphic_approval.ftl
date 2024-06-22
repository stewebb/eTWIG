<!DOCTYPE html>
<html lang="en">
<head>
    <title>Banner Request Outcome</title>
</head>

<body>

    <div style="background-color: #CFCFCF; width: 100%; padding: 20px 0;">
    <table align="center" style="max-width: 720px; margin: 0 auto; width: 100%;">
      <tr>
        <td>
          <div style="background-color: #FFFFFF; padding: 10px; border-radius: 10px;">
            <h2 style="text-align: center; color: #004AAD; font-weight: bolder;">Banner Request Outcome</h2>
            <p>
              Your banner request for event <b style="color:#004AAD">${eventName}</b> has been 
              <!-- Conditional rendering would need to be processed server-side before sending, or using templating language -->
              <b style="color: ${isApproved ? '#198754' : '#DC3545'}">
                ${isApproved ? 'Approved' : 'Declined'}
              </b> on ${responseTime}.
            </p>
          </div>
          <p style="line-height: 20px;">&nbsp;</p>
        </td>
      </tr>
    </table>
  </div>



    <div id="back">
        <div id="core">
    
            <p>&nbsp;</p>
            <div id="section">
                <h2 class="center" style="color: #004AAD; font-weight: bolder;">Banner Request Outcome</h2>


                <p>
                    Your banner request for event <b style="color:#004AAD">${eventName}</b> has been 
                    <#if isApproved>
                        <b style="color:#198754">Approved</b>
                    <#else>
                        <b style="color:#DC3545">Declined</b>
                    </#if>
                    &nbsp;on ${responseTime}.
                </p>


                
            </div>
            <p>&nbsp;</p>
        </div>
    </div>
</body>

</html>


                <#--
                <#assign nameParts = approvalInfo.requesterName?split(" ")>
	            <#assign firstName = nameParts[0]>
                <#assign result = condition?then("a", "b")>
                
	
	            <p>Dear FirstName,</p>
	            <p>Your banner request for event <b>${approvalInfo.eventName}</b> has been ${approvedStr}.</p>
	            <p>&nbsp;</p>

                -->