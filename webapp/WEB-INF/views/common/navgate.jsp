<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<style>

			.navbar-default {
			  background-color: #2063E8;
			  border-color: #1337a3;
			}
			.navbar-default .navbar-brand {
			  color: #f3f5f9;
			}
			.navbar-default .navbar-brand:hover,
			.navbar-default .navbar-brand:focus {
			  color: #d6dbee;
			}
			.navbar-default .navbar-text {
			  color: #f3f5f9;
			}
			.navbar-default .navbar-nav > li > a {
			  color: #f3f5f9;
			}
			.navbar-default .navbar-nav > li > a:hover,
			.navbar-default .navbar-nav > li > a:focus {
			  color: #d6dbee;
			}
						.navbar-default .navbar-nav > li > .dropdown-menu {
			  background-color: #3c5fe7;
			}
			.navbar-default .navbar-nav > li > .dropdown-menu > li > a {
			  color: #f3f5f9;
			}
			.navbar-default .navbar-nav > li > .dropdown-menu > li > a:hover,
			.navbar-default .navbar-nav > li > .dropdown-menu > li > a:focus {
			  color: #d6dbee;
			  background-color: #1337a3;
			}
			.navbar-default .navbar-nav > li > .dropdown-menu > li > .divider {
			  background-color: #3c5fe7;
			}
			.navbar-default .navbar-nav .open .dropdown-menu > .active > a,
			.navbar-default .navbar-nav .open .dropdown-menu > .active > a:hover,
			.navbar-default .navbar-nav .open .dropdown-menu > .active > a:focus {
			  color: #d6dbee;
			  background-color: #1337a3;
			}
						.navbar-default .navbar-nav > .active > a,
			.navbar-default .navbar-nav > .active > a:hover,
			.navbar-default .navbar-nav > .active > a:focus {
			  color: #d6dbee;
			  background-color: #1337a3;
			}
			.navbar-default .navbar-nav > .open > a,
			.navbar-default .navbar-nav > .open > a:hover,
			.navbar-default .navbar-nav > .open > a:focus {
			  color: #d6dbee;
			  background-color: #1337a3;
			}
			.navbar-default .navbar-toggle {
			  border-color: #1337a3;
			}
			.navbar-default .navbar-toggle:hover,
			.navbar-default .navbar-toggle:focus {
			  background-color: #1337a3;
			}
			.navbar-default .navbar-toggle .icon-bar {
			  background-color: #f3f5f9;
			}
			.navbar-default .navbar-collapse,
			.navbar-default .navbar-form {
			  border-color: #f3f5f9;
			}
			.navbar-default .navbar-link {
			  color: #f3f5f9;
			}
			.navbar-default .navbar-link:hover {
			  color: #d6dbee;
			}
			@media (max-width: 767px) {
			  .navbar-default .navbar-nav .open .dropdown-menu > li > a {
			    color: #f3f5f9;
			  }
			  .navbar-default .navbar-nav .open .dropdown-menu > li > a:hover,
			  .navbar-default .navbar-nav .open .dropdown-menu > li > a:focus {
			    color: #d6dbee;
			  }
			  .navbar-default .navbar-nav .open .dropdown-menu > .active > a,
			  .navbar-default .navbar-nav .open .dropdown-menu > .active > a:hover,
			  .navbar-default .navbar-nav .open .dropdown-menu > .active > a:focus {
			    color: #d6dbee;
			    background-color: #1337a3;
			  }
			
</style>
<!--  <div><img alt="" src="/images/ban2.jpg" style="width:100%;height:50%"></div>-->
<div style="width:100%;height:50px;background-color:#1887f0"><span style="margin-left:38%"> <em style="color: white;font-size: xx-large;">XXXXX维修系统</em> </span></div>
<nav class="navbar navbar-default" role="navigation">
   <div class="container">
     <div id="navbar" class="navbar-collapse collapse">
       <ul class="nav navbar-nav">
         <li><a href="/repairer/init"><spring:message code="lable.navgate.repairer"/></a></li>
         <li><a href="/application/init">认可申请</a></li>
         <li><a href="/equipment/init">承修单位</a></li>
       </ul>
     </div><!--/.nav-collapse -->
   </div>
</nav>