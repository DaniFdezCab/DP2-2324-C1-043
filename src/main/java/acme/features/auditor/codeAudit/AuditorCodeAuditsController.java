/*
 * AdministratorUserAccountController.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.codeAudit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.audits.CodeAudit;
import acme.roles.Auditor;

@Controller
public class AuditorCodeAuditsController extends AbstractController<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditsShowService			showService;

	@Autowired
	private AuditorCodeAuditsListPublishedService	listPublishedService;

	@Autowired
	private AuditorCodeAuditsUpdateService			updateService;

	@Autowired
	private AuditorCodeAuditsPublishService			publishService;

	@Autowired

	private AuditorCodeAuditsDeleteService			deleteService;

	@Autowired
	private AuditorCodeAuditsCreateService			createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("create", this.createService);

		super.addCustomCommand("listPublished", "list", this.listPublishedService);
		super.addCustomCommand("publish", "update", this.publishService);

	}

}
