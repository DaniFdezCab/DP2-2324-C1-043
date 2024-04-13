
package acme.forms;

import javax.persistence.Entity;
import javax.validation.constraints.PositiveOrZero;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SponsorDashboard extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	// Attributes 

	// TODO: Implementar estos atributos de forma compleja a través de los servicios.
	@PositiveOrZero
	private int					numberInvoicesTaxed;

	@PositiveOrZero
	private int					numberSponsorshipsLinked;

	@PositiveOrZero
	private double				averageI;

	@PositiveOrZero
	private double				deviationI;

	@PositiveOrZero
	private int					minimumI;

	@PositiveOrZero
	private int					maximumI;

	@PositiveOrZero
	private double				averageS;

	@PositiveOrZero
	private double				deviationS;

	@PositiveOrZero
	private int					minimumS;

	@PositiveOrZero
	private int					maximumS;

}
