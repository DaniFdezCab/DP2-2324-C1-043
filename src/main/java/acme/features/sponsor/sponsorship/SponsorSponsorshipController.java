
package acme.features.sponsor.sponsorship;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Controller
public class SponsorSponsorshipController extends AbstractController<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipListService			listService;

	@Autowired
	private SponsorSponsorshipShowService			showService;

	@Autowired
	private SponsorSponsorshipDeleteService			deleteService;

	@Autowired
	private SponsorSponsorshipPublishService		publishService;

	@Autowired
	private SponsorSponsorshipUpdateService			updateService;

	@Autowired
	private SponsorSponsorshipCreateService			createService;

	@Autowired
	private SponsorSponsorshipListPublishedService	listPublishedService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("publish", "update", this.publishService);
		super.addCustomCommand("listPublished", "list", this.listPublishedService);

	}

}
