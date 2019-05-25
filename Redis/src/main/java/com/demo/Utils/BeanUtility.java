package com.demo.Utils;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

import java.beans.FeatureDescriptor;
import java.math.BigDecimal;
import java.util.stream.Stream;

/**
 * @author Anson
 * @date 2019/5/25
 */
public class BeanUtility {

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null).toArray(String[]::new);
    }

    public static void copyProperties(final Object dest, final Object src) {
        Assert.isTrue(dest != null, "dest can not be null.");
        Assert.isTrue(src != null, "src can not be null.");
        BigDecimalConverter bd = new BigDecimalConverter(BigDecimal.ZERO);
        ConvertUtils.register(bd, java.math.BigDecimal.class);
        BeanUtils.copyProperties(src, dest, getNullPropertyNames(src));
    }

    public static String getWorkingDirectory() {
        String url = BeanUtility.class.getProtectionDomain().getCodeSource().getLocation().toString();
        int index = url.indexOf("jar!/");
        if (index > -1) {
            url = url.substring(0, index);
            index = url.lastIndexOf('/');
            url = url.substring(0, index);
        }
        url = url.replaceFirst("(jar:)?file:/", "/");
        return url;
    }
}
