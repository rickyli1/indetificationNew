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
	  <div class="panel-heading"><spring:message code="lable.resultFile.search.title"/></div>
		  <div class="panel-body"> 
		  
           <fieldset>
              <legend> </legend>
              <div class="form-group">
                  <label class="col-md-1 control-label" for="repairerName"><spring:message code="lable.resultFile.search.company"/></label>
                 <div class="col-md-3">                
					<input type="text" id="repairerName" class="form-control" name="repairerName">
                 </div>
                 
                 <label class="col-md-1 control-label" for="equimentGroup"><spring:message code="lable.resultFile.search.group"/></label>
                 <div class="col-md-3">                
                   <input type="text" id="equimentGroup" class="form-control input-xlarge" name="equimentGroup">
                 </div> 
                 
                <label class="col-md-1 control-label" for="requireData"><spring:message code="lable.resultFile.search.date"/></label>
                 <div class="col-md-2">
                   <span class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="requireData" data-link-format="yyyy-mm-dd">
                    <input class="form-control" type="text" value="" readonly="readonly">
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </span>
				     <input type="hidden" id="requireData" value="" />                
                 </div>
                  
                 
                  <div class="col-md-1">                
      				<button type="button" class="btn btn-primary btnSize" id="resultFileSearchBtn"><spring:message code="button.resuktFile.search"/></button>
                 </div>                                
              </div>
           </fieldset>
               
       		  
	       	<div id="resultFileList">
	       		<c:import url="/WEB-INF/views/resultFile/list.jsp"></c:import>
	       	</div>
	       	
           <div class="modal-backdrop fade in" style="display:none" id="loading">
		   <div class="loading"></div>  
	       </div>
      	      
	      </div>
      </div>
      </div>
  	<input type="hidden" name="page" id="page" value="1"/>
  	<input type="hidden" name="equimentGroupHide" id="equimentGroupHide" value=""/>
  	<input type="hidden" name="requireDataHide" id="requireDataHide" value=""/>
  	<input type="hidden" name="repairerNameHide" id="repairerNameHide" value=""/>
      
    <div id="alertDiv"></div>
		<c:import url="/WEB-INF/views/common/commonScript.jsp"></c:import>  

	    <script type="text/javascript" src="/js/identification/resultFile/resultFile.js" charset="UTF-8"></script>	    
	    <script type="text/javascript">
		    identification.resultFile = new Identification.resultFile.List();
	
		    function goPage() {
		    	identification.resultFile.resultFilePageover();
			}
	    
	    </script>

</body>
</html>

