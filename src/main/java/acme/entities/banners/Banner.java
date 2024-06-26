
package acme.entities.banners;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	private Date				updateMoment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				displayStart;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				displayEnd;

	@NotBlank
	@Length(max = 76)
	private String				slogan;

	@URL
	@NotBlank
	private String				picture;

	@URL
	@NotBlank
	private String				link;
}
