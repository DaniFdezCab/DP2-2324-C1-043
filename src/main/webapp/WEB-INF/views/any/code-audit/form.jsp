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
	<acme:input-textbox code="any.codeaudit.label.code" path="code" placeholder="ABC-000"/>
	<acme:input-moment code="any.codeaudit.label.executionDate" path="executionDate"/>
	<acme:input-select code="any.codeaudit.label.type" path="type" choices="${type}" />
	<acme:input-textbox code="any.codeaudit.label.mark" path="mark" readonly="true"/>
	<acme:input-textbox code="any.codeaudit.label.proposedCorrectiveActions" path="proposedCorrectiveActions"/>
	<acme:input-url code="any.codeaudit.label.optionalLink" path="optionalLink"/>
	<acme:input-select code="any.codeaudit.label.project" path="project" choices="${projects}"/>
	
</acme:form>

	<acme:button code="any.codeaudit.form.button.auditrecords" action="/any/audit-record/list?codeAuditId=${id}"/>