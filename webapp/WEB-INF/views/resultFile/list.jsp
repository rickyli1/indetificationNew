<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>      
 <%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
   
      	 <div style="padding-top: 10px;">
	         <table class="table table-bordered table-striped">
	            <thead>
	              <tr>
	                <th>序号</th>
	                <th>申请单位</th>
	                <th>申请专业</th>
	                <th>申请日期</th>
	                <th>结论文件</th>
	                <th>操作</th>
	              </tr>
	            </thead>
	            	<c:forEach var="resultFile" items="${resultFiles}" varStatus="status">
		               <tr>
		                <td>${status.count+(page-1)*pageSize}</td>
		                <td>${resultFile.repairerName}</td>
		                <td>${resultFile.equimentGroup}</td>
		                <td>${resultFile.applicationDate}</td>
		                <td><a href="fileDownload/${resultFile.mongoFileId}">${resultFile.mongoFileName}</a></td>
		                <td>
						    <button id="previewBtn" class="btn btn-primary btn-sm" type="button" onclick="previewResultFile('${resultFile.mongoFileId}')">预览</button>		                
		                </td>
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

function previewResultFile(fileId) {
	
}
	
 </script>
      
      
      