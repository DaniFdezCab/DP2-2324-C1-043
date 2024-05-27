
package acme.features.developer.trainingSession;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.trainings.TrainingSession;
import acme.roles.Developer;

@Controller
public class DeveloperTrainingSessionController extends AbstractController<Developer, TrainingSession> {

	@Autowired
	protected DeveloperTrainingSessionListService		listService;

	@Autowired
	protected DeveloperTrainingSessionShowService		showService;

	@Autowired
	protected DeveloperTrainingSessionCreateService		createService;

	@Autowired
	protected DeveloperTrainingSessionUpdateService		updateService;

	@Autowired
	protected DeveloperTrainingSessionDeleteService		deleteService;

	@Autowired
	protected DeveloperTrainingSessionPublishService	publishService;


	@PostConstruct
	protected void initilialise() {

		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("publish", "update", this.publishService);
	}
}
