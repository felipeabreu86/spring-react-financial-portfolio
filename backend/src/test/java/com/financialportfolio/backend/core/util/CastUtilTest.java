package com.financialportfolio.backend.core.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CastUtilTest {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List getRawListWithMixedTypes() {
        List result = new ArrayList();
        result.add("I am the 1st String.");
        result.add("I am the 2nd String.");
        result.add("I am the 3rd String.");
        result.add(new Date());
        return result;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List getRawListWithStringType() {
        List result = new ArrayList();
        result.add("I am the 1st String.");
        result.add("I am the 2nd String.");
        result.add("I am the 3rd String.");
        return result;
    }

    @Test
    public void givenRawList_whenAssignToTypedListAfterCallingCastList_shouldOnlyHaveElementsWithExpectedType() {
        @SuppressWarnings("rawtypes")
        List rawList = getRawListWithMixedTypes();
        List<String> strList = CastUtil.castList(String.class, rawList);
        Assertions.assertEquals(4, rawList.size());
        Assertions.assertEquals(3, strList.size());
        Assertions.assertTrue(strList.stream().allMatch(el -> el.endsWith("String.")));
    }

    @Test
    public void givenRawList_whenAssignToTypedListAfterCallingCastList_shouldOnlyHaveElementsWithStringType() {
        @SuppressWarnings("rawtypes")
        List rawList = getRawListWithStringType();
        List<String> strList = CastUtil.castList(String.class, rawList);
        Assertions.assertEquals(3, rawList.size());
        Assertions.assertEquals(3, strList.size());
        Assertions.assertTrue(strList.stream().allMatch(el -> el.endsWith("String.")));
    }

}
