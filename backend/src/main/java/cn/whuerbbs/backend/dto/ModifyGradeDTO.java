package cn.whuerbbs.backend.dto;

import javax.validation.constraints.NotEmpty;

public class ModifyGradeDTO {
    @NotEmpty
    private String grade;
    @NotEmpty
    private String diploma;

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
