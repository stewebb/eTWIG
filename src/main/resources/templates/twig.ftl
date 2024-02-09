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
	
	<#-- Settings button -->
	<div class="setting-button">
    	<button id="settingsButton" class="btn btn-primary">

			<#-- Gear icon from Font Awesome -->
        	<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="28" height="28">
				<path d="M495.9 166.6c3.2 8.7 .5 18.4-6.4 24.6l-43.3 39.4c1.1 8.3 1.7 16.8 1.7 25.4s-.6 17.1-1.7 25.4l43.3 39.4c6.9 6.2 9.6 15.9 6.4 24.6c-4.4 11.9-9.7 23.3-15.8 34.3l-4.7 8.1c-6.6 11-14 21.4-22.1 31.2c-5.9 7.2-15.7 9.6-24.5 6.8l-55.7-17.7c-13.4 10.3-28.2 18.9-44 25.4l-12.5 57.1c-2 9.1-9 16.3-18.2 17.8c-13.8 2.3-28 3.5-42.5 3.5s-28.7-1.2-42.5-3.5c-9.2-1.5-16.2-8.7-18.2-17.8l-12.5-57.1c-15.8-6.5-30.6-15.1-44-25.4L83.1 425.9c-8.8 2.8-18.6 .3-24.5-6.8c-8.1-9.8-15.5-20.2-22.1-31.2l-4.7-8.1c-6.1-11-11.4-22.4-15.8-34.3c-3.2-8.7-.5-18.4 6.4-24.6l43.3-39.4C64.6 273.1 64 264.6 64 256s.6-17.1 1.7-25.4L22.4 191.2c-6.9-6.2-9.6-15.9-6.4-24.6c4.4-11.9 9.7-23.3 15.8-34.3l4.7-8.1c6.6-11 14-21.4 22.1-31.2c5.9-7.2 15.7-9.6 24.5-6.8l55.7 17.7c13.4-10.3 28.2-18.9 44-25.4l12.5-57.1c2-9.1 9-16.3 18.2-17.8C227.3 1.2 241.5 0 256 0s28.7 1.2 42.5 3.5c9.2 1.5 16.2 8.7 18.2 17.8l12.5 57.1c15.8 6.5 30.6 15.1 44 25.4l55.7-17.7c8.8-2.8 18.6-.3 24.5 6.8c8.1 9.8 15.5 20.2 22.1 31.2l4.7 8.1c6.1 11 11.4 22.4 15.8 34.3zM256 336a80 80 0 1 0 0-160 80 80 0 1 0 0 160z" fill="#FFFFFF" />
			</svg>
			<#-- /Gear icon from Font Awesome -->

    	</button>
	</div>
	<#-- /Settings button -->

	<#-- Required js -->
	<script src="/static/js/vendor/date.min.js"></script>
	<script src="/static/js/vendor/sweetalert2.min.js"></script>
	<script src="/static/js/vendor/p5.min.js"></script>


	<script src="/static/js/etwig/common.js"></script>
	<script src="/static/js/etwig/twig.js"></script>
	<script src="/static/js/etwig/twig-draw.js"></script>

</body>
</html>
