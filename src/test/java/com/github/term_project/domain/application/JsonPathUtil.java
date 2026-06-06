package com.github.term_project.domain.application;

import com.jayway.jsonpath.JsonPath;

public final class JsonPathUtil {

    private JsonPathUtil() {
    }

    public static Object read(String json, String expression) {
        return JsonPath.read(json, expression);
    }
}
