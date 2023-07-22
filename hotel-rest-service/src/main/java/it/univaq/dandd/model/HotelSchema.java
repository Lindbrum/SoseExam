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
@Table(name = "hotel_schema")
public class HotelSchema {
		@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank
	@Column(name="hotel_name", nullable = false)
	private String hotelName;
	
	@NotBlank
	@Column(name="location_name", nullable = false)
	private String locationName;
	
	@NotNull
	@Min(-90) @Max(90)
	@Column(name="location_latitude", nullable = false, precision=9)
	private Float locationLatitude;
	
	@NotNull
	@Min(-180) @Max(180)
	@Column(name="location_longitude", nullable = false, precision=10)
	private Float locationLongitude;
	
	public HotelSchema() {}

	public HotelSchema(long id, @NotBlank String hotelName, @NotBlank String locationName,
			@NotNull @Min(-90) @Max(90) Float locationLatitude, @NotNull @Min(-180) @Max(180) Float locationLongitude) {
		super();
		this.id = id;
		this.hotelName = hotelName;
		this.locationName = locationName;
		this.locationLatitude = locationLatitude;
		this.locationLongitude = locationLongitude;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Float getLocationLatitude() {
		return locationLatitude;
	}

	public void setLocationLatitude(Float locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public Float getLocationLongitude() {
		return locationLongitude;
	}

	public void setLocationLongitude(Float locationLongitude) {
		this.locationLongitude = locationLongitude;
	}


	
	
	
}