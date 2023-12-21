<#-- 
	Top navbar and sidebar component. 
	This is a part of the eTWIG platform.
-->

	<#-- Navbar -->
  	<nav class="main-header navbar navbar-expand navbar-white navbar-light">
  	
    <#-- Left navbar (Only a button to show/hode the aside) -->
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
      		
      		<#-- Switch to fullscreen -->
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
      			<span class="brand-text font-weight-light">&nbsp;</span>
    		</a>

    <#-- Sidebar -->
    <div class="sidebar">
      <!-- Sidebar user panel (optional) -->
      <div class="user-panel mt-3 pb-3 mb-3 d-flex">
        <div class="image">
          <img src="dist/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">
        </div>
        <div class="info">
          <a href="#" class="d-block">Alexander Pierce</a>
        </div>
      </div>

      <!-- SidebarSearch Form -->
      <div class="form-inline">
        <div class="input-group" data-widget="sidebar-search">
          <input class="form-control form-control-sidebar" type="search" placeholder="Search" aria-label="Search">
          <div class="input-group-append">
            <button class="btn btn-sidebar">
              <i class="fas fa-search fa-fw"></i>
            </button>
          </div>
        </div>
      </div>

      <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
          <li class="nav-item menu-open">
            <a href="#" class="nav-link active">
              <i class="nav-icon fas fa-tachometer-alt"></i>
              <p>
                Starter Pages
                <i class="right fas fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="#" class="nav-link active">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Active Page</p>
                </a>
              </li>
              <li class="nav-item">
                <a href="#" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Inactive Page</p>
                </a>
              </li>
            </ul>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>
                Simple Link
                <span class="right badge badge-danger">New</span>
              </p>
            </a>
          </li>
        </ul>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  </aside>