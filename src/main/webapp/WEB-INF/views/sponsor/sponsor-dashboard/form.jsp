<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="sponsor.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.maximumInvoice"/>
		</th>
		<td>
			<acme:print value="${maximumInvoice}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.maximumSponsorship"/>
		</th>
		<td>
			<acme:print value="${maximumSponsorship}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.averageSponsorship"/>
		</th>
		<td>
			<acme:print value="${averageSponsorship}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.averageInvoice"/>
		</th>
		<td>
			<acme:print value="${averageInvoice}"/>
		</td>
	</tr>
	
	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.deviationInvoice"/>
		</th>
		<td>
			<acme:print value="${deviationInvoice}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.deviationSponsorship"/>
		</th>
		<td>
			<acme:print value="${deviationSponsorship}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.minimumInvoice"/>
		</th>
		<td>
			<acme:print value="${minimumInvoice}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.minimumSponsorship"/>
		</th>
		<td>
			<acme:print value="${minimumSponsorship}"/>
		</td>
	</tr>		
</table>



<acme:return/>