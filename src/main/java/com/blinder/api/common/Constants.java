package com.blinder.api.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final long SEVEN_DAYS = 1000 * 60 * 60 * 24 * 7;
    public static final String CHAT_API_URL = "http://51.20.75.47:5000";
    public static final String CHAT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY1NzBmZmM5NTM3ZmRkZDI2M2M1MTc3NCIsImlhdCI6MTcwMTkwNDMyOSwiZXhwIjo0ODU1NTA0MzI5fQ._YwFSojmr_w3CmmlMizSKB5qoBNOGXLs0de5xHVz4P4";

    public static class Roles{
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String TRAINER = "ROLE_TRAINER";
    }
}

