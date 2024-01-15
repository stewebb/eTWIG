<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, page header part.
	This part contains the common page header.
   -->
   		
       	<#-- Page header -->
		<section class="content-header">
			<div class="container-fluid">
				<div class="row mb-2">
					<div class="col-sm-6">
						<h1 class="bold-text">TWIG Template: ${modeStr}</h1>
					</div>
					<div class="col-sm-6">
						<ol class="breadcrumb float-sm-right">
							<li class="breadcrumb-item"><a href="/">Home</a></li>
							<li class="breadcrumb-item">Graphics</li>
							<li class="breadcrumb-item">
								<a href="/graphics/twigTemplate">TWIG Template</a>
							</li>
							<li class="breadcrumb-item">
								<a href="/graphics/twigTemplate/${modeStr?lower_case}">${modeStr}</a>
							</li>
						</ol>
					</div>
				</div>
			</div>
		</section>