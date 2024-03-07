
package acme.entities.projects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project extends AbstractEntity {

	// Serialisation identifier ---------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{3}-[0-9]{4}")
	@NotBlank
	private String				code;

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				summary;

	/*
	 * Esta es una propiedad con una restricción compleja, ya que cuando fatalErrors se setea en true
	 * el proyecto tiene que ser rechazado por el sistema
	 */
	private boolean				fatalErrors			= false;

	@PositiveOrZero
	private double				cost;

	@URL
	@Length(max = 255)
	private String				url;

	@ManyToOne(optional = false)
	@Valid
	private Manager				manager;

}
