<!DOCTYPE html>
<html lang="en">
<head>
    <title>Banner Request Outcome</title>
</head>

<body>

    <div style="background-color: #DFDFDF; width: 100%; padding: 20px 0;">
        <table align="center" style="max-width: 640px; margin: 0 auto; width: 100%;">
            <tr>
                <td>
                    <div style="background-color: #FFFFFF; padding: 10px; border-radius: 10px;">
                        <h2 style="text-align: center; color: #004AAD; font-weight: bolder;">Banner Request Outcome</h2>

                        <#-- Information -->
                        <p>
                            Your banner request for event <b style="color:#004AAD">${eventName}</b> has been 
                            <#if isApproved>
                                <b style="color:#198754">Approved</b>
                            <#else>
                                <b style="color:#DC3545">Declined</b>
                            </#if>
                            on ${responseTime}.
                        </p>
                        
                        <#if isApproved>
                            <p>The banner has been attached in this email.</p>
                        </#if>
                        <#-- /Information -->

                        <!-- Button -->
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td align="center" style="padding-top: 20px;">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td align="center" style="background-color: #004AAD; border-radius: 6px;">
                                                <a href="https://www.google.com" target="_blank" style="font-size: 16px; font-weight: bolder; font-family: sans-serif; color: #ffffff; text-decoration: none; padding: 12px 24px; display: block; border-radius: 6px;">
                                                View Details
                                                </a>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <!-- /Button -->

                    </div>
                    <p style="line-height: 20px;">&nbsp;</p>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>

<#--
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
-->

                <#--
                <#assign nameParts = approvalInfo.requesterName?split(" ")>
	            <#assign firstName = nameParts[0]>
                <#assign result = condition?then("a", "b")>
                
	
	            <p>Dear FirstName,</p>
	            <p>Your banner request for event <b>${approvalInfo.eventName}</b> has been ${approvedStr}.</p>
	            <p>&nbsp;</p>

                -->