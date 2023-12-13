		<div class="etwig-box">
			<h4 class="header-text bold">
				<i class="fa-solid fa-briefcase"></i>&nbsp;Portfolio
			</h4>
			
			<div class="table-responsive collapse show" id="collapsePortfolioTable">
				<table class="table">
				<!--
					<thead>
    					<tr>
      						<th scope="col">Portfolio ID</th>
      						<th scope="col">Name</th>
      						<th scope="col">Icon and color</th>
    					</tr>
  					</thead>
  					<tbody>
						<#list portfolio as portfolio_id, portfolio_info>
						<tr>
							<td>${portfolio_id}</td>
							<td>${portfolio_info.name}</td>
							<td><i class="fa-solid fa-${portfolio_info.icon!"square"} fa-2xl" style="color:#${portfolio_info.color}"></i></td>
						</tr>
						</#list>
					</tbody>
				-->
					<#-- First row: Show only portfolio name -->
					<tr>
						<td><strong>Name</strong></td>
						<#list portfolio as portfolio_id, portfolio_info>
							<td>${portfolio_info.name}</td>
						</#list>
					</tr>
					
					<#-- Second row: Show portfolio color and icon -->
					<tr>
						<td><strong>Color and Icon</strong></td>
						<#list portfolio as portfolio_id, portfolio_info>
							<td><i class="fa-solid fa-${portfolio_info.icon!"square"} fa-2xl" style="color:#${portfolio_info.color}"></i></td>
						</#list>
					</tr>
				
				</table>
			</div>
			
			<div style="text-align:right;">
    			<a class="btn btn-outline-secondary my-2 my-sm-0" data-toggle="collapse" href="#collapsePortfolioTable" role="button" aria-expanded="false" aria-controls="collapseFilterBox"> 
    				<i class="fa-solid fa-eye"></i> &nbsp;Show/Hide
    			</a>
    		</div>
		</div>
