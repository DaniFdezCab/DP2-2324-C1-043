
package acme.entities.projects;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserStory extends AbstractEntity {

	// Serialisation identifier ---------------------------------------------

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 76)
	private String				title;

	@NotBlank
	@Length(max = 101)
	private String				description;

	@Min(0)
	@NotNull
	private int					estimatedCost;

	@NotBlank
	@Length(max = 101)
	private String				acceptanceCriteria;

	@NotNull
	private Priority			priority;

	@URL
	@Length(max = 255)
	private String				optionalLink;

}
