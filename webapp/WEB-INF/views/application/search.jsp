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
                   <span class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="requireData" data-link-format="yyyy-mm-dd">
                    <input class="form-control" type="text" value="" readonly="readonly">
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </span>
				     <input type="hidden" id="requireData" value="" />                
                 </div>
                 <label class="col-md-1 control-label" for="requireCompany">申请单位</label>
                 <div class="col-md-2">                
					<input type="text" id="requireCompany" class="form-control" name="requireCompany">
                 </div>
                 
                 <label class="col-md-2 control-label" for="equipmentName">申请ZB型号名称</label>
                 <div class="col-md-2">                
                   <input type="text" id="equipmentName" class="form-control input-xlarge" name="equipmentName">
                 </div>                 
              </div>
           </fieldset>
        		  
		  
		  
           <fieldset Style="margin-top:10px">
              <div class="form-group">
                 <div class="col-md-4">
      				<button type="button" class="btn btn-primary btn-sm" id="applicationSearchBtn">查询</button>
      				
                    <span class="btn btn-primary  btn-sm fileinput-button">
				        <i class="glyphicon glyphicon-plus"></i>
				        <span>导入</span>
				        <!-- The file input field used as target for the file upload widget -->
				         <input id="applicationImoprtBtn"  type="file" name="file" multiple>
				    </span>  
      				<button type="button" class="btn btn-primary btn-sm" id="applicationExportBtn">导出</button>
      				
      				<select style="height:30px" id="orderType"> 
      					<option value="1">升序+申请日期</option>
      					<option value="2">升序+申请单位</option>
      					<option value="3">升序+申请ZB型号名称</option>
      					<option value="4">降序+申请日期</option>
      					<option value="5">降序+申请单位</option>
      					<option value="6">降序+申请ZB型号名称</option>
      				</select>
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
 
 <div class="modal fade" id="applicationUpdateModal" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true" style="display:none">
      	<div class="modal-dialog" style="width:800px">
      		<form method="post">
      			<div class="modal-content">
      				<div class="modal-header">
      					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        		<h4 class="modal-title" id="detailModalLabel">认可申请修改</h4>
      				</div>
      				<div class="panel-body"> 
      				<div class="form-group">
					<div class="row" style="margin-top:10px">
					    <div class="row" style="margin-top:10px">
					      	<label class="col-md-2 control-label" for="updateApplicationDate">申请日期</label>
					      <div class="col-md-3">	
					      	<input type="text" id="updateApplicationDate" name="updateApplicationDate"/>
					      </div>
					      
						  <label class="col-md-2 control-label" for="updateApplicationRepairer">申请单位</label>
					      <div class="col-md-3">
					      	<input type="text" id="updateApplicationRepairer" name="updateApplicationRepairer"/>
						  </div>
						 </div>
					    <div class="row" style="margin-top:10px">							  
						 <label class="col-md-2 control-label" for="updateEquipmentManager">装备管理机关</label>							  
					      <div class="col-md-3">
					      	<input type="text" id="updateEquipmentManager" name="updateEquipmentManager"/>
						  </div>
						  
						  <label class="col-md-2 control-label" for="updateEquimentGroup">备装专业</label>
						  <div class="col-md-3">
					      	<input type="text" id="updateEquimentGroup" name="updateEquimentGroup"/>
						  </div>
                             </div>
                           <div class="row" style="margin-top:10px">
						  <label class="col-md-2 control-label" for="updateRepairerLevel">申请级别</label>
						  <div class="col-md-3">
					      	<input type="text" id="updateRepairerLevel" name="updateRepairerLevel"/>
						  </div>

                          <label class="col-md-2 control-label" for="updateEquimentName">申请ZB型号名称</label>
						  <div class="col-md-3">
					      	<input type="text" id="updateEquimentName" name="updateEquimentName"/>
						  </div>
						  </div>
						  <div class="row" style="margin-top:10px">
						  
						 <label class="col-md-2 control-label" for="updateRepairerHistory">申请经历</label>							  
						  <div class="col-md-3">
					      	<textarea rows="5" cols="68" id="updateRepairerHistory"></textarea>
						  </div>
						  </div>
						  <div class="row" style="margin-top:10px">
						  
						 <label class="col-md-2 control-label" for="updateRepairerHistory">备注</label>							  
						  <div class="col-md-3">
					      	<input type="text" id="updateRemark" name="updateRemark"/>
						  </div>
						  </div>
						  								  
						</div>
					</div>
					<input type="hidden" id="updateApplicationId" value=""/>
					<button style="float:right" id="updateApplicationBtn" class="btn btn-primary btn-sm" type="button"  onclick="identification.application.updatelicationUpdate()">确认</button>
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

