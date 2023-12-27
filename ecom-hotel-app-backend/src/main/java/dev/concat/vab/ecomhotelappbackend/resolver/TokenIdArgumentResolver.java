package dev.concat.vab.ecomhotelappbackend.resolver;

import dev.concat.vab.ecomhotelappbackend.annotations.TokenId;
import dev.concat.vab.ecomhotelappbackend.model.EcomToken;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class TokenIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(Long.class) &&
                methodParameter.hasParameterAnnotation(TokenId.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory
    ) {
        String tokenIdStr = nativeWebRequest.getParameter("tokenId");
        if (tokenIdStr != null) {
            return Long.parseLong(tokenIdStr);
        }
        return null;
    }
}