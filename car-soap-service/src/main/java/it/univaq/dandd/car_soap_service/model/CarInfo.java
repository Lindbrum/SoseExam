package it.univaq.dandd.car_soap_service.model;

import java.time.LocalDateTime;

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
@Table(name = "car_info")
public class CarInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String name;
	
	@NotNull
	@Min(0)
	@Column(nullable = false)
	private Float price;
	
	@NotBlank
	@Column(name = "location_name", nullable = false)
	private String locationName;
	
	@NotNull
	@Min(-90) @Max(90)
	@Column(name="rental_latitude", nullable = false, precision=9)
	private Float rentalLatitude;
	
	@NotNull
	@Min(-180) @Max(180)
	@Column(name="rental_longitude", nullable = false, precision=10)
	private Float rentalLongitude;
	
	@NotNull
	@Column(name="available_from", nullable = false)
	private LocalDateTime availableFrom;
	
	
	
	public CarInfo(Long id, @NotBlank String name, @NotNull @Min(0) Float price, @NotBlank String locationName,
			@NotNull @Min(-90) @Max(90) Float rentalLatitude, @NotNull @Min(-180) @Max(180) Float rentalLongitude,
			@NotNull LocalDateTime availableFrom) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.locationName = locationName;
		this.rentalLatitude = rentalLatitude;
		this.rentalLongitude = rentalLongitude;
		this.availableFrom = availableFrom;
	}

	public CarInfo() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Float getRentalLatitude() {
		return rentalLatitude;
	}

	public void setRentalLatitude(Float rentalLatitude) {
		this.rentalLatitude = rentalLatitude;
	}

	public Float getRentalLongitude() {
		return rentalLongitude;
	}

	public void setRentalLongitude(Float rentalLongitude) {
		this.rentalLongitude = rentalLongitude;
	}

	public LocalDateTime getAvailableFrom() {
		return availableFrom;
	}

	public void setAvailableFrom(LocalDateTime availableFrom) {
		this.availableFrom = availableFrom;
	}
	
	

}
