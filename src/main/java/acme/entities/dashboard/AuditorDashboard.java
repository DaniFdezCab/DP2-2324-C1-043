
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
	private int					numAuditsStatic;

	@PositiveOrZero
	private int					numAuditsDynamic;

	@PositiveOrZero
	private Double				averageAuditRecords;

	@PositiveOrZero
	private Double				deviationAuditRecords;

	@PositiveOrZero
	private int					minimumAuditRecords;

	@PositiveOrZero
	private int					maximumAuditRecords;

	@PositiveOrZero
	private Double				averagePeriodAuditRecords;

	@PositiveOrZero
	private Double				deviationPeriodAuditRecords;

	@PositiveOrZero
	private Double				minimumPeriodAuditRecords;

	@PositiveOrZero
	private Double				maximumPeriodAuditRecords;

}
