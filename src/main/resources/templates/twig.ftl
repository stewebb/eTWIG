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
	<script>

		// Loading animation.
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

	<#-- SweetAlert 2-->
	<script src="/static/js/vendor/sweetalert2.min.js"></script>

	<#-- Bootstrap 4 -->
	<link rel="stylesheet" href="/static/css/vendor/bootstrap.min.css">
	<script src="/static/js/vendor/bootstrap.min.js"></script>

	<#-- Font Awesome 6 -->
	<link rel="stylesheet" href="/static/css/vendor/fontawesome.min.css">
	<link rel="stylesheet" href="/static/css/vendor/solid.min.css">

	<#-- date.js -->
	<script src="/static/js/vendor/date.min.js"></script>

	<#-- icheck-bootstrap -->
	<link rel="stylesheet" href="/static/css/vendor/icheck-bootstrap.min.css">

	<#-- ToastUI date picker -->
	<link rel="stylesheet" href="/static/css/vendor/tui-date-picker.min.css">
	<script src="/static/js/vendor/tui-date-picker.min.js"></script>

	<#-- Select 2 -->
	<link rel="stylesheet" href="/static/css/vendor/select2.min.css"/>
	<link rel="stylesheet" href="/static/css/vendor/select2-bootstrap4.min.css"/>
	<script src="/static/js/vendor/select2.min.js"></script>

	<#-- eTWIG platform -->
	<link rel="stylesheet" href="/static/css/etwig-style.css">
	<script src="/static/js/etwig/common.js"></script>	

	<#-- p5.js -->
	<script src="/static/js/vendor/p5.min.js"></script>

	<script src="/static/js/etwig/twig.js"></script>
	<script src="/static/js/etwig/twig-draw.js"></script>

	<#-- Settings button -->
	<div class="setting-button">
    	<button id="settingsButton" class="btn btn-primary">
        	<i class="fa fa-gear fa-xl fa-beat-fade" id="cogIcon"></i>
    	</button>
	</div>
	<#-- /Settings button -->
	

</body>
</html>
