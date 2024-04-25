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

<div>
	<canvas id="myCanvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"TaxedInvoices", "LinkedSponsorships"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${taxedInvoices}"/>, 
						<jstl:out value="${linkedSponsorships}"/>
		
					],
					backgroundColor: [
		                'rgba(255, 99, 132, 0.2)',   
		                'rgba(54, 162, 235, 0.2)',   
		                'rgba(255, 206, 86, 0.2)',   
		                'rgba(75, 192, 192, 0.2)'    
		            ],
		            borderColor: [
		                'rgba(255, 99, 132, 1)',     
		                'rgba(54, 162, 235, 1)',     
		                'rgba(255, 206, 86, 1)',     
		                'rgba(75, 192, 192, 1)'      
		            ],
		            borderWidth: 1
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("myCanvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>

<acme:return/>