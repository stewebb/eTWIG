<!DOCTYPE html>
<html>
	<head>
	<#include "./includes/header.ftl">
	<link rel="stylesheet" href="/static/css/welcome.css">
	<title>Welcome to eTWIG</title>
</head>

<body>

	<div class="container h-100">
  		<div class="row h-100 justify-content-center align-items-center">
  			<center>
  		 		<div class="form-group">
        			<p><img src="/static/images/eTWIG.png" class="img-fluid" alt="eTWIG logo" style="max-width: 400px; width:100%;"></p>
        			<p><h2 id="welcome-label">Welcome to eTWIG</h2></p>
     			</div>
     			<p>&nbsp;</p>
      			<div class="form-group col-12">
        			<a href="/public/TWIG" class="btn btn-outline-primary btn-lg btn-block"><i class="fa-regular fa-lightbulb"></i>&nbsp;TWIG</a>
      			</div>
     			<div class="form-group col-12">
					<a href="/public/events" class="btn btn-outline-success btn-lg btn-block"><i class="fa-regular fa-calendar"></i>&nbsp;Events</a>
				</div>
				<div class="form-group col-12">
					<a href="/public/help.do" class="btn btn-outline-warning btn-lg btn-block"><i class="fa-solid fa-circle-question"></i>&nbsp;Help</a>
				</div>
    		</center>   	
  		</div>
	</div>


<!--
<div class="container h-100">
  <div class="row h-100 justify-content-center align-items-center">
    <form class="col-12">
      <div class="form-group">
        <label for="formGroupExampleInput">Example label</label>
        <input type="text" class="form-control" id="formGroupExampleInput" placeholder="Example input">
      </div>
      <div class="form-group">
        <label for="formGroupExampleInput2">Another label</label>
        <input type="text" class="form-control" id="formGroupExampleInput2" placeholder="Another input">
      </div>
    </form>   
  </div>
</div>
-->

	<#include "./includes/footer.ftl">
</body>
</html>