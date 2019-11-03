package sop.project.booking.model.extendModel.requestModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hotel  {
    private long hotelId;
    private String hotelName;
    private String country;
    private String provinceState;
    private String district;
    private String street;
    private List<String> hotelImages = new ArrayList<String>();
    private String additionalDetail;
    private boolean availible;
    private Set<String> tel = new HashSet<>();
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

    public void setTel(Set<String> tel) {
        this.tel = tel;
    }

    public void setEmail(Set<String> email) {
        this.email = email;
    }

    public void setHotelImages(List<String> hotelImages) {
        this.hotelImages = hotelImages;
    }

    public void addHotelImages(String imageUrl){
        this.hotelImages.add(imageUrl);
    }
}
