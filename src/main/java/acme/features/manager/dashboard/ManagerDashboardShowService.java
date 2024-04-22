
package acme.features.manager.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		double averageCostUserStories;
		double deviationCostUserStories;
		int minimumCostUserStories;
		int maximumCostUserStories;
		double averageCostProjects;
		double deviationCostProjects;
		double minimumCostProjects;
		double maximumCostProjects;

		totalUserStories = this.repository.countManyUserStoriesByManagerId(managerId);
		averageCostUserStories = this.repository.averageCostUserStoriesByManagerId(managerId);
		deviationCostUserStories = this.repository.deviationCostUserStoriesByManagerId(managerId);
		minimumCostUserStories = this.repository.minimumCostUserStoriesByManager(managerId);
		maximumCostUserStories = this.repository.maximumCostUserStoriesByManager(managerId);
		averageCostProjects = this.repository.averageCostProjectsByManagerId(managerId);
		deviationCostProjects = this.repository.deviationCostProjectsByManagerId(managerId);
		minimumCostProjects = this.repository.minimumCostProjectsByManager(managerId);
		maximumCostProjects = this.repository.maximumCostProjectsByManager(managerId);

		managerDashboard.setTotalUserStories(totalUserStories);
		managerDashboard.setAverageCostUserStories(averageCostUserStories);
		managerDashboard.setDeviationCostUserStories(deviationCostUserStories);
		managerDashboard.setMinimumCostUserStories(minimumCostUserStories);
		managerDashboard.setMaximumCostUserStories(maximumCostUserStories);
		managerDashboard.setAverageCostProjects(averageCostProjects);
		managerDashboard.setDeviationCostUserStories(deviationCostProjects);
		managerDashboard.setMinimumCostProjects(minimumCostProjects);
		managerDashboard.setMaximumCostProjects(maximumCostProjects);

		super.getBuffer().addData(managerDashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "totalUserStories", "averageCostUserStories", "deviationCostUserStories", "minimumCostUserStories", "maximumCostUserStories", "averageCostProjects", "deviationCostProjects", "minimumCostProjects",
			"maximumCostProjects");

		super.getResponse().addData(dataset);
	}

}
