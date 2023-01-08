package com.iot.rashome.commons.util;

import org.apache.commons.lang3.StringUtils;

public class ParameterCheckUtil {

    public static Boolean isStringHeaderNumeric(String parameter) {
        String trimedParameter = StringUtils.trimToNull(parameter);
        return StringUtils.isNotEmpty(trimedParameter) & StringUtils.isNumeric(trimedParameter);
    }
}
