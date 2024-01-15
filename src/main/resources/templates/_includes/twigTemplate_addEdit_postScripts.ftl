<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, post JavaScript part.
	This part contains the JavaScript after the form. Commonly the initialization of some libraries.
   -->
   
   	<#-- Post Scripts -->
	<script>
	
      	$('#twigPortfolio').select2({
    		theme: 'bootstrap4',
    		templateResult: formatState,
  			templateSelection: formatState,
		});
    </script>