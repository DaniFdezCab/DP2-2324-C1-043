
package acme.features.manager.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.dashboard.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Integer managerId = super.getRequest().getPrincipal().getActiveRoleId();

		ManagerDashboard managerDashboard = new ManagerDashboard();

		int totalUserStories;
		int totalUserStoriesMUST;
		int totalUserStoriesSHOULD;
		int totalUserStoriesCOULD;
		int totalUserStoriesWONT;
		double averageCostUserStories;
		double deviationCostUserStories;
		int minimumCostUserStories;
		int maximumCostUserStories;
		double averageCostProjects;
		double deviationCostProjects;
		double minimumCostProjects;
		double maximumCostProjects;

		totalUserStories = this.repository.countManyUserStoriesByManagerId(managerId).orElse(0);
		totalUserStoriesMUST = this.repository.countManyUserStoriesMUSTByManagerId(managerId).orElse(0);
		totalUserStoriesSHOULD = this.repository.countManyUserStoriesSHOULDByManagerId(managerId).orElse(0);
		totalUserStoriesCOULD = this.repository.countManyUserStoriesCOULDByManagerId(managerId).orElse(0);
		totalUserStoriesWONT = this.repository.countManyUserStoriesWONTByManagerId(managerId).orElse(0);
		averageCostUserStories = this.repository.averageCostUserStoriesByManagerId(managerId).orElse(0.0);
		deviationCostUserStories = this.repository.deviationCostUserStoriesByManagerId(managerId).orElse(0.0);
		minimumCostUserStories = this.repository.minimumCostUserStoriesByManager(managerId).orElse(0);
		maximumCostUserStories = this.repository.maximumCostUserStoriesByManager(managerId).orElse(0);
		averageCostProjects = this.repository.averageCostProjectsByManagerId(managerId).orElse(0.0);
		deviationCostProjects = this.repository.deviationCostProjectsByManagerId(managerId).orElse(0.0);
		minimumCostProjects = this.repository.minimumCostProjectsByManager(managerId).orElse(0.0);
		maximumCostProjects = this.repository.maximumCostProjectsByManager(managerId).orElse(0.0);

		managerDashboard.setTotalUserStories(totalUserStories);
		managerDashboard.setTotalUserStoriesMUST(totalUserStoriesMUST);
		managerDashboard.setTotalUserStoriesSHOULD(totalUserStoriesSHOULD);
		managerDashboard.setTotalUserStoriesCOULD(totalUserStoriesCOULD);
		managerDashboard.setTotalUserStoriesWONT(totalUserStoriesWONT);
		managerDashboard.setAverageCostUserStories(averageCostUserStories);
		managerDashboard.setDeviationCostUserStories(deviationCostUserStories);
		managerDashboard.setMinimumCostUserStories(minimumCostUserStories);
		managerDashboard.setMaximumCostUserStories(maximumCostUserStories);

		Money averageMoney = new Money();
		averageMoney.setAmount(averageCostProjects);
		averageMoney.setCurrency("USD");
		managerDashboard.setAverageCostProjects(averageMoney);

		Money deviationMoney = new Money();
		deviationMoney.setAmount(deviationCostProjects);
		deviationMoney.setCurrency("USD");
		managerDashboard.setDeviationCostProjects(deviationMoney);

		Money minimumMoney = new Money();
		minimumMoney.setAmount(minimumCostProjects);
		minimumMoney.setCurrency("USD");
		managerDashboard.setMinimumCostProjects(minimumMoney);

		Money maximumMoney = new Money();
		maximumMoney.setAmount(maximumCostProjects);
		maximumMoney.setCurrency("USD");
		managerDashboard.setMaximumCostProjects(maximumMoney);

		super.getBuffer().addData(managerDashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "totalUserStories", "totalUserStoriesMUST", "totalUserStoriesSHOULD", "totalUserStoriesCOULD", "totalUserStoriesWONT", "averageCostUserStories", "deviationCostUserStories", "minimumCostUserStories",
			"maximumCostUserStories", "averageCostProjects", "deviationCostProjects", "minimumCostProjects", "maximumCostProjects");

		super.getResponse().addData(dataset);
	}

}
