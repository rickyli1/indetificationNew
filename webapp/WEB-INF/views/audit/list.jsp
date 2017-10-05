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
	                <th><spring:message code="lable.audit.list.date"/></th>
	                <th><spring:message code="lable.audit.list.company"/></th>
	                <th><spring:message code="lable.audit.list.equiment.manager"/></th>
	                <th><spring:message code="lable.audit.list.group"/></th>
	                <th><spring:message code="lable.audit.list.equiment.name"/></th>
	                <th><spring:message code="lable.audit.list.repair.level"/></th>
	                <th><spring:message code="lable.audit.list.history"/></th>
	                <th><spring:message code="lable.audit.list.success.work"/></th>
	                <th colspan="2"><spring:message code="lable.audit.list.repair.level"/></th>
	                <th><spring:message code="lable.audit.list.company.result"/></th>
	                <th><spring:message code="lable.audit.list.remark"/></th>
	                <th><spring:message code="lable.audit.list.opration"/></th>
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
		                <td>
		                <input type="radio" <c:if test="${application.haveSuccesWwork == '有'}">checked="checked"</c:if> name="haveSuccesWwork${application.applicationId}" value="有"><spring:message code="option.audit.list.have.success.work"/></<input><br>
		                <input type="radio"  <c:if test="${application.haveSuccesWwork == '无'}">checked="checked"</c:if> name="haveSuccesWwork${application.applicationId}" value="无"><spring:message code="option.audit.list.no.have.success.work"/></<input>
		                </td>
		                <c:if test="${application.areaRepairInfos.size() == 0 }">
		                   <td><spring:message code="option.audit.list.no.have.success.work"/></td>
		                </c:if>
		                
		                <c:if test="${application.areaRepairInfos.size() > 0 }">
		                <td>
		                <c:forEach var="info" items="${application.areaRepairInfos}" varStatus="status">
		                	${info}<br>
		                </c:forEach>
		                </td>
		                </c:if>
		                
		                <td>
		                <spring:message code="option.audit.list.result"/><br>
		                <input type="radio" <c:if test="${application.areaHaveAbility == '具备'}"> checked="checked"</c:if>name="areaHaveAbility${application.applicationId}" value="具备"><spring:message code="option.audit.list.have.ability"/></input><br>
		                <input type="radio" <c:if test="${application.areaHaveAbility == '基本具备'}">checked="checked"</c:if> name="areaHaveAbility${application.applicationId}" value="基本具备"><spring:message code="option.audit.list.base.ability"/></input><br>
		                <input type="radio" <c:if test="${application.areaHaveAbility == '不具备'}">checked="checked"</c:if> name="areaHaveAbility${application.applicationId}"value="不具备"><spring:message code="option.audit.list.no.ability"/></input>
		                </td>
		                <td>
		                 <input type="radio" <c:if test="${application.orginizationResult == '大修'}"> checked="checked"</c:if>name="orginizationResult${application.applicationId}" value="大修"><spring:message code="option.audit.list.major.repair"/>大修</input><br>
		                 <input type="radio" <c:if test="${application.orginizationResult == '中修'}"> checked="checked"</c:if>name="orginizationResult${application.applicationId}" value="中修"><spring:message code="option.audit.list.middle.repair"/>中修</input><br>
		                 <input type="radio" <c:if test="${application.orginizationResult == '小修'}"> checked="checked"</c:if>name="orginizationResult${application.applicationId}" value="小修"><spring:message code="option.audit.list.small.repair"/>小修</input><br>
		                 <input type="radio" <c:if test="${application.orginizationResult == '检修'}"> checked="checked"</c:if>name="orginizationResult${application.applicationId}" value="检修"><spring:message code="option.audit.list.check.repair"/>检修</input><br>
		                 <input type="radio" <c:if test="${application.orginizationResult == '否'}"> checked="checked"</c:if>name="orginizationResult${application.applicationId}" value="否"><spring:message code="option.audit.list.no.repair"/>否</input>
		                
		                </td>
		                <td>
		                <textarea id="remark${application.applicationId}" rows="3" cols="20">${fn:trim(application.remark)}</textarea>
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
      
      
      