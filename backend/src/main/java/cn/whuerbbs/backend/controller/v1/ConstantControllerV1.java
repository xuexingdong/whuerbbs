package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.common.SchoolConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ConstantControllerV1 {

    @GetMapping("schools")
    public String[] getSchools() {
        return SchoolConstants.SCHOOL_LIST;
    }

    @GetMapping("grades")
    public Map<String, String[]> getGradesAndDiplomas() {
        return Map.of("grade", SchoolConstants.GRADE_LIST, "diploma", SchoolConstants.DIPLOMA_LIST);
    }
}
