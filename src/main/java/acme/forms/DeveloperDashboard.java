
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;
	Integer						trainingModulesWithUpdateMoment;
	Integer						trainingSessionsWithALink;
	Double						averageTime;
	Double						deviationTime;
	Double						maximumTime;
	Double						minimumTime;

}
