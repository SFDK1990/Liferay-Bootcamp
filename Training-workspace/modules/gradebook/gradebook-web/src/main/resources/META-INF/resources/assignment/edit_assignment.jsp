
<%@ include file="../init.jsp"%>

<liferay-ui:error key="serviceErrorDetails">
	<liferay-ui:message key="error.assignment-service-error"
		arguments='<%=SessionErrors.get(liferayPortletRequest, "serviceErrorDetails")%>' />
</liferay-ui:error>
<liferay-ui:error key="assignmentTitleEmpty"
	message="error.assignment-title-empty" />
<liferay-ui:error key="assignmentDescriptionEmpty"
	message="error.assignment-description-empty" />

<%-- Generate add / edit action URL and set title. --%>

<c:choose>
	<c:when test="${not empty assignment}">
		<portlet:actionURL var="assignmentActionURL"
			name="<%=MVCCommandNames.EDIT_ASSIGNMENT%>">
			<portlet:param name="redirect" value="${param.redirect}" />
		</portlet:actionURL>

		<c:set var="editTitle" value="edit-assignment" />
	</c:when>
	<c:otherwise>
		<portlet:actionURL var="assignmentActionURL"
			name="<%=MVCCommandNames.ADD_ASSIGNMENT%>">
			<portlet:param name="redirect" value="${param.redirect}" />
		</portlet:actionURL>

		<c:set var="editTitle" value="add-assignment" />
	</c:otherwise>
</c:choose>

<div class="container-fluid-1280 edit-assignment">

	<h1>
		<liferay-ui:message key="${editTitle}" />
	</h1>

	<aui:model-context bean="${assignment}" model="${assignmentClass}" />

	<aui:form action="${assignmentActionURL}" name="fm">

		<aui:input name="assignmentId" field="assignmentId" type="hidden" />

		<aui:fieldset-group markupView="lexicon">

			<aui:fieldset>

				<%-- Title field. --%>

				<aui:input name="title">
				
				 <aui:validator name="required" />

				 <aui:validator errorMessage="error.assignment-title-format" name="custom">
				     function(val, fieldNode, ruleValue) {
				         var wordExpression = 
				             new RegExp("^[^\\[\\]\\^$<>]*$");
				
				         return wordExpression.test(val);
				     }
				 </aui:validator>

				</aui:input>

				<%-- Description field. --%>

				<aui:input name="description">
					<aui:validator name="required" />
				</aui:input>

				<%-- Due date field. --%>

				<aui:input name="dueDate">
					<aui:validator name="required" />
				</aui:input>
			</aui:fieldset>
		</aui:fieldset-group>

		<%--Buttons. --%>

		<aui:button-row>
			<aui:button cssClass="btn btn-primary" type="submit" />
			<aui:button cssClass="btn btn-secondary" onClick="${param.redirect}"
				type="cancel" />
		</aui:button-row>
	</aui:form>

</div>