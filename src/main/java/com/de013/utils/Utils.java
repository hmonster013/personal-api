package com.de013.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.de013.model.Paging;

public class Utils {
	public static boolean isNotEmpty(Object str) {
        if (str == null || str.toString().trim().length() == 0 || str.toString().trim().equalsIgnoreCase(null)) {
            return false;
        } else {
            return true;
        }
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
}
