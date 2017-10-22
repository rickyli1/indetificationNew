<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>      
   
      	 <div style="padding-top: 24px;">
	         <table class="table table-bordered table-striped">
	            <thead>
	              <tr>
	                <th>No</th>
	                <th>序列号</th>
	                <th><spring:message code="lable.equipment.group"/></th>
	                <th><spring:message code="lable.equipment.subGroup"/></th>
	                <th><spring:message code="lable.equipment.equipmentName"/></th>
	                <th><spring:message code="lable.equipment.modifyCategory"/></th>
	                <th><spring:message code="lable.equipment.modifyCompany"/></th>
	                <th><spring:message code="lable.equipment.limit"/></th>
	                <th><spring:message code="lable.equipment.remarks"/></th>
	                <th>操作</th>
	              </tr>
	            </thead>
	            <tbody>
	            	<c:forEach var="apply" items="${equipments}" varStatus="status">
	               		<tr>
							<td>${status.count+(page-1)*pageSize}</td>
			                <td>${apply.equipmentNo}</td>
			                <td>${apply.equipmentGroup}</td>
			                <td>${apply.equipmentSubGroup}</td>
			                <td>${apply.equipmentName}</td>
							<td>${apply.equipmentLevel}</td>
			                <td>${apply.equipmentCompany}</td>
			                <td>${apply.equipmentLimit}</td>
			                <td>${apply.remark}</td>
			                <td>
						    	<button id="detailBtn" class="btn btn-primary btn-sm" type="button" data-toggle="modal" data-target="#detailModal" onclick="goEquipmentUpdate('${apply.equipmentId}')"><spring:message code="lable.equipment.equipmentUpdate"/></button>			                
			                	<button id="deleteBtn" class="btn btn-primary btn-sm" type="button"   onclick="goDel('${apply.equipmentId}')"><spring:message code="lable.equipment.equipmentDelete"/></button>
			                </td>
		                </tr>
	            	</c:forEach>
	            </tbody>
	         </table>
         </div>
		
          <div class="text-center">
                <ul id="pagination-demo" class="pagination-sm pagination">
               </ul>
          </div>		
		<script src="/js/common/jquery/jquery-1.11.1.min.js"></script>          
		<script type="text/javascript" src="/js/common/jquery/jquery.twbsPagination.min.js" charset="UTF-8"></script>
		
		<script type="text/javascript">
	
		$('#pagination-demo').twbsPagination({
		    totalPages: '${totalPage}',
		    startPage: parseInt('${page}', 10),
		    visiblePages: 10,
		    onPageClick: function (event, page) {
		    	$("#page").val(page);
			    event.preventDefault();
		    	goPage(page);
		    }
		});
		
	 
	    
	    function goEquipmentUpdate(equipmentNo) {
			//window.open("/equipment/updateInit/"+equipmentNo);
			identification.add = new Identification.equipment.Add();
	    	identification.add.goDetail(equipmentNo);
		}
	   </script>
      
      
      