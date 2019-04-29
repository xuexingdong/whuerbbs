package cn.whuerbbs.backend.dto;

import cn.whuerbbs.backend.enumeration.Gender;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class WxLoginDTO {
    @NotEmpty
    @Length(max = 255)
    private String code;
    @NotEmpty
    @Length(max = 255)
    private String nickName;
    @NotEmpty
    @Length(max = 255)
    private String avatarUrl;
    @NotNull
    private Gender gender;
    @NotNull
    @Length(max = 255)
    private String country;
    @NotNull
    @Length(max = 255)
    private String province;
    @NotNull
    @Length(max = 255)
    private String city;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
