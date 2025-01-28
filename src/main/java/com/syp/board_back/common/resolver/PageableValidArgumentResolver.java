package com.syp.board_back.common.resolver;

import com.syp.board_back.common.exception.PageException;
import com.syp.board_back.common.util.StringToNumber;
import com.syp.board_back.dto.common.response.ResponseCode;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class PageableValidArgumentResolver extends PageableHandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return super.supportsParameter(parameter);
    }

    @Override
    public Pageable resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        String page = webRequest.getParameter("page");
        String size = webRequest.getParameter("size");

        if (page != null && !StringToNumber.strToNum(page)) {
            throw new PageException(ResponseCode.PAGE_VALIDATION_ERROR);
        }

        if (size != null && !StringToNumber.strToNum(size)) {
            throw new PageException(ResponseCode.PAGE_VALIDATION_ERROR);
        }

        if (size != null && Integer.parseInt(size) > 100) {
            throw new PageException(ResponseCode.PAGE_VALIDATION_ERROR);
        }

        return super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
    }
}
