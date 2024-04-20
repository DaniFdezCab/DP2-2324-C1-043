<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%> 

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:form>
	<acme:input-textbox code="manager.userStory.form.label.title" path="title" readonly="${published}"/>
	<acme:input-textarea code="manager.userStory.form.label.description" path="description" readonly="${published}"/>
	<acme:input-textarea code="manager.userStory.form.label.acceptanceCriteria" path="acceptanceCriteria" readonly="${published}"/>
	<acme:input-integer code="manager.userStory.form.label.estimatedCost" path="estimatedCost" readonly="${published}"/>
	<acme:input-textbox code="manager.userStory.form.label.priority" path="priority" readonly="${published}"/>
	<acme:input-url code="manager.userStory.form.label.url" path="url" readonly="${published}"/>
	
	
	<jstl:choose>
		<jstl:when test="${published}">
			<acme:button code="manager.userStory.form.button.userStory" action="/manager/userStory/list?projectId=${id}"/>
			<acme:submit code="manager.userStory.form.button.publish.${published}" action="/manager/userStory/publish"/>
		</jstl:when>
		<jstl:when test="${!published && acme:anyOf(_command, 'show|update|delete|publish')}">
			<acme:button code="manager.userStory.form.button.userStory" action="/manager/userStory/list?projectId=${id}"/>	
			<acme:submit code="manager.userStory.form.button.update" action="/manager/project/update"/>
			<acme:submit code="manager.userStory.form.button.delete" action="/manager/project/delete"/>
			<acme:submit code="manager.userStory.form.button.publish.${published}" action="/manager/project/publish"/>			
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.project.form.button.create" action="/manager/project/create"/>
		</jstl:when>
	</jstl:choose>

</acme:form>