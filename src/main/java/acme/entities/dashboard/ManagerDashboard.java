
package acme.entities.dashboard;

import javax.persistence.Entity;
import javax.validation.constraints.PositiveOrZero;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ManagerDashboard extends AbstractEntity {

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
	private double				minimumCostUserStories;

	@PositiveOrZero
	private double				maximumCostUserStories;

	@PositiveOrZero
	private double				averageCostProjects;

	@PositiveOrZero
	private double				deviationCostProjects;

	@PositiveOrZero
	private double				minimumCostProjects;

	@PositiveOrZero
	private double				maximumCostProjects;

}
