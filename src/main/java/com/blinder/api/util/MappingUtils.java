package com.blinder.api.util;

import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class MappingUtils {
    public static <T> Set<String> getNullPropertyNames(T object) {
        final BeanWrapper src = new BeanWrapperImpl(object);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }

            if(getNullPropertyNamesOnes(srcValue).size() == getNumberOfProperties(srcValue))
                emptyNames.add(pd.getName());
        }
        return emptyNames;
    }

    public static <T> Set<String> getNullPropertyNamesOnes(T object) {
        final BeanWrapper src = new BeanWrapperImpl(object);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames;
    }

    //get number of properties
    public static <T> int getNumberOfProperties(T object) {
        final BeanWrapper src = new BeanWrapperImpl(object);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        return pds.length;
    }


}
