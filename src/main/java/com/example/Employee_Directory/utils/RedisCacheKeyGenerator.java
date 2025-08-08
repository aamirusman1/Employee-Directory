package com.example.Employee_Directory.utils;

import org.springframework.stereotype.Component;

@Component
public class RedisCacheKeyGenerator {
    private static final String APP_PREFIX = "employee-app";
    private static final String SEPARATOR = ":";

    // Employee cache keys
    public String getAllEmployeesKey() {
        return APP_PREFIX + SEPARATOR + "employees" + SEPARATOR + "all";
    }

    public String getEmployeeByIdKey(Integer id) {
        return APP_PREFIX + SEPARATOR + "employee" + SEPARATOR + id;
    }
}
