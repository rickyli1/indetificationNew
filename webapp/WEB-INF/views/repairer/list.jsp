<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>      
 <%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
   
      	 <div style="padding-top: 10px;">
	         <table class="table table-bordered table-striped">
	            <thead>
	              <tr>
	                <th>序号</th>
	                <th>辖区</th>
	                <th>修理单位名称</th>
	                <th>单位性质</th>
	              </tr>
	            </thead>
	            <tbody>
	            	<c:forEach var="apply" items="${applicationResultList}" varStatus="status">
		               <tr>
		                <td>${status.count+(page-1)*pageSize}</td>
		                <td>${apply.companyName}</td>
		                <td>${apply.equipmentName}</td>
		                <td>${apply.repairLevel}</td>
		               </tr>
	            	</c:forEach>
	            </tbody>
	         </table>
         </div>
		
          <div class="text-center">
                <ul id="pagination_repairerList" class="pagination-sm pagination">
               </ul>
          </div>
 <div id="listCondition">
 	<input type="hidden" id="applicationNoHidden" value=""/>
 </div>		
	
<script src="/js/common/jquery/jquery-1.11.1.min.js"></script>          
<script type="text/javascript" src="/js/common/jquery/jquery.twbsPagination.min.js" charset="UTF-8"></script>

<script type="text/javascript">
	
	
 </script>
      
      
      