package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.enumeration.Gender;
import cn.whuerbbs.backend.model.User;
import org.springframework.beans.BeanUtils;

public class UserDetailVO extends UserVO {
    private Gender gender;
    private String school;
    private String grade;
    private String diploma;

    public UserDetailVO(User user) {
        super(user);
        BeanUtils.copyProperties(user, this);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }
}
