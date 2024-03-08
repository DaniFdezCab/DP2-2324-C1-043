
package acme.entities.dashboard;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	// Serialisation identifier 

	private static final long	serialVersionUID	= 1L;

	// Attributes

	int							totalNumberOfProgressLogs;
	int							completeness;
	float						averageBudget;
	float						deviationBudget;
	float						minimumBudget;
	float						maximumBudget;

	// Las operaciones se realizan en el servicio.

}
