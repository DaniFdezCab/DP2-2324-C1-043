
package acme.entities.projects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project extends AbstractEntity {

	// Serialisation identifier ---------------------------------------------

	private static final long	serialVersionUID	= 1L;

	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{3}-[0-9]{4}")
	@NotBlank
	private String				code;

	@NotBlank
	@Length(max = 76)
	private String				title;

	@NotBlank
	@Length(max = 101)
	private String				summary;

	private boolean				fatalErrors			= false;

	@Min(0)
	private int					cost;

	@URL
	@Length(max = 255)
	private String				optionalLink;

}
