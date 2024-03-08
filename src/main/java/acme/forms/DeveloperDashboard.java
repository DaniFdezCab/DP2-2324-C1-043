
package acme.forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard {

	private static final long	serialVersionUID	= 1L;
	Integer						trainingModulesWithUpdateMoment;
	Integer						trainingSessionsWithALink;
	Double						averageTime;
	Double						deviationTime;
	Double						maximumTime;
	Double						minimumTime;

}
