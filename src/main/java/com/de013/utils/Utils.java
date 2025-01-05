package com.de013.utils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.data.domain.Page;

import com.de013.model.Paging;

import ch.qos.logback.classic.pattern.Util;

public class Utils {
	public static boolean isNotEmpty(Object str) {
        if (str == null || str.toString().trim().length() == 0 || str.toString().trim().equalsIgnoreCase(null)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isEmpty(Object str) {
        return !isNotEmpty(str);
    }

    public static Map<String, Object> responseMap(List<?> responseList, Page result, Long total) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (result.getSize() != 1) {
            map.put(JConstants.DATA_LIST, responseList);
        }
        map.put(JConstants.DATA_TOTAL, total);

        Paging paging = new Paging(result.getNumber() + 1, result.getSize());
        paging.setTotalPages(result.getTotalPages());
        paging.setTotalRows(result.getTotalElements());
        map.put(Paging.class.getSimpleName().toLowerCase(), paging);
        return map;
    }

    public static Map<String, Object> responseMap(List<?> responseList, Page result) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (result.getSize() != 1) {
            map.put(JConstants.DATA_LIST, responseList);
        }
        Paging paging = new Paging(result.getNumber() + 1, result.getSize());
        paging.setTotalPages(result.getTotalPages());
        paging.setTotalRows(result.getTotalElements());
        map.put(Paging.class.getSimpleName().toLowerCase(), paging);
        return map;
    }

    public static void copyNonNullProperties(Object source, Object destination) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            String name = pd.getName();
            if (name.equals("request") || name.equals("response")) {
                continue;
            }
            try {
                Object srcValue = src.getPropertyValue(name);
                if (Utils.isEmpty(srcValue) || srcValue.toString().contains("***")) {
                    emptyNames.add(name);
                }
            } catch (InvalidPropertyException e) {
                emptyNames.add(name);
                continue;
            }
        }
        String[] result = new String[emptyNames.size()];
        String[] ignoreProperties = emptyNames.toArray(result);
        BeanUtils.copyProperties(source, destination, ignoreProperties);
    }
}
