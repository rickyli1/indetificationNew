<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
	  <div class="panel-heading"><spring:message code="lable.equipment.backup"/></div>
		  <div class="panel-body"> 
		  
           <fieldset>
              <legend> </legend>
              <div class="form-group">
                 <label class="col-md-1 control-label" for="fileName"><spring:message code="lable.equipment.fileName"/></label>
                 <div class="col-md-2">               
					 <input class="form-control" type="text" id="fileName" name="fileName">
                 </div>
                 <div class="col-md-1">                
                    <button type="button" class="btn btn-primary btnSize" id="backupBtn">备份</button>
                 </div>
                 <label class="col-md-1 control-label" for="filepath"><spring:message code="lable.equipment.filepath"/></label>
                 <div class="col-md-3" >                
                     <select  class="form-control" id="filepath" style="width:160px">
					 <option selected value=""></option>				
					    <c:forEach var="constant" items="${listFolders}" varStatus="status">
						  <option value="${constant}">${constant}</option>
					  </c:forEach>					 
					</select>
                 </div>  
               
                 <div class="col-md-1">                
                    <button type="button" class="btn btn-primary btnSize" id="recoverBakcupBtn">恢復</button>
                 </div>                                 
                 <div class="col-md-1">                
                    <button type="button" class="btn btn-primary btnSize" id="deleteBakcupBtn">删除</button>
                 </div> 
              </div>
           </fieldset>  		  
		   </div>
         </div>
       </div>
       	  <c:import url="/WEB-INF/views/common/commonScript.jsp"></c:import>  
          <script type="text/javascript" src="/js/identification/backup/backup.js" charset="UTF-8"></script>
	 
	 	  <script type="text/javascript">
	       identification.backup = new Identification.backup.List();

          </script>
	 	  
	  </body>
</html>

