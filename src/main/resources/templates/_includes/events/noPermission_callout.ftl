<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, basic information part.
	This part contains the a callout to tell user he/she has no permission to perform some operations due to a different portfolio.
   -->

<#--
									<div class="callout callout-primary">
										<h5 class="bold-text mb-3">${calloutTitle}</h5>
										This event was created by the user with <span class="bold-text" style="color:#${eventDetails.portfolio.color}">${eventDetails.portfolio.name}</span> portfolio. <br />
										However, your portfolios are [
										<#if myPortfolios?has_content>
        									<#list myPortfolios as portfolio>
        										<span class="bold-text" style="color:#${portfolio.color}">${portfolio.name}</span>,&nbsp;
        									</#list>
        								<#else>
        									<strong>Null</strong>
        								</#if>
        								].
									</div>
-->