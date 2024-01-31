<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
<#assign navbar = "TWIG">

<!DOCTYPE html>
<html lang="en">
<head>

    <#-- Required meta tags. -->
	<meta charset="utf-8">
	<meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	
	<#-- The eTWIG icon. -->
	<link rel="icon" href="/static/images/favicon.ico">

	<#-- pace.js and JQuery. -->
	<script src="/static/js/vendor/pace.min.js"></script>
	<script src="/static/js/vendor/jquery.min.js"></script>
	
	<link rel="stylesheet" href="/static/css/twig.css">

    <title>TWIG - ${app.appName}</title>

    <style>

    </style>

	<script>
        paceOptions = {
            ajax: true,
            document: true,
            eventLag: true,
            elements: false,
            restartOnRequestAfter: false
        };

    	
        
        Pace.on('start', function() {
            $('#logo-container').show().find('#logo').addClass('beating-logo');
        });

        Pace.on('done', function() {
            var $logo = $('#logo');
            $logo.removeClass('beating-logo').addClass('shrinking-logo');

            // Wait for the animation to complete before hiding the logo container
            $logo.on('animationend', function() {
                $('#logo-container').hide();
            });
        });

		
    
        
    </script>
</head>

<body>

    <#-- Griffin Hall logo -->
    <div id="logo-container">
        <img src="/static/images/GHLogo.min.svg" id="logo" width="50%" height="50%"/>
    </div>
	<#-- /Griffin Hall logo -->
		
	<#-- Date.js. -->
	<!--
	<script src="/static/js/vendor/date.min.js"></script>
	-->

	<#-- p5.js -->
	<script src="/static/js/vendor/p5.min.js"></script>

	<script src="/static/js/etwig/twig.js"></script>
	<script src="/static/js/etwig/twig-draw.js"></script>

</body>
</html>
