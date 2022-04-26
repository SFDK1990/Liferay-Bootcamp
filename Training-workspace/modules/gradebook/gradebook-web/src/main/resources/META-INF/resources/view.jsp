<%@ include file="./init.jsp"%>

<liferay-ui:error key="serviceErrorDetails">
	<liferay-ui:message
		arguments='<%=SessionErrors.get(liferayPortletRequest, "serviceErrorDetails")%>'
		key="error.assignment-service-error" />
</liferay-ui:error>
<liferay-ui:success key="assignmentAdded"
	message="assignment-added-successfully" />
<liferay-ui:success key="assignmentUpdated"
	message="assignment-updated-successfully" />
<liferay-ui:success key="assignmentDeleted"
	message="assignment-deleted-successfully" />

<p>
	<b><liferay-ui:message key="gradebook.caption" /></b>
</p>

<div class="container-fluid-1280">

	<h1>
		<liferay-ui:message key="assignments" />
	</h1>

	<%-- Clay management toolbar. --%>

	<clay:management-toolbar disabled="${assignmentCount eq 0}"
		displayContext="${assignmentsManagementToolbarDisplayContext}"
		itemsTotal="${assignmentCount}" searchContainerId="assignmentEntries"
		selectable="false" />

	<%-- Search container. --%>

	<liferay-ui:search-container emptyResultsMessage="no-assignments"
		id="assignmentEntries" iteratorURL="${portletURL}"
		total="${assignmentCount}">

		<liferay-ui:search-container-results results="${assignments}" />

		<liferay-ui:search-container-row
			className="com.liferay.training.gradebook.model.Assignment"
			modelVar="entry">

			<%@ include file="/assignment/entry_search_columns.jspf"%>

		</liferay-ui:search-container-row>

		<%-- Iterator / Paging --%>

		<liferay-ui:search-iterator
			displayStyle="${assignmentsManagementToolbarDisplayContext.getDisplayStyle()}"
			markupView="lexicon" />
	</liferay-ui:search-container>
</div>