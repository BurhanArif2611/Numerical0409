package com.revauc.revolutionbuy.network.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import com.revauc.revolutionbuy.network.response.profile.CityDto;
import com.revauc.revolutionbuy.network.response.profile.LocationDto;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("soldCount")
    private int soldCount;

    @SerializedName("purchasedCount")
    private int purchasedCount;

    @SerializedName("email")
    private String email;

    @SerializedName("fbId")
    private String fbId;

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private Integer age;

    @SerializedName("image")
    private String image;

    @SerializedName("mobile")
    private String mobile;
    @SerializedName("username")
    private String username;

    @SerializedName("isMobileVerified")
    private int isMobileVerified;

    @SerializedName("isTempPassword")
    private int isTempPassword;

    @SerializedName("isSellerAccConnected")
    private int isSellerAccConnected;

    @SerializedName("isProfileComplete")
    private int isProfileComplete;

    @SerializedName("status")
    private int status;

    @SerializedName("loginType")
    private int loginType;

    @SerializedName("city")
    private LocationDto city;

    @SerializedName("imageName")
    private String imageName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(int soldCount) {
        this.soldCount = soldCount;
    }

    public int getPurchasedCount() {
        return purchasedCount;
    }

    public void setPurchasedCount(int purchasedCount) {
        this.purchasedCount = purchasedCount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIsMobileVerified() {
        return isMobileVerified;
    }

    public void setIsMobileVerified(int isMobileVerified) {
        this.isMobileVerified = isMobileVerified;
    }

    public int getIsTempPassword() {
        return isTempPassword;
    }

    public void setIsTempPassword(int isTempPassword) {
        this.isTempPassword = isTempPassword;
    }

    public int getIsSellerAccConnected() {
        return isSellerAccConnected;
    }

    public void setIsSellerAccConnected(int isSellerAccConnected) {
        this.isSellerAccConnected = isSellerAccConnected;
    }

    public int getIsProfileComplete() {
        return isProfileComplete;
    }

    public void setIsProfileComplete(int isProfileComplete) {
        this.isProfileComplete = isProfileComplete;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public LocationDto getCity() {
        return city;
    }

    public void setCity(LocationDto city) {
        this.city = city;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.soldCount);
        dest.writeInt(this.purchasedCount);
        dest.writeString(this.email);
        dest.writeString(this.fbId);
        dest.writeString(this.name);
        dest.writeValue(this.age);
        dest.writeString(this.image);
        dest.writeString(this.mobile);
        dest.writeString(this.username);
        dest.writeInt(this.isMobileVerified);
        dest.writeInt(this.isTempPassword);
        dest.writeInt(this.isSellerAccConnected);
        dest.writeInt(this.isProfileComplete);
        dest.writeInt(this.status);
        dest.writeInt(this.loginType);
        dest.writeParcelable(this.city, flags);
        dest.writeString(this.imageName);
    }

    public UserDto() {
    }

    protected UserDto(Parcel in) {
        this.id = in.readInt();
        this.soldCount = in.readInt();
        this.purchasedCount = in.readInt();
        this.email = in.readString();
        this.fbId = in.readString();
        this.name = in.readString();
        this.age = (Integer) in.readValue(Integer.class.getClassLoader());
        this.image = in.readString();
        this.mobile = in.readString();
        this.username = in.readString();
        this.isMobileVerified = in.readInt();
        this.isTempPassword = in.readInt();
        this.isSellerAccConnected = in.readInt();
        this.isProfileComplete = in.readInt();
        this.status = in.readInt();
        this.loginType = in.readInt();
        this.city = in.readParcelable(LocationDto.class.getClassLoader());
        this.imageName = in.readString();
    }

    public static final Parcelable.Creator<UserDto> CREATOR = new Parcelable.Creator<UserDto>() {
        @Override
        public UserDto createFromParcel(Parcel source) {
            return new UserDto(source);
        }

        @Override
        public UserDto[] newArray(int size) {
            return new UserDto[size];
        }
    };
}
