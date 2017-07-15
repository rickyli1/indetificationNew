<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE HTML>
<html lang="zh-cn">
	<head> 
	    <meta charset="utf-8">
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <c:import url="/WEB-INF/views/common/commonCss.jsp"></c:import>
	</head>
	<body>
		<div class="container">
			<c:import url="/WEB-INF/views/common/navgate.jsp"></c:import>

	<div class="panel panel-primary">
	  <div class="panel-heading"><spring:message code="lable.repairer.search.title"/></div>
		  <div class="panel-body"> 
		  
           <fieldset>
              <legend> </legend>
              <div class="form-group">
                 <div class="col-md-3">
      				<button type="button" class="btn btn-primary btn-sm" id="repairerSearchBtn">查询</button>
      				
                    <span class="btn btn-primary  btn-sm fileinput-button">
				        <i class="glyphicon glyphicon-plus"></i>
				        <span>导入</span>
				        <!-- The file input field used as target for the file upload widget -->
				         <input id="repairerImoprtBtn"  type="file" name="file" multiple>
				    </span>  
      				<button type="button" class="btn btn-primary btn-sm" id="repairerExportBtn">导出</button>
                 </div>
                                 
              </div>
           </fieldset>
           
       		  
	       	<div id="repairerResultList">
	       	<c:import url="/WEB-INF/views/repairer/list.jsp" charEncoding="UTF-8"></c:import>  
	       	</div>
	       	
           <div class="modal-backdrop fade in" style="display:none" id="loading">
		   <div class="loading"></div>  
	       </div>
      	      
	      </div>
      </div>
      </div>
  	<input type="hidden" name="page" id="page" value="1"/>
      
 <div id="alertDiv"></div>
		<c:import url="/WEB-INF/views/common/commonScript.jsp"></c:import>  

	    <script type="text/javascript" src="/js/identification/repairer/repairer.js" charset="UTF-8"></script>	    
	    <script type="text/javascript">
	    identification.repairer = new Identification.repairer.List();

	    function goPage() {
	    	identification.repairer.searchList();
		}
      </script>

	  </body>
</html>

