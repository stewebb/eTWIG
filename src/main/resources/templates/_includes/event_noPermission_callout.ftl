<#-- 
	eTWIG - The event and banner management software for residential halls and student unions.
	copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, basic information part.
	This part contains the a callout to tell user he/she has no permission to perfrom some operations due to a different portfolio.
   -->

									<div class="callout callout-primary">
										<h5 class="bold-text mb-3">${calloutTitle}</h5>
										This event was created by the user with <span class="bold-text" style="color:#${eventDetails.portfolio.color}">${eventDetails.portfolio.name}</span> portfolio. <br />
										However, your portfolios are 
										<#if portfolio?has_content>
        									<#list portfolio as portfolio_id, portfolio_info>
        										<span class="bold-text" style="color:#${portfolio_info.color}">${portfolio_info.name}</span>
        									</#list>
        								<#else>
        									<strong>Null</strong>
        								</#if>.
									</div>