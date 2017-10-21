<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>   
<div class="modal-body">   
<div class="container-fluid">
<div class="row">
	<div class="col-md-* col-xs-* col-sm-*">
	    <div class="row">
	      <div class="panel-body"> 
	           <fieldset>
	              <div class="form-group">
	                 <label class="col-md-1 control-label"  style="width:100px;" for="dequipmentName"><spring:message code="lable.equipment.equipmentName"/></label>
	                 <div class="col-md-2" style="width:180px;">
	                    <input type="text" class="form-control" id="dequipmentName">
	                    <input type="hidden" id="dequipmentId" type="text" />
	                 </div>
	                 
	                 <label class="col-md-1 control-label" for="dgroupNo"  style="width:100px;"><spring:message code="lable.equipment.group"/></label>
	                 <div class="col-md-2" style="width:180px;">                
						<input type="text" class="form-control" id="dgroupNo">
	                 </div>
	                 
	                 <label class="col-md-1 control-label" for="dsubGroupNo" style="width:100px;"><spring:message code="lable.equipment.subGroup"/></label>
	                 <div class="col-md-2" style="width:180px;">                
						<input type="text" class="form-control" id="dsubGroupNo">
	                 </div>   
	              </div>
	           </fieldset>
	           <fieldset style="margin-top:10px">
	              <div class="form-group">  
	                 <label class="col-md-1 control-label" for="dequipmentCompany" style="width:100px;"><spring:message code="lable.equipment.update.choose.company"/></label>
	                 <div class="col-md-2"  style="width:180px;">
	                    <input type="text" class="form-control" id="dequipmentCompany">
	                 </div>
	                 <label class="col-md-1 control-label" for="dlevel" style="width:100px;"><spring:message code="lable.equipment.update.repair.level"/></label>
	                 <div class="col-md-2"  style="width:180px;">
	                      <select  class="form-control" id="dequipmentLevel">
							 <option selected value=""></option>				
								  <option value="大修"><spring:message code="option.equipment.update.major.repair"/></option>
								  <option value="中修"><spring:message code="option.equipment.update.middle.repair"/></option>
								  <option value="小修"><spring:message code="option.equipment.update.small.repair"/></option>
								  <option value="检修"><spring:message code="option.equipment.update.check.repair"/></option>
							</select>
	                 </div>
	                 
	                 <label class="col-md-1 control-label" for="dlimit" style="width:100px;"><spring:message code="lable.equipment.limit"/></label>
	                 <div class="col-md-2"  style="width:180px;">
	                    <input type="text" class="form-control" id="dlimit">
	                 </div>
	                 
	              </div>
	              
	           </fieldset>
	           <fieldset style="margin-top:10px">
                 <div class="form-group">  
	                 <label class="col-md-1 control-label" for="dremark" style="width:100px;"><spring:message code="lable.equipment.remarks"/></label>
	                 <div class="col-md-2"  style="width:180px;">
	                    <input type="text" class="form-control" id="dremark">
	                 </div>
                 </div> 
	           </fieldset>
	           
	           <fieldset style="margin-top:10px">
	              <div class="form-group">
	                  <div class="col-md-1">
	                  <button type="button" class="btn btn-primary btn-sm" id="updateEquipmentBtn"><spring:message code="lable.equipment.equipmentSave"/></button>
	                  </div>                  
	               </div>  
	            </fieldset>       
		  </div>
		  
	    </div>
	</div>
</div>

		
		
