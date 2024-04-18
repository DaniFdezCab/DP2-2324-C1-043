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
	<acme:input-textbox code="sponsor.sponsorship.form.label.code" path="code" readonly="${published}"/>
	<acme:input-moment code="sponsor.sponsorship.form.label.moment" path="moment" readonly="${published}"/>
	<acme:input-double code="sponsor.sponsorship.form.label.duration" path="duration" readonly="${published}"/>
	<acme:input-integer code="sponsor.sponsorship.form.label.amount" path="amount" readonly="${published}"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.type" path="type" readonly="${published}"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.emailContact" path="emailContact" readonly="${published}"/>
	<acme:input-url code="sponsor.sponsorship.form.label.moreInfo" path="moreInfo" readonly="${published}"/>		
	
	<jstl:choose>
    <jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && !publish}">
        <acme:submit code="sponsor.sponsorship.form.button.update" action="/sponsor/sponsorship/update"/>
        <acme:submit code="sponsor.sponsorship.form.button.delete" action="/sponsor/sponsorship/delete"/>
        <acme:submit code="sponsor.sponsorship.form.button.publish" action="/sponsor/sponsorship/publish"/>
    </jstl:when>
    <jstl:when test="${_command == 'create'}">
        <acme:submit code="sponsor.sponsorship.form.button.create" action="/sponsor/sponsorship/create"/>
    </jstl:when>
	</jstl:choose>
		
</acme:form>

