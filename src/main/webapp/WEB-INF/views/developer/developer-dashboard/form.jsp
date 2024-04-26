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
	<acme:input-integer code="developer.dashboard.form.label.training-modules-with-update-moment" path="trainingModulesWithUpdateMoment"/>
	<acme:input-integer code="developer.dashboard.form.label.training-sessions-with-a-link" path="trainingSessionsWithALink"/>
	<acme:input-double code="developer.dashboard.form.label.average-time" path="averageTime"/>
	<acme:input-double code="developer.dashboard.form.label.deviation-time" path="deviationTime"/>
	<acme:input-double code="developer.dashboard.form.label.maximum-time" path="maximumTime"/>
	<acme:input-double code="developer.dashboard.form.label.minimum-time" path="minimumTime"/>
	
</acme:form>






