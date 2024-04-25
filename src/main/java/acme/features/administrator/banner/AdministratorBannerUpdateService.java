
package acme.features.administrator.banner;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banners.Banner;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Administrator.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "updateMoment", "displayStart", "displayEnd", "slogan", "picture", "link");

	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("displayStart"))
			super.state(object.getDisplayStart().after(object.getUpdateMoment()), "displayStart", "administrator.banner.form.error.displayStart");
		if (!super.getBuffer().getErrors().hasErrors("displayEnd")) {
			boolean status;

			Calendar c = Calendar.getInstance();
			c.setTime(object.getDisplayStart());
			c.add(Calendar.DATE, 7);

			Date displayStartAfterAWeek = c.getTime();

			status = object.getDisplayEnd().after(displayStartAfterAWeek);
			super.state(status, "displayEnd", "administrator.banner.form.error.displayEnd");
		}

	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "updateMoment", "displayStart", "displayEnd", "slogan", "picture", "link");

		super.getResponse().addData(dataset);
	}

}
