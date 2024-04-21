<%--
- formAdd.jsp
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
	<acme:input-select code="manager.associationProject.form.label.title" choices="${userStories}" path="userStory" readonly="${published}"/>

	<jstl:choose>
		<jstl:when test="${_command == 'add'}">
			<acme:submit code="manager.associationProject.form.button.add" action="/manager/association-project/add?projectId=${projectId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
    		<acme:button code="manager.userStory.list.button.create-form" action="/manager/user-story/create?projectId=${projectId}"/>
		</jstl:when>
	</jstl:choose>

</acme:form>