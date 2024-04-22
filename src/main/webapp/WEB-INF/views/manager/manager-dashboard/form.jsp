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
	<acme:input-integer code="manager.dashboard.form.label.totalUserStories" path="totalUserStories"/>
	<acme:input-double code="manager.dashboard.form.label.averageCostUserStories" path="averageCostUserStories"/>
	<acme:input-double code="manager.dashboard.form.label.deviationCostUserStories" path="deviationCostUserStories"/>
	<acme:input-integer code="manager.dashboard.form.label.minimumCostUserStories" path="minimumCostUserStories"/>
	<acme:input-integer code="manager.dashboard.form.label.maximumCostUserStories" path="maximumCostUserStories"/>
	<acme:input-double code="manager.dashboard.form.label.averageCostProjects" path="averageCostProjects"/>
	<acme:input-double code="manager.dashboard.form.label.deviationCostProjects" path="deviationCostProjects"/>
	<acme:input-double code="manager.dashboard.form.label.minimumCostProjects" path="minimumCostProjects"/>
	<acme:input-double code="manager.dashboard.form.label.maximumCostProjects" path="maximumCostProjects"/>

</acme:form>