package cn.whuerbbs.backend.controller.v1;

import cn.whuerbbs.backend.common.AnonymousNameConstants;
import cn.whuerbbs.backend.common.SchoolConstants;
import cn.whuerbbs.backend.enumeration.Campus;
import cn.whuerbbs.backend.enumeration.TradeCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ConstantControllerV1 {

    @GetMapping("schools")
    public List<String> getSchools() {
        return SchoolConstants.SCHOOL_LIST;
    }

    @GetMapping("grades")
    public Map<String, List<String>> getGradesAndDiplomas() {
        return Map.of("grade", SchoolConstants.GRADE_LIST, "diploma", SchoolConstants.DIPLOMA_LIST);
    }

    @GetMapping("anonymous_names")
    public List<String> getAnonymousNames() {
        return AnonymousNameConstants.ANONYMOUS_NAME_LIST;
    }

    @GetMapping("trade_categories")
    public Map<TradeCategory, String> getTradeCategories() {
        var result = new HashMap<TradeCategory, String>();
        for (TradeCategory tradeCategory : TradeCategory.values()) {
            result.put(tradeCategory, tradeCategory.getTitle());
        }
        return result;
    }

    @GetMapping("campuses")
    public Map<Campus, String> getCampuses() {
        var result = new HashMap<Campus, String>();
        for (Campus campus : Campus.values()) {
            result.put(campus, campus.getTitle());
        }
        return result;
    }
}