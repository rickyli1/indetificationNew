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
	  <div class="panel-heading">报告登录</div>
      <div class="panel-body"> 
  <form class="form-horizontal" role="form">
           <fieldset>
              
              <div class="form-group">
                 <label class="col-md-1 control-label"  for="company" id="companyLable"><spring:message code="lable.application.add.company.name"/></label>
                 <div class="col-md-3">   
                  <input type="text" class="form-control"  id="company"/>             
					  <select  class="form-control" id="companySelect" style="display:none">
					  <c:forEach var="company" items="${companys}" varStatus="status">
						  <option value="${company.companyNo}">${company.companyName}</option>
					  </c:forEach>					 
					</select>
                 </div>
                 
                 <label class="col-md-1 control-label" for="department">申请专业</label>
                 <div class="col-md-3">                
                    <input class="form-control" id="department" type="text"/>
                 </div>    
 
                <label class="col-md-1 control-label" for="applicationDate"><spring:message code="lable.application.add.application.date"/></label>
                 <div class="col-md-3">
                    <span class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="applicationDate" data-link-format="yyyy-mm-dd">
                    <input class="form-control" type="text" value="" readonly="readonly">
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </span>
				     <input type="hidden" id="applicationDate" value="" />
                 </div>     
                 
              
             </div> 
              <div class="form-group">
                                             
                <label class="col-md-1 control-label" for="resultFileupload"><spring:message code="lable.application.add.result.file"/></label>
				<div class="col-md-3">
		 			<span class="btn btn-default btn-sm fileinput-button">
				        <i class="glyphicon glyphicon-plus"></i>
				        <span><spring:message code="lable.application.add.application.select.file"/></span>
				        <!-- The file input field used as target for the file upload widget -->
				         <input id="resultFileupload"  type="file" name="file" multiple>
				    </span>
				    <span>
				     <span id="resultFileNameSpan"></span>
				     <input type="hidden" id="resultFileIdHid"/>
				    </span>				    
				</div>  
              </div>
           </fieldset>   
           
            <fieldset>
                <div class="form-group">
                  <div class="col-md-11">
                  </div>
                  <div class="col-md-1">
                  
                  <button type="button" class="btn btn-primary btn-sm" id="saveApplicationBtn"><spring:message code="button.application.add.save.application"/></button>
                  </div>                  
               </div>  
            </fieldset>   
              
        </form>
			  
      </div>  
 <div id="alertDiv"></div>
<c:import url="/WEB-INF/views/common/commonScript.jsp"></c:import>  

   <script type="text/javascript" src="/js/identification/resultFile/resultFileAdd.js" charset="UTF-8"></script>	    
   <script type="text/javascript">
   identification.resultFileAdd = new Identification.resultFile.Add();
    </script>

</body>
</html>

