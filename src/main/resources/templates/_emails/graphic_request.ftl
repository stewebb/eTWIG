<!DOCTYPE html>
<html lang="en">
<head>
    <title>New Banner Request</title>
</head>

<body>
    <div style="background-color: #DFDFDF; width: 100%; padding: 20px 0;">
        <table align="center" style="max-width: 640px; margin: 0 auto; width: 100%;">
            <tr>
                <td>
                    <div style="background-color: #FFFFFF; padding: 10px; border-radius: 10px;">
                        <h2 style="text-align: center; color: #004AAD; font-weight: bolder;">New Banner Request</h2>

                        <#-- Information -->
                        <p style="line-height: 1.5;">
                            <b>${requester}</b> submitted a new banner request for event <b>${eventName}</b> on <b>${requestTime}</b>.
                        </p>
                        <#-- /Information -->

                        <!-- Button -->
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td align="center" style="padding-top: 20px;">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td align="center" style="background-color: #004AAD; border-radius: 6px;">
                                                <a href="${appUrl}/graphics/approvalDetails.do?requestId=${requestId}" target="_blank" style="font-size: 16px; font-weight: bolder; font-family: sans-serif; color: #ffffff; text-decoration: none; padding: 12px 24px; display: block; border-radius: 6px;">
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
                    <p style="line-height: 10px;">&nbsp;</p>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>