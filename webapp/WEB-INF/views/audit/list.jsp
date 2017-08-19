<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
   
 <%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  	 
  
  <style type="text/css" mce_bogus="1">  
  table th{
            white-space: nowrap;
        }
        table td{
            white-space: nowrap;
        }
        
  table{
         empty-cells:show; 
         border-collapse: collapse;
         margin:0 auto;
        }
 
 
   </style>
   
    <div style="overflow: auto; width: 100%;margin-top:10px">
          <table class="table table-bordered table-striped">
	            <thead>
	              <tr>
	                <th>申请日期</th>
	                <th>申请单位</th>
	                <th>装备管理机关</th>
	                <th>备装专业</th>
	                <th>申请ZB型号名称</th>
	                <th>申请级别</th>
	                <th>申请经历</th>
	                <th>有无认可任务</th>
	                <th colspan="2">辖区</th>
	                <th>机关批复</th>
	                <th>备注</th>
	                <th>操作</th>
	              </tr>
	            </thead>
	            	
	            <tbody>
	            	<c:forEach var="application" items="${applications}" varStatus="status">
		               <tr>
		                <td id="applicationDate${application.applicationId}">${application.applicationDate}</td>
		                <td   id="applicationRepairer${application.applicationId}">${application.applicationRepairer}</td>
		                <td   id="equipmentManager${application.applicationId}">${application.equipmentManager}</td>
		                <td   id="equimentGroup${application.applicationId}">${application.equimentGroup}</td>
		                <td   id="equimentName${application.applicationId}">${application.equimentName}</td>
		                <td   id="repairerLevel${application.applicationId}">${application.repairerLevel}</td>
		                <td   id="repairerHistory${application.applicationId}"class="history">${application.repairerHistory}</td>
		                <td   id="haveSuccesWwork${application.applicationId}">
		                <input type="radio" <c:if test="${application.haveSuccesWwork == '有'}">checked="checked"</c:if> name="haveSuccesWwork${application.applicationId}" id="haveSuccesWwork${application.applicationId}Y">有</input>
		                <input type="radio"  <c:if test="${application.haveSuccesWwork == '无'}">checked="checked"</c:if> name="haveSuccesWwork${application.applicationId}" id="haveSuccesWwork${application.applicationId}N">无</input>
		                </td>
		                <c:if test="${application.areaRepairInfos.size() == 0 }">
		                   <td>无</td>
		                </c:if>
		                
		                <c:if test="${application.areaRepairInfos.size() > 0 }">
		                <td>
		                <c:forEach var="info" items="${application.areaRepairInfos}" varStatus="status">
		                	${info}<br>
		                </c:forEach>
		                </td>
		                </c:if>
		                
		                <td   id="areaHaveAbility${application.applicationId}">
		                结论<br>
		                <input type="radio" <c:if test="${application.areaHaveAbility == '具备'}"> checked="checked"</c:if>name="areaHaveAbility${application.applicationId}" id="haveSuccesWwork${application.applicationId}YY">具备</input><br>
		                <input type="radio" <c:if test="${application.areaHaveAbility == '基本具备'}">checked="checked"</c:if> name="areaHaveAbility${application.applicationId}" id="haveSuccesWwork${application.applicationId}Y">基本具备</input><br>
		                <input type="radio" <c:if test="${application.areaHaveAbility == '不具备'}">checked="checked"</c:if> name="areaHaveAbility${application.applicationId}" id="haveSuccesWwork${application.applicationId}N">不具备</input><br>
		                </td>
		                <td   id="orginizationResult${application.applicationId}">
		                ${application.orginizationResult}
		                 <input type="radio" <c:if test="${application.areaHaveAbility == '大修'}"> checked="checked"</c:if>name="orginizationResult${application.applicationId}" id="orginizationResult${application.applicationId}B">大修</input><br>
		                 <input type="radio" <c:if test="${application.areaHaveAbility == '中修'}"> checked="checked"</c:if>name="orginizationResult${application.applicationId}" id="orginizationResult${application.applicationId}M">中修</input><br>
		                 <input type="radio" <c:if test="${application.areaHaveAbility == '小修'}"> checked="checked"</c:if>name="orginizationResult${application.applicationId}" id="orginizationResult${application.applicationId}S">小修</input><br>
		                 <input type="radio" <c:if test="${application.areaHaveAbility == '检修'}"> checked="checked"</c:if>name="orginizationResult${application.applicationId}" id="orginizationResult${application.applicationId}C">检修</input><br>
		                 <input type="radio" <c:if test="${application.areaHaveAbility == '否'}"> checked="checked"</c:if>name="orginizationResult${application.applicationId}" id="orginizationResult${application.applicationId}N">否</input><br>
		                
		                </td>
		                <td   id="remark${application.applicationId}">
		                <textarea rows="3" cols="20">${fn:trim(application.remark)}</textarea>
		                </td>
		                <td>
							<button id="auditSaveBtn" class="btn btn-primary btn-sm" type="button"  data-toggle="modal"  onclick="identification.audit.auditSave('${application.applicationId}')">保存</button>
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
      
      
      