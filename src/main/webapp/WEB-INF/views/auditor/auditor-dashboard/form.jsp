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
	<acme:input-integer code="auditor.dashboard.form.label.numAuditsDynamic" path="numAuditsDynamic" readonly="true"/>
	<acme:input-integer code="auditor.dashboard.form.label.numAuditsStatic" path="numAuditsStatic" readonly="true"/>
	<acme:input-double code="auditor.dashboard.form.label.averageAuditRecords" path="averageAuditRecords" readonly="true" placeholder="--"/>
	<acme:input-double code="auditor.dashboard.form.label.deviationAuditRecords" path="deviationAuditRecords" readonly="true" placeholder="--"/>
	<acme:input-integer code="auditor.dashboard.form.label.minimumAuditRecords" path="minimumAuditRecords" readonly="true" placeholder="--"/>
	<acme:input-integer code="auditor.dashboard.form.label.maximumAuditRecords" path="maximumAuditRecords" readonly="true" placeholder="--"/>
	<acme:input-double code="auditor.dashboard.form.label.averagePeriodLength" path="averagePeriodAuditRecords" readonly="true" placeholder="--"/>
	<acme:input-double code="auditor.dashboard.form.label.deviationPeriodLength" path="deviationPeriodAuditRecords" readonly="true" placeholder="--"/>
	<acme:input-integer code="auditor.dashboard.form.label.minimumPeriodAuditRecords" path="minimumPeriodAuditRecords" readonly="true" placeholder="--"/>
	<acme:input-integer code="auditor.dashboard.form.label.maximumPeriodAuditRecords" path="maximumPeriodAuditRecords" readonly="true" placeholder="--"/>
</acme:form>