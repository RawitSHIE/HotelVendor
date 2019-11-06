package sop.project.hotel.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "hotel")
public class Hotel extends AuditModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long hotelId;

	@NotNull
	@Size(min = 1, max = 30)
	private String hotelName;

	@NotNull
	@Size(min = 1, max = 100)
	private String country;

	@NotNull
	@Size(min = 1, max = 100)
	private String provinceState;

	@NotNull
	@Size(min = 1, max = 100)
	private String district;

	@NotNull
	@Size(min = 1, max = 100)
	private String street;

	@ElementCollection
	@CollectionTable(name = "hotel_images", joinColumns = @JoinColumn(name = "hotel_id"))
	@Column(name = "images")
	private List<String> hotelImages = new ArrayList<String>();

	@Column(columnDefinition = "text")
	private String additionalDetail;

	@NotNull
	private boolean availible;

	@ElementCollection
	@CollectionTable(name = "hotel_user", joinColumns = @JoinColumn(name = "hotel_id"))
	@Column(name = "user_id")
	private List<Integer> user_id = new ArrayList<Integer>();

	@NotNull
	@ElementCollection
	@CollectionTable(name = "hotel_tel", joinColumns = @JoinColumn(name = "hotel_id"))
	@Column(name = "tel")
	private Set<String> tel = new HashSet<>();

	@NotNull
	@ElementCollection
	@CollectionTable(name = "hotel_email", joinColumns = @JoinColumn(name = "hotel_id"))
	@Column(name = "email")
	private Set<String> email = new HashSet<>();

	public long getHotelId() {
		return hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public String getCountry() {
		return country;
	}

	public String getProvinceState() {
		return provinceState;
	}

	public String getDistrict() {
		return district;
	}

	public String getStreet() {
		return street;
	}

	public String getAdditionalDetail() {
		return additionalDetail;
	}

	public boolean getAvailible() {
		return availible;
	}

	public Set<String> getTel() {
		return tel;
	}

	public Set<String> getEmail() {
		return email;
	}

	public List<String> getHotelImages() {
		return hotelImages;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setProvinceState(String provinceState) {
		this.provinceState = provinceState;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setAdditionalDetail(String additionalDetail) {
		this.additionalDetail = additionalDetail;
	}

	public void setAvailible(boolean availible) {
		this.availible = availible;
	}

	public List<Integer> getUsers_id() {
		return user_id;
	}

	public void setUsers_id(List<Integer> users_id) {
		this.user_id = users_id;
	}

	public void setTel(Set<String> tel) {
		this.tel = tel;
	}

	public void setEmail(Set<String> email) {
		this.email = email;
	}

	public void setHotelImages(List<String> hotelImages) {
		this.hotelImages = hotelImages;
	}

	public void addHotelImages(String imageUrl) {
		this.hotelImages.add(imageUrl);
	}
}
