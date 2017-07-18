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
	  <div class="panel-heading">认可申请</div>
		  <div class="panel-body"> 
            <fieldset>
              <legend> </legend>
              <div class="form-group">
                 <label class="col-md-1 control-label" for="requireData">申请日期</label>
                 <div class="col-md-2">
                    <input type="text" id="requireData" class="form-control" name="requireData">
                 </div>
                 <label class="col-md-1 control-label" for="requireCompany">申请单位</label>
                 <div class="col-md-2">                
					<input type="text" id="requireCompany" class="form-control" name="requireCompany">
                 </div>
                 
                 <label class="col-md-2 control-label" for="equipmentName">申请ZB型号名称</label>
                 <div class="col-md-2">                
                   <input type="text" id="equipmentName" class="form-control" name="equipmentName">
                 </div>                 
              </div>
           </fieldset>
        		  
		  
		  
           <fieldset Style="margin-top:10px">
              <div class="form-group">
                 <div class="col-md-3">
      				<button type="button" class="btn btn-primary btn-sm" id="applicationSearchBtn">查询</button>
      				
                    <span class="btn btn-primary  btn-sm fileinput-button">
				        <i class="glyphicon glyphicon-plus"></i>
				        <span>导入</span>
				        <!-- The file input field used as target for the file upload widget -->
				         <input id="applicationImoprtBtn"  type="file" name="file" multiple>
				    </span>  
      				<button type="button" class="btn btn-primary btn-sm" id="applicationExportBtn">导出</button>
                 </div>
                                 
              </div>
           </fieldset>
           
       		  
	       	<div id="applicationResultList">
	       	<c:import url="/WEB-INF/views/application/list.jsp" charEncoding="UTF-8"></c:import>  
	       	</div>
	       	
           <div class="modal-backdrop fade in" style="display:none" id="loading">
		   <div class="loading"></div>  
	       </div>
      	      
	      </div>
      </div>
      </div>
  	<input type="hidden" name="page" id="page" value="1"/>
<div>
 <input type="hidden" id="applicationDateHid" value="">
 <input type="hidden" id="applicationRepairerHid" value="">
 <input type="hidden" id="equimentNameHid" value="">
 </div>	       	
      
 <div id="alertDiv"></div>
		<c:import url="/WEB-INF/views/common/commonScript.jsp"></c:import>  

	    <script type="text/javascript" src="/js/identification/application/application.js" charset="UTF-8"></script>	    
	    <script type="text/javascript">
	    identification.application = new Identification.application.List();

	    function goPage() {
	    	identification.application.searchList();
		}
      </script>

	  </body>
</html>

