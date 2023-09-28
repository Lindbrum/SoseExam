package it.univaq.dandd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "itinerary_info")
public class ItineraryInfo {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name="service_id", nullable = false)
	private int serviceId;
	
	@NotBlank
	@Column(name="service_type", nullable = false)
	private String serviceType;
	
	public ItineraryInfo() {}
	
	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public ItineraryInfo(int id, int serviceId, @NotBlank String serviceType) {
		super();
		this.id = id;
		this.serviceId = serviceId;
		this.serviceType = serviceType;
	}
		
}

