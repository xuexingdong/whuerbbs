package cn.whuerbbs.backend.dto;

import javax.validation.constraints.NotEmpty;

public class ModifySchoolDTO {
    @NotEmpty
    private String school;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
