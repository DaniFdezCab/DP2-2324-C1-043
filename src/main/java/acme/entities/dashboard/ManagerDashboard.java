
package acme.entities.dashboard;

import javax.validation.constraints.PositiveOrZero;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Derived attributes -----------------------------------------------------

	@PositiveOrZero
	private int					totalUserStories;

	@PositiveOrZero
	private double				averageCostUserStories;

	@PositiveOrZero
	private double				deviationCostUserStories;

	@PositiveOrZero
	private int					minimumCostUserStories;

	@PositiveOrZero
	private int					maximumCostUserStories;

	private Money				averageCostProjects;

	private Money				deviationCostProjects;

	private Money				minimumCostProjects;

	private Money				maximumCostProjects;

}
