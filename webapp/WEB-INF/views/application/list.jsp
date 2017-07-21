<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>      
 <%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  	 
  
      	 <div style="padding-top: 10px;">
	         <table class="table table-bordered table-striped">
	            <thead>
	              <tr>
	                <th style="width:9%">申请日期</th>
	                <th>申请单位</th>
	                <th>装备管理机关</th>
	                <th style="width:7%">备装专业</th>
	                <th>申请ZB型号名称</th>
	                <th style="width:7%">申请级别</th>
	                <th>申请经历</th>
	                <th Style="width:5%">备注</th>
	                <th style="width:11%">操作</th>
	              </tr>
	            </thead>
	            	
	            <tbody>
	            	<c:forEach var="application" items="${applications}" varStatus="status">
		               <tr>
		                <td id="applicationDate${application.applicationId}">${application.applicationDate}</td>
		                <td id="applicationRepairer${application.applicationId}">${application.applicationRepairer}</td>
		                <td id="equipmentManager${application.applicationId}">${application.equipmentManager}</td>
		                <td id="equimentGroup${application.applicationId}">${application.equimentGroup}</td>
		                <td id="equimentName${application.applicationId}">${application.equimentName}</td>
		                <td id="repairerLevel${application.applicationId}">${application.repairerLevel}</td>
		                <td  id="repairerHistory${application.applicationId}"class="history">${application.repairerHistory}</td>
		                <td id="remark${application.applicationId}">${application.remark}</td>
		                <td>
							<button id="applicationModifyBtn" class="btn btn-primary btn-sm" type="button"  data-toggle="modal" data-target="#applicationUpdateModal" onclick="identification.application.goApplicationUpdate('${application.applicationId}')">修改</button>
							<button id="applocationDelBtn" class="btn btn-primary btn-sm" type="button" onclick="identification.application.goApplicationDelete('${application.applicationId}')">删除</button>
		                </td>		              
		               </tr>
	            	</c:forEach>
	            </tbody>
	         </table>
         </div>
		
          <div class="text-center">
                <ul id="pagination_applicationList" class="pagination-sm pagination">
               </ul>
          </div>
 	
<script src="/js/common/jquery/jquery-1.11.1.min.js"></script>          
<script type="text/javascript" src="/js/common/jquery/jquery.twbsPagination.min.js" charset="UTF-8"></script>

<script type="text/javascript">
$('#pagination_applicationList').twbsPagination({
    totalPages: '${totalPage}',
    startPage:parseInt('${page}', 10),
    visiblePages: 10,
    onPageClick: function (event, page) {
    	$("#page").val(page);
	    event.preventDefault();
    	goPage(page);
    }
});	
	
 </script>
      
      
      