
package acme.entities.dashboard;

import javax.persistence.Entity;
import javax.validation.constraints.PositiveOrZero;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditorDashboard extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Derived attributes -----------------------------------------------------

	@PositiveOrZero
	private int					totalCodeAudits;

	@PositiveOrZero
	private double				averageAuditRecords;

	@PositiveOrZero
	private double				deviationAuditRecords;

	@PositiveOrZero
	private double				minimumAuditRecords;

	@PositiveOrZero
	private double				maximumAuditRecords;

	@PositiveOrZero
	private double				averagePeriodAuditRecords;

	@PositiveOrZero
	private double				deviationPeriodAuditRecords;

	@PositiveOrZero
	private double				minimumPeriodAuditRecords;

	@PositiveOrZero
	private double				maximumPeriodAuditRecords;

}
