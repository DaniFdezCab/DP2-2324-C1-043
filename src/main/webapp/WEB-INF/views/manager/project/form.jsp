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
	<acme:input-textbox code="manager.project.list.label.code" path="code" readonly="true" />
	<acme:input-textbox code="manager.project.list.label.title" path="title" readonly="true" />
	<acme:input-textarea code="manager.project.list.label.summary" path="summary" readonly="true" />
	<acme:input-double code="manager.project.list.label.cost" path="cost" readonly="true" />
	<acme:input-url code="manager.project.list.label.url" path="url"  readonly="true" />
	<acme:submit
		test="${acme:anyOf(_command, 'show|update') && !acme:anyOf(status, 'ACCEPTED|REJECTED')}"
		code="manager.application.form.button.update"
		action="/manager/project/update" />
</acme:form>