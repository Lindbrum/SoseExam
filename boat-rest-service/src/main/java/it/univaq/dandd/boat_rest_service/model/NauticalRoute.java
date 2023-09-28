package it.univaq.dandd.boat_rest_service.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "nautical_route")
public class NauticalRoute {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank
	@Column(name="departure_name", nullable = false)
	private String departureName;
	
	@NotNull
	@Min(-90) @Max(90)
	@Column(name="departure_latitude", nullable = false, precision=9)
	private Float departureLatitude;
	
	@NotNull
	@Min(-180) @Max(180)
	@Column(name="departure_longitude", nullable = false, precision=10)
	private Float departureLongitude;
	
	@NotBlank
	@Column(name="arrival_name", nullable = false)
	private String arrivalName;
	
	@NotNull
	@Min(-90) @Max(90)
	@Column(name="arrival_latitude", nullable = false, precision=9)
	private Float arrivalLatitude;
	
	@NotNull
	@Min(-180) @Max(180)
	@Column(name="arrival_longitude", nullable = false, precision=10)
	private Float arrivalLongitude;
	
	@NotNull
	@Future
	@Column(name="departure_datetime")
	private LocalDateTime departureDatetime;
	
	@NotNull
	@Future
	@Column(name="arrival_datetime")
	private LocalDateTime arrivalDatetime;

	public NauticalRoute(long id, @NotBlank String departureName, @NotNull @Min(-90) @Max(90) Float departureLatitude,
			@NotNull @Min(-180) @Max(180) Float departureLongitude, @NotBlank String arrivalName,
			@NotNull @Min(-90) @Max(90) Float arrivalLatitude, @NotNull @Min(-180) @Max(180) Float arrivalLongitude,
			@NotNull @Future LocalDateTime departureDatetime, @NotNull @Future LocalDateTime arrivalDatetime) {
		this.id = id;
		this.departureName = departureName;
		this.departureLatitude = departureLatitude;
		this.departureLongitude = departureLongitude;
		this.arrivalName = arrivalName;
		this.arrivalLatitude = arrivalLatitude;
		this.arrivalLongitude = arrivalLongitude;
		this.departureDatetime = departureDatetime;
		this.arrivalDatetime = arrivalDatetime;
	}
	
	public NauticalRoute() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDepartureName() {
		return departureName;
	}

	public void setDepartureName(String departureName) {
		this.departureName = departureName;
	}

	public Float getDepartureLatitude() {
		return departureLatitude;
	}

	public void setDepartureLatitude(Float departureLatitude) {
		this.departureLatitude = departureLatitude;
	}

	public Float getDepartureLongitude() {
		return departureLongitude;
	}

	public void setDepartureLongitude(Float departureLongitude) {
		this.departureLongitude = departureLongitude;
	}

	public String getArrivalName() {
		return arrivalName;
	}

	public void setArrivalName(String arrivalLocationName) {
		this.arrivalName = arrivalLocationName;
	}

	public Float getArrivalLatitude() {
		return arrivalLatitude;
	}

	public void setArrivalLatitude(Float arrivalLatitude) {
		this.arrivalLatitude = arrivalLatitude;
	}

	public Float getArrivalLongitude() {
		return arrivalLongitude;
	}

	public void setArrivalLongitude(Float arrivalLongitude) {
		this.arrivalLongitude = arrivalLongitude;
	}

	public LocalDateTime getDepartureDatetime() {
		return departureDatetime;
	}

	public void setDepartureDatetime(LocalDateTime departureDatetime) {
		this.departureDatetime = departureDatetime;
	}

	public LocalDateTime getArrivalDatetime() {
		return arrivalDatetime;
	}

	public void setArrivalDatetime(LocalDateTime arrivalDatetime) {
		this.arrivalDatetime = arrivalDatetime;
	}
	
	
	
}