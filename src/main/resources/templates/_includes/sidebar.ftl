<#-- 
	eTWIG - The event and banner management software for residential halls and student unions.
	copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The common sidebar for every pages.
   -->

	<#-- Navbar -->
  	<nav class="main-header navbar navbar-expand navbar-white navbar-light">
  	
    <#-- Left navbar (Only a button to show/hide the aside) -->
    	<ul class="navbar-nav">
      		<li class="nav-item">
        		<a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
      		</li>
    	</ul>

    	<#-- Right navbar links -->
    	<ul class="navbar-nav ml-auto">

      		<#-- Notifications Dropdown Menu -->
      		<li class="nav-item dropdown">
       			<a class="nav-link" data-toggle="dropdown" href="#">
          			<i class="far fa-bell"></i>
          			<span class="badge badge-warning navbar-badge">15</span>
        		</a>
        		
        		<div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
          			<span class="dropdown-header">15 Notifications</span>
          			<div class="dropdown-divider"></div>
          			<a href="#" class="dropdown-item">
            			<i class="fas fa-envelope mr-2"></i> 4 new messages
            			<span class="float-right text-muted text-sm">3 mins</span>
          			</a>
          				
          			<a href="#" class="dropdown-item dropdown-footer">See All Notifications</a>
        		</div>
      		</li>
      		
      		<#-- Switch to full screen -->
      		<li class="nav-item">
        		<a class="nav-link" data-widget="fullscreen" href="#" role="button">
          			<i class="fas fa-expand-arrows-alt"></i>
        		</a>
      		</li>
    	</ul>
    </nav>
    
    <#-- Main Sidebar -->
  	<aside class="main-sidebar sidebar-dark-primary elevation-4">
  	
    	<#-- eTWIG Logo -->
    	<a href="/" class="brand-link">
      		<img src="/static/images/eTWIG_white.png" alt="eTWIG Logo" class="brand-image">
      		<span class="brand-text font-weight-light">Admin</span>
    	</a>

    	<#-- Sidebar -->
    	<div class="sidebar">
    	
      		<#-- User panel -->
      		<div class="user-panel mt-3 pb-3 mb-3 d-flex">
        		<div class="image">
        			<img src="/static/images/default_avatar.svg" class="elevation-2" alt="User Avatar">
        			<!--<i class="fa-solid fa-user fa-xl img-circle elevation-2" style="color: #ffffff;"></i>-->
        		</div>
        		
        		<div class="info">
          			<a href="#" class="d-block">
          				<#if user?has_content>${user.username}</#if>
          			</a>
        		</div>
        	
      		</div>

      		<#-- Menu -->
      		<nav class="mt-2">
        		<ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
        	
        			<#-- Dashboard -->
        			<li class="nav-item">
            			<a href="/" class="nav-link <#if navbar=="DASHBOARD">active</#if>">
              				<i class="nav-icon fas fa-gauge-high"></i>
              				<p>Dashboard</p>
            			</a>
          			</li>
          		
          			<#-- TWIG -->
        			<li class="nav-item">
            			<a href="/twig/main" target="_blank" class="nav-link <#if navbar=="TWIG">active</#if>">
              				<i class="nav-icon fas fa-tree"></i>
              				<p>TWIG</p>
            			</a>
          			</li>
          
					<#-- Event Management -->
          			<li class="nav-header">Event Management</li>
          			
          			<#-- Event: Calendar -->
        			<li class="nav-item">
            			<a href="/events/calendar" class="nav-link <#if navbar=="CALENDAR">active</#if>">
              				<i class="nav-icon fas fa-calendar"></i>
              				<p>Calendar</p>
            			</a>
          			</li>	
          			
          			<#-- Event: List -->
        			<li class="nav-item">
            			<a href="#" class="nav-link">
              				<i class="nav-icon fas fa-list"></i>
              				<p>List</p>
            			</a>
          			</li>		
          			
          			<#-- Event: Add -->
        			<li class="nav-item">
            			<a href="/events/add" class="nav-link <#if navbar=="ADD_EVENT">active</#if>">
              				<i class="nav-icon fas fa-plus"></i>
              				<p>Add</p>
            			</a>
          			</li>	
          			
          			<#-- Event: Edit -->
        			<li class="nav-item">
            			<a href="/events/edit" class="nav-link <#if navbar=="EDIT_EVENT">active</#if>">
              				<i class="nav-icon fas fa-pen-to-square"></i>
              				<p>Edit</p>
            			</a>
          			</li>	
          			
          			<#-- Event: Delete -->
        			<li class="nav-item">
            			<a href="/events/delete" class="nav-link <#if navbar=="DELETE_EVENT">active</#if>">
              				<i class="nav-icon fas fa-trash"></i>
              				<p>Delete</p>
            			</a>
          			</li>	
          			
          			<#-- Banner Management -->
          			<#-- <li class="nav-header">Banner Management</li> -->
          			
          			<#-- Account -->
          			<li class="nav-header">Account</li>
          			<li class="nav-item">
            			<a href="/user/logout" class="nav-link">
              				<i class="nav-icon fas fa-right-from-bracket"></i>
              				<p>Logout</p>
            			</a>
          			</li>
        		</ul>
      		</nav>
    	</div>
  </aside>