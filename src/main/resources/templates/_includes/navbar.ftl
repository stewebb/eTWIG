        
        <#-- Nav -->
        <nav class="main-header navbar navbar-expand-md navbar-light navbar-white no-padding fixed-top">
            <div class="container-fluid">
            
            	<#-- Logo -->
				<#--
				<a href="/" class="navbar-brand navbar-border">
					<img src="/static/images/eTWIG.png" alt="eTWIG Logo" class="brand-image">
					<span class="brand-text font-weight-light">&nbsp;</span>
				</a>
				-->
				<#-- /Logo -->
				
				<button class="navbar-toggler order-1" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				
				<#-- Navbar left -->
				<div class="collapse navbar-collapse no-padding" id="navbarCollapse">
					<ul class="navbar-nav">
					
						<#-- Home -->
						<li class="nav-item <#if navbar=="HOME">active</#if>">
							<a href="/home.do" class="nav-link navbar-border">
								<i class="fa-solid fa-home"></i>&nbsp;Home
							</a>
						</li>
						<#-- /Home -->
						
						<#-- Events -->
						<li class="nav-item <#if navbar=="CALENDAR">active</#if>">
							<a href="/events/calendar.do" class="nav-link navbar-border">
								<i class="fa-solid fa-calendar-check"></i>&nbsp;Events
							</a>
						</li>
						<#-- /Events -->
						
						<#-- Graphics -->
          				<#if userPermission.graphicsAccess>
							<li class="nav-item dropdown <#if navbar?starts_with("GRAPHICS_")>active</#if>">
							
								<#-- Dropdown btn -->
								<a id="graphicsSubMenu" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link navbar-border">
									<i class="fa-solid fa-icons active"></i>&nbsp;Graphics&nbsp;<i class="fa-solid fa-caret-down"></i>
								</a>
								<#-- /Dropdown btn -->
								
								<ul aria-labelledby="graphicsSubMenu" class="dropdown-menu border-0 shadow">
								
									<#-- Graphics Approval -->
									<li>
										<a href="/graphics/approvalList.do" class="dropdown-item <#if navbar=="GRAPHICS_APPROVAL">active</#if>">
											<i class="fa-solid fa-circle-check"></i>&nbsp;Approval
										</a>
									</li>
									<#-- /Graphics Approval -->
									
									<#-- TWIG Template -->
									<!--
									<li>
										<a href="/graphics/twigTemplate/list" class="dropdown-item <#if navbar=="GRAPHICS_TWIG_TEMPLATE">active</#if>">
											<i class="fa-solid fa-lines-leaning"></i>&nbsp;TWIG Template
										</a>
									</li>
									-->
									<#-- TWIG Template -->

									<#-- Event -->
									<li>
										<a href="/graphics/eventList.do" class="dropdown-item <#if navbar=="GRAPHICS_EVENTS">active</#if>">
											<i class="fa-solid fa-list-ul"></i>&nbsp;Events
										</a>
									</li>
									<#-- /Event -->
								
								</ul>
							</li>
						</#if>
					    <#-- /Graphics -->
					
					    <#-- Admin -->
          				<#if userPermission.adminAccess>
							<li class="nav-item dropdown">
							
								<#-- Dropdown btn -->
								<a id="adminSubMenu" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link navbar-border">
									<i class="fa-solid fa-shield-halved"></i>&nbsp;Admin&nbsp;<i class="fa-solid fa-caret-down"></i>
								</a>
								<#-- /Dropdown btn -->
								
								<ul aria-labelledby="adminSubMenu" class="dropdown-menu border-0 shadow">
								
								</ul>
							</li>
						</#if>
					    <#-- /Admin -->

				    </ul>
                </div>
			</div>
			<#-- /Navbar left-->
			
            <#-- Navbar right-->
			<ul class="order-1 order-md-3 navbar-nav navbar-no-expand ml-auto">

				<#-- TWIG -->
				<li class="nav-item">
					<a href="/twig/index.do" class="nav-link navbar-border" target="_blank">TWIG</a>
				</li>
				<#-- /TWIG -->

				
                <#-- Notifications -->
                <li class="nav-item dropdown">
                	
                	<#-- Bell icon -->
                	<a class="nav-link navbar-border" data-toggle="dropdown" href="#">
                   		<i class="far fa-bell"></i>
						<!--
                    	<span class="badge badge-warning navbar-badge">15</span>
						-->
                	</a>
                	<#-- /Bell icon -->
                	
					<div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
						<span class="dropdown-header">No Notifications</span>
						<!--
						<span class="dropdown-header">15 Notifications</span>
						<div class="dropdown-divider"></div>
						
						<#-- Notification item -->
						<a href="#" class="dropdown-item">
							<i class="fas fa-envelope mr-2"></i> 4 new messages
							<span class="float-right text-muted text-sm">3 mins</span>
						</a>
						<#-- /Notification item -->
						-->

                    </div>
				</li>
                <#-- /Notifications -->
				

                <#-- Account -->
                <li class="nav-item dropdown <#if navbar=="USER">active</#if>">
						
                	<#-- User icon -->
                	<a class="nav-link navbar-border" data-toggle="dropdown" href="#">
                   		<i class="fa-solid fa-user"></i>
                	</a>
                	<#-- /User icon -->
                	
					<div class="dropdown-menu dropdown-menu-lg dropdown-menu-right ">
						                	
						<div class="user-panel d-flex justify-content-center mt-2 mb-2">
							<div class="image">
								<img src="" class="img-circle elevation-3">
							</div>
							<div class="info">
								<a href="#" class="d-block" id="userName">${userBasicInfo.fullName}</a>
							</div>
						</div>


						<div class="dropdown-divider"></div>

						<#-- Switch Role -->						
						<div class="dropdown-item">
							<div class="form-group">
								<label>
									<i class="fa-regular fa-user-tie"></i>&nbsp;Select Position
								</label>
								
								<select class="form-control select2bs4">

								<#list userPosition.myPositions as position>
									<#--
									<option value="${position.userRoleId}">${position.position}</option>
									-->

									
									<#if position.userRoleId == userPosition.myCurrentPosition.userRoleId>
										<option value="${position.userRoleId}" selected>${position.position}</option>
									<#else>
										<option value="${position.userRoleId}">${position.position}</option>
									</#if>
									
        						</#list>

								<#--
									<#list position.myRolesStr?keys as key>
										
									
										
										<#if key == position.myCurrentRoleStr>
											<option value="${key}" selected>${position.myRolesStr[key]}</option>
										<#else>
											<option value="${key}">${position.myRolesStr[key]}</option>
										</#if>
										
									</#list>
								-->
								</select>
							</div>
						</div>	
						<#-- /Switch Role -->
						
						<#-- Profile -->						
						<a href="/user/index.do" class="dropdown-item">
							<i class="fa-regular fa-id-badge"></i>&nbsp;Profile
						</a>	
						<#-- /Profile -->
						
						<#-- Logout -->						
						<a href="/user/logout" class="dropdown-item">
							<i class="fa-solid fa-right-from-bracket"></i>&nbsp;Logout
						</a>	
						<#-- /Logout -->

                    </div>
				</li>
                <#-- /Account -->

			</ul>
			<#-- /Navbar right-->

        </nav>