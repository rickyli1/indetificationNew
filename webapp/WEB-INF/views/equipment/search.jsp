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
	  <div class="panel-heading"><spring:message code="lable.equipment.equipmentSearch"/></div>
		  <div class="panel-body"> 
		  
           <fieldset>
              <legend> </legend>
              <div class="form-group">
                <label class="col-md-1 control-label" for="equipmentGroup"><spring:message code="lable.equipment.group"/></label>
                 <div class="col-md-2">
                 	<input class="form-control" type="text" id="equipmentGroup" name="equipmentGroup">
                 </div>
                 <label class="col-md-1 control-label" for="equipmentSubGroup"><spring:message code="lable.equipment.subGroup"/></label>
                 <div class="col-md-2">
					<input class="form-control" type="text" id="equipmentSubGroup" name="equipmentSubGroup">
                 </div>
                 <label class="col-md-1 control-label" for="equipmentName"><spring:message code="lable.equipment.equipmentName"/></label>
                 <div class="col-md-2">               
					 <input class="form-control" type="text" id="equipmentName" name="equipmentName">
                 </div>
                 
                 <label class="col-md-1 control-label" for="equipmentLevel"><spring:message code="lable.equipment.modifyCategory"/></label>
                 <div class="col-md-1">                
                     <select  class="form-control" id="equipmentLevel">
					 <option selected value=""></option>				
						  <option value="大修">大修</option>
						  <option value="中修">中修</option>
						  <option value="小修">小修</option>
						  <option value="检修">检修</option>
					</select>
                 </div>  
                 <div class="col-md-1">                
                    <button type="button" class="btn btn-primary btnSize" id="equipmentSearchBtn">查询</button>
                 </div>                                
                                 
              </div>
           </fieldset>  		  


             <fieldset style="margin-top:10px">
                <legend> </legend>
              <div class="form-group">
                 <div class="col-md-*">
             
      				
                <span class="btn btn-primary  btnSize fileinput-button">
				        <i class="glyphicon glyphicon-plus"></i>
				        <span>导入</span>
				        <!-- The file input field used as target for the file upload widget -->
				         <input id="equipmentImoprtBtn"  type="file" name="file" multiple>
				</span>  
				
		        <div style="display:inline-block;float:right;">  
      			<button type="button" class="btn btn-primary btnSize" id="equipmentExportBtn">导出</button>
                 </div>
                 
                 </div>
              </div>
                 
            </fieldset>  		  
	       	<div id="equipmentResultList">
	       	<c:import url="/WEB-INF/views/equipment/list.jsp" charEncoding="UTF-8"></c:import>  
	       	</div>
      	      
      	      
      	   <div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true">
	      	<div class="modal-dialog" style="width:900px">
	      		<form method="post">
	      			<div class="modal-content">
	      				<div class="modal-header">
	      					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        		<h4 class="modal-title" id="detailModalLabel"><spring:message code="lable.equipment.equipmentInfo"/></h4>
	      				</div>
					<c:import url="/WEB-INF/views/equipment/update.jsp" charEncoding="UTF-8"></c:import>
					</div>
				</form>
			</div>
		  </div>
	      </div>
      </div>
      </div>
	      <div class="modal-backdrop fade in" style="display:none;z-index:2000" id="loading">
			<div class="loading"></div>  
		  </div>
			
<!-- yangqi     -->
 <div id="searchCondition">
 	<input type="hidden" id="page" value="1"/>
    <input type="hidden" id="equipmentNameHide" value=""/>
 	<input type="hidden" id="equipmentGroupHide" value=""/>
 	<input type="hidden" id="equipmentSubGroupHide" value=""/>
 	<input type="hidden" id="equipmentLevelHide" value=""/> 
 </div>		
   


      
		<c:import url="/WEB-INF/views/common/commonScript.jsp"></c:import>  

	    <script type="text/javascript" src="/js/identification/equipment/equipment.js" charset="UTF-8"></script>
	    <script type="text/javascript" src="/js/identification/equipment/equipmentAdd.js" charset="UTF-8"></script>	    	    
	    <script type="text/javascript">
	    identification.equipment = new Identification.equipment.List();

	    function goPage() {
	    	identification.equipment.searchList();
		}
	    
	    function goDel(equipmentNo) {
	    	 identification.equipment.goDel(equipmentNo);
		}
	    
      </script>

	  </body>
</html>

