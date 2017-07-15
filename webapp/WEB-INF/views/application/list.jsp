<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>      
 <%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  	 
  
      	 <div style="padding-top: 10px;">
	         <table class="table table-bordered table-striped">
	            <thead>
	              <tr>
	                <th>申请日期</th>
	                <th>申请单位</th>
	                <th>装备管理机关</th>
	                <th>申请装备专业</th>
	                <th>申请装备型号名称</th>
	                <th>申请级别</th>
	                <th>申请经历</th>
	                <th>备注</th>
	              </tr>
	            </thead>
	            	
	            <tbody>
	            	<c:forEach var="application" items="${applications}" varStatus="status">
		               <tr>
		                <td>${application.applicationDate}</td>
		                <td>${application.applicationRepairer}</td>
		                <td>${application.equipmentManager}</td>
		                <td>${application.equimentGroup}</td>
		                <td>${application.equimentName}</td>
		                <td>${application.repairerLevel}</td>
		                <td>${application.repairerHistory}</td>
		                <td>${application.remark}</td>
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
      
      
      