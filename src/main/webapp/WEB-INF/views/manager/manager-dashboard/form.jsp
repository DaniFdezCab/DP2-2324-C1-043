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


<div>
	<canvas id="myCanvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"totalUserStories", "totalUserStoriesMUST", "totalUserStoriesSHOULD", "totalUserStoriesCOULD", "totalUserStoriesWONT"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${totalUserStories}"/>, 
						<jstl:out value="${totalUserStoriesMUST}"/>,
						<jstl:out value="${totalUserStoriesSHOULD}"/>,
						<jstl:out value="${totalUserStoriesCOULD}"/>,
						<jstl:out value="${totalUserStoriesWONT}"/>
		
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
<acme:form>
	<acme:input-double code="manager.dashboard.form.label.averageCostUserStories" path="averageCostUserStories"/>
	<acme:input-double code="manager.dashboard.form.label.deviationCostUserStories" path="deviationCostUserStories"/>
	<acme:input-integer code="manager.dashboard.form.label.minimumCostUserStories" path="minimumCostUserStories"/>
	<acme:input-integer code="manager.dashboard.form.label.maximumCostUserStories" path="maximumCostUserStories"/>
	<acme:input-money code="manager.dashboard.form.label.averageCostProjects" path="averageCostProjects"/>
	<acme:input-money code="manager.dashboard.form.label.deviationCostProjects" path="deviationCostProjects"/>
	<acme:input-money code="manager.dashboard.form.label.minimumCostProjects" path="minimumCostProjects"/>
	<acme:input-money code="manager.dashboard.form.label.maximumCostProjects" path="maximumCostProjects"/>

</acme:form>