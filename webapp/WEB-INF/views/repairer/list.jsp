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
	            	<c:forEach var="repairer" items="${repairers}" varStatus="status">
		               <tr>
		                <td>${repairer.repairerNo}</td>
		                <td>${repairer.repairerArea}</td>
		                <td>${repairer.repairerName}</td>
		                <td>${repairer.repairerLevel}</td>
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
$('#pagination_repairerList').twbsPagination({
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
      
      
      