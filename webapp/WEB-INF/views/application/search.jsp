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
	  <div class="panel-heading"><spring:message code="lable.applicationNew.search.title"/></div>
		  <div class="panel-body"> 
            <fieldset>
              <legend> </legend>
              <div class="form-group">
                 <label class="col-md-1 control-label" for="requireData"><spring:message code="lable.applicationNew.search.date"/></label>
                 <div class="col-md-2">
                   <span class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="requireData" data-link-format="yyyy-mm-dd">
                    <input class="form-control" type="text" value="" readonly="readonly">
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </span>
				     <input type="hidden" id="requireData" value="" />                
                 </div>
                 <label class="col-md-1 control-label" for="requireCompany"><spring:message code="lable.applicationNew.search.company"/></label>
                 <div class="col-md-2">                
					<input type="text" id="requireCompany" class="form-control" name="requireCompany">
                 </div>
                 
                 <label class="col-md-2 control-label" for="equipmentName"><spring:message code="lable.applicationNew.search.equipment.name"/></label>
                 <div class="col-md-2">                
                   <input type="text" id="equipmentName" class="form-control input-xlarge" name="equipmentName">
                 </div>  
                 
                  <div class="col-md-2">                
      				<button type="button" class="btn btn-primary btnSize" id="applicationSearchBtn"><spring:message code="button.applicationNew.search"/></button>
                 </div>                                
              </div>
           </fieldset>
        		  
		  
		  
           <fieldset Style="margin-top:10px">
              <legend> </legend>
           
              <div class="form-group">
                 <div class="col-md-*">
                      <span class="btn btn-primary  btnSize fileinput-button">
				        <i class="glyphicon glyphicon-plus"></i>
				        <span><spring:message code="button.applicationNew.import"/></span>
				        <!-- The file input field used as target for the file upload widget -->
				         <input id="applicationImoprtBtn"  type="file" name="file" multiple>
				       </span>  
				       
				     <div style="display:inline-block;float:right;">  
     				<select style="height:30px" id="orderType" > 
      					<option value="1"><spring:message code="option.applicationNew.asc.date"/></option>
      					<option value="2"><spring:message code="option.applicationNew.asc.company"/></option>
      					<option value="3"><spring:message code="option.applicationNew.asc.equipment.name"/></option>
      					<option value="4"><spring:message code="option.applicationNew.desc.date"/></option>
      					<option value="5"><spring:message code="option.applicationNew.desc.company"/></option>
      					<option value="6"><spring:message code="option.applicationNew.desc.equipment.name"/></option>
      				</select>
       				
       				<button type="button" class="btn btn-primary btnSize" id="applicationExportBtn"><spring:message code="button.applicationNew.export"/></button>
      				</div>
                 </div>
                                 
              </div>
           </fieldset>
           
       		  
	       	<div id="applicationResultList">
	       	<c:import url="/WEB-INF/views/application/list.jsp" charEncoding="UTF-8"></c:import>  
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
 
 <div class="modal fade" id="applicationUpdateModal" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true" style="display:none">
      	<div class="modal-dialog" style="width:800px">
      		<form method="post">
      			<div class="modal-content">
      				<div class="modal-header">
      					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        		<h4 class="modal-title" id="detailModalLabel"><spring:message code="lable.applicationNew.update.title"/></h4>
      				</div>
      				<div class="panel-body"> 
      				<div class="form-group">
					<div class="row" style="margin-top:10px">
					    <div class="row" style="margin-top:10px">
					      	<label class="col-md-2 control-label" for="updateApplicationDate"><spring:message code="lable.applicationNew.update.date"/></label>
					      <div class="col-md-3">	
							<span  class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="updateApplicationDate" data-link-format="yyyy-mm-dd">
		                    <input id="updateApplicationDateTxt" class="form-control" type="text" value="" readonly="readonly">
							<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                    </span>
						     <input type="hidden" id="updateApplicationDate" value="" name="updateApplicationDate"/>               
					      
					      </div>
					      
						  <label class="col-md-2 control-label" for="updateApplicationRepairer"><spring:message code="lable.applicationNew.update.company"/></label>
					      <div class="col-md-3">
					      	<input type="text" id="updateApplicationRepairer" name="updateApplicationRepairer"/>
						  </div>
						 </div>
					    <div class="row" style="margin-top:10px">							  
						 <label class="col-md-2 control-label" for="updateEquipmentManager"><spring:message code="lable.applicationNew.update.manage.company"/></label>							  
					      <div class="col-md-3">
					      	<input type="text" id="updateEquipmentManager" name="updateEquipmentManager"/>
						  </div>
						  
						  <label class="col-md-2 control-label" for="updateEquimentGroup"><spring:message code="lable.applicationNew.update.equipment.group"/></label>
						  <div class="col-md-3">
					      	<input type="text" id="updateEquimentGroup" name="updateEquimentGroup"/>
						  </div>
                             </div>
                           <div class="row" style="margin-top:10px">
						  <label class="col-md-2 control-label" for="updateRepairerLevel"><spring:message code="lable.applicationNew.update.level"/></label>
						  <div class="col-md-3">
                             <select  class="form-control" id="updateRepairerLevel" name="updateRepairerLevel">
							 <option selected value=""></option>				
								  <option value="大修"><spring:message code="option.applicationNew.update.major.repair"/></option>
								  <option value="中修"><spring:message code="option.applicationNew.update.middle.repair"/></option>
								  <option value="小修"><spring:message code="option.applicationNew.update.small.repair"/></option>
								  <option value="检修"><spring:message code="option.applicationNew.update.check.repair"/></option>
							</select>					      	
					      	
						  </div>

                          <label class="col-md-2 control-label" for="updateEquimentName"><spring:message code="lable.applicationNew.update.equipment.name"/></label>
						  <div class="col-md-3">
					      	<input type="text" id="updateEquimentName" name="updateEquimentName"/>
						  </div>
						  </div>
						  <div class="row" style="margin-top:10px">
						  
						 <label class="col-md-2 control-label" for="updateRepairerHistory"><spring:message code="lable.applicationNew.update.history"/></label>							  
						  <div class="col-md-3">
					      	<textarea rows="5" cols="68" id="updateRepairerHistory"></textarea>
						  </div>
						  </div>
						  <div class="row" style="margin-top:10px">
						  
						 <label class="col-md-2 control-label" for="updateRepairerHistory"><spring:message code="lable.applicationNew.update.remark"/></label>							  
						  <div class="col-md-3">
					      	<input type="text" id="updateRemark" name="updateRemark"/>
						  </div>
						  </div>
						  								  
						</div>
					</div>
					<input type="hidden" id="updateApplicationId" value=""/>
					<button style="float:right" id="updateApplicationBtn" class="btn btn-primary btn-sm" type="button"  onclick="identification.application.updatelicationUpdate()"><spring:message code="button.applicationNew.update.ok"/></button>
				  </div>
				</div>
				</div>
			</form>
		</div>
	  </div>
 
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

