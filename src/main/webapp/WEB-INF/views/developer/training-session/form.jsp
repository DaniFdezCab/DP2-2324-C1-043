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
	<acme:input-textbox code="developer.training-session.form.label.code" path="code"/>
	<acme:input-textarea code="developer.training-session.form.label.location" path="location"/>
	<acme:input-textbox code="developer.training-session.form.label.instructor" path="instructor"/>
	<acme:input-moment code="developer.training-session.form.label.startMoment" path="startMoment"/>	
	<acme:input-moment code="developer.training-session.form.label.endMoment" path="endMoment"/>	
	<acme:input-url code="developer.training-session.form.label.link" path="link"/>
	<acme:input-email code="developer.training-session.form.label.email" path="email"/>
	<acme:input-textbox code="developer.training-session.form.label.notPublished" path="notPublished" readonly="true"/>
	
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') &&  notPublished == true }">
			<acme:submit code="developer.training-session.form.button.update" action="/developer/training-session/update"/>
			<acme:submit code="developer.training-session.form.button.delete" action="/developer/training-session/delete"/>
			<acme:submit code="developer.training-session.form.button.publish" action="/developer/training-session/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.training-session.form.button.create" action="/developer/training-session/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>

