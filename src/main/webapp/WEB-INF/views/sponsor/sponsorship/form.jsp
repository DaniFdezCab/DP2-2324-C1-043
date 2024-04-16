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

<acme:form readonly="true">
	<acme:input-textbox code="sponsor.sponsorship.form.label.code" path="code"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.moment" path="moment"/>
	<acme:input-double code="sponsor.sponsorship.form.label.duration" path="duration"/>
	<acme:input-integer code="sponsor.sponsorship.form.label.amount" path="amount"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.type" path="type"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.emailContact" path="emailContact"/>
	<acme:input-url code="sponsor.sponsorship.form.label.moreInfo" path="moreInfo"/>				
</acme:form>

