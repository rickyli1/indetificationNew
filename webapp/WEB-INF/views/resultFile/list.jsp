<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>      
 <%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %> 
   
      	 <div style="padding-top: 10px;">
	         <table class="table table-bordered table-striped">
	            <thead>
	              <tr>
	                <th><spring:message code="lable.resultFile.list.no"/></th>
	                <th><spring:message code="lable.resultFile.list.company"/></th>
	                <th><spring:message code="lable.resultFile.list.group"/></th>
	                <th><spring:message code="lable.resultFile.list.date"/></th>
	                <th><spring:message code="lable.resultFile.list.file"/></th>
	              </tr>
	            </thead>
	            	<c:forEach var="resultFile" items="${resultFiles}" varStatus="status">
		               <tr>
		                <td>${status.count+(page-1)*pageSize}</td>
		                <td>${resultFile.repairerName}</td>
		                <td>${resultFile.equimentGroup}</td>
		                <td>${resultFile.applicationDate}</td>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">  
                           <td><a href="fileDownload/${resultFile.mongoFileId}">${resultFile.mongoFileName}</a></td>
		                </sec:authorize>
						
                        <sec:authorize access="hasRole('ROLE_GENERAL')">  
                           <td>${resultFile.mongoFileName}</td>
		                </sec:authorize>
		               </tr>
	            	</c:forEach>
	            	
	            <tbody>
	            	
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
      
      
      