package sop.project.booking.model.extendModel.requestModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private long hotelId;
    private String hotelName;
    private String country;
    private String provinceState;
    private String district;
    private String street;
    private List<String> hotelImages = new ArrayList<String>();
    private String additionalDetail;
    private boolean availible;
    private List<Integer> user_id = new ArrayList<Integer>();
    private List<String> tel = new ArrayList<>();
    private List<String> email = new ArrayList<>();

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

    public List<String> getTel() {
        return tel;
    }

    public List<String> getEmail() {
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

    public void setTel(List<String> tel) {
        this.tel = tel;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public void setHotelImages(List<String> hotelImages) {
        this.hotelImages = hotelImages;
    }

    public void addHotelImages(String imageUrl) {
        this.hotelImages.add(imageUrl);
    }
}
