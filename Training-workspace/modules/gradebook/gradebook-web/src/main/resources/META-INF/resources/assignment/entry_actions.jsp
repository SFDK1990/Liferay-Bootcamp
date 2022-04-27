<%@ include file="/init.jsp"%>

 <c:set var="assignment" value="${SEARCH_CONTAINER_RESULT_ROW.object}" />

 <liferay-ui:icon-menu markupView="lexicon">

     <%-- View action. --%>

     <c:if test="${assignmentPermission.contains(permissionChecker, assignment.assignmentId, 'VIEW' )}">
         <portlet:renderURL var="viewAssignmentURL">
             <portlet:param name="mvcRenderCommandName"
                 value="<%=MVCCommandNames.VIEW_ASSIGNMENT %>" />
             <portlet:param name="redirect" value="${currentURL}" />
             <portlet:param name="assignmentId" value="${assignment.assignmentId}" />
         </portlet:renderURL>

         <liferay-ui:icon message="view" url="${viewAssignmentURL}" />
     </c:if>

     <%-- Edit action. --%>

     <c:if test="${assignmentPermission.contains(permissionChecker, assignment.assignmentId, 'UPDATE' )}">

         <portlet:renderURL var="editAssignmentURL">
             <portlet:param name="mvcRenderCommandName"
                 value="<%=MVCCommandNames.EDIT_ASSIGNMENT %>" />
             <portlet:param name="redirect" value="${currentURL}" />
             <portlet:param name="assignmentId" value="${assignment.assignmentId}" />
         </portlet:renderURL>

         <liferay-ui:icon message="edit" url="${editAssignmentURL}" />    
     </c:if>

     <%-- Permissions action. --%>

     <c:if test="${assignmentPermission.contains(permissionChecker, assignment.assignmentId, 'PERMISSIONS')}">

         <liferay-security:permissionsURL
             modelResource="com.liferay.training.gradebook.model.Assignment"
             modelResourceDescription="${assignment.getTitle(locale)}"
             resourcePrimKey="${assignment.assignmentId}" 
             var="permissionsURL" 
         />

         <liferay-ui:icon message="permissions" url="${permissionsURL}" />
     </c:if>

     <%-- Delete action. --%>

     <c:if test="${assignmentPermission.contains(permissionChecker, assignment.assignmentId, 'DELETE')}">

         <portlet:actionURL name="<%=MVCCommandNames.DELETE_ASSIGNMENT %>" var="deleteAssignmentURL">
             <portlet:param name="redirect" value="${currentURL}" />
             <portlet:param name="assignmentId" value="${assignment.assignmentId}" />
         </portlet:actionURL>

         <liferay-ui:icon-delete url="${deleteAssignmentURL}" />
     </c:if>
 </liferay-ui:icon-menu>