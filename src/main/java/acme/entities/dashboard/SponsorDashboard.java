
package acme.entities.dashboard;

import javax.persistence.Entity;

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

	private Integer				numberInvoicesTaxed;

	private Integer				numberSponsorshipsLinked;

	private Double				averageI;

	private Double				deviationI;

	private Integer				minimumI;

	private Integer				maximumI;

	private Double				averageS;

	private Double				deviationS;

	private Integer				minimumS;

	private Integer				maximumS;

	// Las operaciones se realizan en el servicio

}
