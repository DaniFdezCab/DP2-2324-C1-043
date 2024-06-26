
package acme.features.manager.project;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.Project;
import acme.roles.Manager;

@Controller
public class ManagerProjectController extends AbstractController<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectShowService			showService;

	@Autowired
	private ManagerProjectListService			listService;

	@Autowired
	private ManagerProjectListPublishedService	listPublishedService;

	@Autowired
	private ManagerProjectUpdateService			updateService;

	@Autowired
	private ManagerProjectPublishService		publishService;

	@Autowired
	private ManagerProjectDeleteService			deleteService;

	@Autowired
	private ManagerProjectCreateService			createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("create", this.createService);

		super.addCustomCommand("listPublished", "list", this.listPublishedService);
		super.addCustomCommand("publish", "update", this.publishService);

	}

}
