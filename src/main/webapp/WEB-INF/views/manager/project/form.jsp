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
	<acme:input-textbox code="manager.project.form.label.code" path="code" readonly="${published}"/>
	<acme:input-textbox code="manager.project.form.label.title" path="title" readonly="${published}"/>
	<acme:input-textarea code="manager.project.form.label.summary" path="summary" readonly="${published}"/>
	<acme:input-double code="manager.project.form.label.cost" path="cost" readonly="${published}"/>
	<acme:input-checkbox code="manager.project.form.label.fatalErrors" path="fatalErrors" readonly="${published}"/>
	<acme:input-url code="manager.project.form.label.url" path="url" readonly="${published}"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<acme:submit code="manager.project.form.button.publish.${published}" action="/manager/project/publish"/>
			<jstl:choose>
				<jstl:when test="${!published}">
					<acme:submit code="manager.project.form.button.update" action="/manager/project/update"/>
					<acme:submit code="manager.project.form.button.delete" action="/manager/project/delete"/>
				</jstl:when>
			</jstl:choose>
		</jstl:when>
	</jstl:choose>

</acme:form>