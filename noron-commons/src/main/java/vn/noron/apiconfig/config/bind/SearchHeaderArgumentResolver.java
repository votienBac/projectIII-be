package vn.noron.apiconfig.config.bind;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import vn.noron.apiconfig.config.bind.annotation.SearchRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class SearchHeaderArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(SearchRequest.class) != null;
    }

    @Override
    public String resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Map<String, String[]> param = request.getParameterMap();
        String keyword = "";
        if(param.containsKey("keyword") && !StringUtils.isEmpty(param.get("keyword")[0])){
            keyword = param.get("keyword")[0];
        }

        return keyword;
    }
}
