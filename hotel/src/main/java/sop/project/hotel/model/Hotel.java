package sop.project.hotel.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
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
    @Size(min = 1, max=100)
    private String country;

    @NotNull
    @Size(min = 1, max=100)
    private String provinceState;

    @NotNull
    @Size(min = 1, max=100)
    private String district;

    @NotNull
    @Size(min = 1, max=100)
    private String street;

    @Column(columnDefinition = "text")
    private String additionalDetail;

    @NotNull
    private boolean availible;

    @ElementCollection
    @CollectionTable(
            name = "hotel_tel",
            joinColumns = @JoinColumn(name = "hotel_id")
    )
    @Column(name = "tel")
    private Set<String> tel = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "hotel_email",
            joinColumns = @JoinColumn(name = "hotel_id")
    )
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

    public void setTel(Set<String> tel) {
        this.tel = tel;
    }

    public void setEmail(Set<String> email) {
        this.email = email;
    }
}
