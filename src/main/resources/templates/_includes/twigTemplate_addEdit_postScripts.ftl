<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, post JavaScript part.
	This part contains the JavaScript after the form. Commonly the initialization of some libraries.
   -->
   
	<#-- Bootstrap color picker -->
	<link rel="stylesheet" href="/static/css/bootstrap-colorpicker.min.css">
	<script src="/static/js/bootstrap-colorpicker.min.js"></script>
	
	<#-- jQuery inputmask -->
   	<script src="/static/js/jquery.inputmask.min.js"></script>
   
   	<#-- Post Scripts -->
	<script>
	
      	$('#twigPortfolio').select2({
    		theme: 'bootstrap4',
    		templateResult: formatState,
  			templateSelection: formatState,
		});
		
		    $('.color-picker').colorpicker()

    $('.color-picker').on('colorpickerChange', function(event) {
      $('.color-picker .fa-palette').css('color', event.color.toString());
    })
    
      $("#position").inputmask('99,99', {placeholder: "__,__"});
    </script>