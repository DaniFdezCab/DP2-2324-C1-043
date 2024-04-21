
package acme.features.manager.associationProject;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.AssociationProject;
import acme.roles.Manager;

@Controller
public class ManagerAssociationProjectController extends AbstractController<Manager, AssociationProject> {

	@Autowired
	private ManagerAssociationProjectAddService addService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("add", "create", this.addService);
	}

}
