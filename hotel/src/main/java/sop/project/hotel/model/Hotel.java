package sop.project.hotel.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private String street;

    @Column(columnDefinition = "text")
    private String additionalDetail;

    @NotNull
    private boolean isAvailible;

    private String tel;

    private String email;


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

    public String getStreet() {
        return street;
    }

    public String getAdditionalDetail() {
        return additionalDetail;
    }

    public boolean isAvailible() {
        return isAvailible;
    }

    public String getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
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

    public void setStreet(String street) {
        this.street = street;
    }

    public void setAdditionalDetail(String additionalDetail) {
        this.additionalDetail = additionalDetail;
    }

    public void setAvailible(boolean availible) {
        isAvailible = availible;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
