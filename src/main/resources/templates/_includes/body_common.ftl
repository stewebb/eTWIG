	
	<#-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-etwig-blue">
  		<a class="navbar-brand" href="#"><img src=/static/images/eTWIG_white.png /></a>
  		
  		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    		<span class="navbar-toggler-icon"></span>
  		</button>

  		<div class="collapse navbar-collapse" id="navbarSupportedContent">
   			<ul class="navbar-nav mr-auto">
   				<li class="nav-item">
        			<a class="nav-link" href="/"><i class="fa-solid fa-home"></i>&nbsp;Home</a>
      			</li>
   				<li class="nav-item dropdown">
        			<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          				<i class="fa-solid fa-list"></i>&nbsp;Events
        			</a>
        			<div class="dropdown-menu" aria-labelledby="navbarDropdown">
          				<a class="dropdown-item" href="/events/calendar"><i class="fa-solid fa-calendar"></i>&nbsp;Calendar</a>
          				<a class="dropdown-item" href="/events/list"><i class="fa-solid fa-list"></i>&nbsp;List</a>
        			</div>
      			</li>
   			
   			
   			
      			<li class="nav-item">
        			<a class="nav-link" href="/public/TWIG"><i class="fa-solid fa-lightbulb"></i>&nbsp;TWIG</a>
      			</li>
      			<li class="nav-item active">
        			<a class="nav-link" href="/public/calendar"><i class="fa-solid fa-calendar"></i>&nbsp;Calendar</a>
      			</li>
    		</ul>
    		
    		<ul class="navbar-nav">
				 <li class="nav-item dropdown">
        		 	<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        		 		<i class="fa-solid fa-user"></i>&nbsp;Guest
        		 	</a>
        			<div class="dropdown-menu" aria-labelledby="navbarDropdown">
         				<a class="dropdown-item" href="#">Action</a>
          				<a class="dropdown-item" href="#">Another action</a>
          				<div class="dropdown-divider"></div>
          				<a class="dropdown-item" href="#">Something else here</a>
        			</div>
      			</li>
        	</ul>
  		</div>
	</nav>
	
	<#-- Alerts -->
	<div id="alerts" class="etwig-main"></div>
	
	<#-- Modal -->
	<div class="modal fade" tabindex="-1" id="etwig-modal">
  		<div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg">
    		<div class="modal-content">
      			<div class="modal-header">
        			<h5 class="modal-title bold" id="etwig-modal-title">Title</h5>
      			</div>
     			<div class="modal-body" id="etwig-modal-body">Content</div>
      			<div class="modal-footer">
        			<button type="button" class="btn btn-outline-secondary" data-dismiss="modal"><i class="fa-solid fa-xmark"></i>&nbsp;Close</button>
     			</div>
    		</div>
  		</div>
	</div>
