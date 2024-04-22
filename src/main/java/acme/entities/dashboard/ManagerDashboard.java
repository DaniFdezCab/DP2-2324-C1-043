
package acme.entities.dashboard;

import javax.validation.constraints.PositiveOrZero;

import acme.client.data.AbstractForm;
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

	@PositiveOrZero
	private double				averageCostProjects;

	@PositiveOrZero
	private double				deviationCostProjects;

	@PositiveOrZero
	private double				minimumCostProjects;

	@PositiveOrZero
	private double				maximumCostProjects;

}
