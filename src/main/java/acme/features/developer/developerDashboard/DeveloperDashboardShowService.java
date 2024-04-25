
package acme.features.developer.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Service
public class DeveloperDashboardShowService extends AbstractService<Developer, DeveloperDashboard> {

	@Autowired
	private DeveloperDashboardRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		DeveloperDashboard dashboard;
		Integer trainingModulesWithUpdateMoment;
		Integer trainingSessionsWithALink;
		Double averageTime;
		Double deviationTime;
		Double maximumTime;
		Double minimumTime;

		Principal principal;
		principal = super.getRequest().getPrincipal();
		int id = principal.getActiveRoleId();

		trainingModulesWithUpdateMoment = this.repository.totalNumberOfTrainingModulesWithUpdateMoment(id);
		trainingSessionsWithALink = this.repository.totalNumberOfTrainingSessionsWithLink(id);
		averageTime = this.repository.averageTotalTimeOfTrainingModulesPerDeveloper(id);
		deviationTime = this.repository.deviationTotalTimeOfTrainingModulesPerDeveloper(id);
		maximumTime = this.repository.maxTotalTimeOfTrainingModulesPerDeveloper(id);
		minimumTime = this.repository.minTotalTimeOfTrainingModulesPerDeveloper(id);

		dashboard = new DeveloperDashboard();
		dashboard.setTrainingModulesWithUpdateMoment(trainingModulesWithUpdateMoment);
		dashboard.setTrainingSessionsWithALink(trainingSessionsWithALink);
		dashboard.setAverageTime(averageTime);
		dashboard.setDeviationTime(deviationTime);
		dashboard.setMaximumTime(maximumTime);
		dashboard.setMinimumTime(minimumTime);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"trainingModulesWithUpdateMoment", "trainingSessionsWithALink", // 
			"averageTime", "deviationTime", //
			"maximumTime", "minimumTime");

		super.getResponse().addData(dataset);
	}
}
