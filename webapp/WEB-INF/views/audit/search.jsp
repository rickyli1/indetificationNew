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
	  <div class="panel-heading"><spring:message code="lable.audit.search.title"/></div>
		  <div class="panel-body"> 
            <fieldset>
              <legend> </legend>
              <div class="form-group">
                 <label class="col-md-1 control-label" for="requireData"><spring:message code="lable.audit.search.date"/></label>
                 <div class="col-md-2">
                   <span class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="requireData" data-link-format="yyyy-mm-dd">
                    <input class="form-control" type="text" value="" readonly="readonly">
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </span>
				     <input type="hidden" id="requireData" value="" />                
                 </div>
                 <label class="col-md-1 control-label" for="requireCompany"><spring:message code="lable.audit.search.company"/></label>
                 <div class="col-md-2">                
					<input type="text" id="requireCompany" class="form-control" name="requireCompany">
                 </div>
                 
                 <label class="col-md-2 control-label" for="equipmentName"><spring:message code="lable.audit.search.equiment.name"/></label>
                 <div class="col-md-2">                
                   <input type="text" id="equipmentName" class="form-control input-xlarge" name="equipmentName">
                 </div>  
                 
                  <div class="col-md-2">                
      				<button type="button" class="btn btn-primary btnSize" id="auditSearchBtn"><spring:message code="button.audit.search"/></button>
                 </div>                                
              </div>
           </fieldset>
        		  
		  
		  
           <fieldset Style="margin-top:10px">
              <legend> </legend>
           
              <div class="form-group">
                 <div class="col-md-*">
                       
				     <div style="display:inline-block;float:right;">  
				         <button type="button" class="btn btn-primary btnSize" id="auditSaveBtn">保存</button>
				     
       				     <button type="button" class="btn btn-primary btnSize" id="auditExportBtn">导出</button>
      				</div>
                 </div>
                                 
              </div>
           </fieldset>
           
       		  
	       	<div id="applicationResultList">
	       	<c:import url="/WEB-INF/views/audit/list.jsp" charEncoding="UTF-8"></c:import>  
	       	</div>
	       	
           <div class="modal-backdrop fade in" style="display:none;z-index:2000" id="loading">
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

	  </div>
 
		<c:import url="/WEB-INF/views/common/commonScript.jsp"></c:import>  

	    <script type="text/javascript" src="/js/identification/audit/audit.js" charset="UTF-8"></script>	    
	    <script type="text/javascript">
	    identification.audit = new Identification.audit.List();

	    function goPage() {
	    	identification.audit.searchList();
		}
      </script>

	  </body>
</html>

