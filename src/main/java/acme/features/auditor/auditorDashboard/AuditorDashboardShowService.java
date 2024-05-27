
package acme.features.auditor.auditorDashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.dashboard.AuditorDashboard;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int auditorId;
		AuditorDashboard dashboard;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		Collection<Double> auditingRecordsPerAudit = this.repository.auditingRecordsPerAudit(auditorId);

		int numAuditsDynamic;
		int numAuditsStatic;
		Double averageAuditRecords;
		Double deviationAuditRecords;
		int minimumAuditRecords;
		int maximumAuditRecords;
		Double averageRecordPeriod;
		Double deviationRecordPeriod;
		Double minimumRecordPeriod;
		Double maximumRecordPeriod;

		numAuditsDynamic = this.repository.auditsDynamic(auditorId);
		numAuditsStatic = this.repository.auditsStatic(auditorId);
		averageAuditRecords = this.repository.averageAuditingRecords(auditorId);
		deviationAuditRecords = this.desviation(auditingRecordsPerAudit);
		minimumAuditRecords = this.repository.minAuditingRecords(auditorId);
		maximumAuditRecords = this.repository.maxAuditingRecords(auditorId);
		averageRecordPeriod = this.repository.averageRecordPeriod(auditorId);
		deviationRecordPeriod = this.repository.deviationRecordPeriod(auditorId);
		minimumRecordPeriod = this.repository.minimumRecordPeriod(auditorId);
		maximumRecordPeriod = this.repository.maximumRecordPeriod(auditorId);

		dashboard = new AuditorDashboard();
		dashboard.setNumAuditsDynamic(numAuditsDynamic);
		dashboard.setNumAuditsStatic(numAuditsStatic);
		dashboard.setAverageAuditRecords(averageAuditRecords);
		dashboard.setDeviationAuditRecords(deviationAuditRecords);
		dashboard.setMinimumAuditRecords(minimumAuditRecords);
		dashboard.setMaximumAuditRecords(maximumAuditRecords);
		dashboard.setAveragePeriodAuditRecords(averageRecordPeriod);
		dashboard.setDeviationPeriodAuditRecords(deviationRecordPeriod);
		dashboard.setMinimumPeriodAuditRecords(minimumRecordPeriod);
		dashboard.setMaximumPeriodAuditRecords(maximumRecordPeriod);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "averageAuditRecords", "deviationAuditRecords", "minimumAuditRecords", "maximumAuditRecords", "averagePeriodAuditRecords", "deviationPeriodAuditRecords", "minimumPeriodAuditRecords", "maximumPeriodAuditRecords",
			"numAuditsDynamic", "numAuditsStatic");

		super.getResponse().addData(dataset);
	}

	public Double desviation(final Collection<Double> values) {
		Double res;
		Double aux;
		res = 0.0;
		if (!values.isEmpty()) {
			Double average = this.average(values);
			aux = 0.0;
			for (final Double value : values)
				aux += Math.pow(value + average, 2);
			res = Math.sqrt(aux / values.size());
		}
		return res;
	}

	private Double average(final Collection<Double> values) {
		double sum = 0.0;
		for (Double value : values)
			sum += value;
		return sum / values.size();
	}

}
