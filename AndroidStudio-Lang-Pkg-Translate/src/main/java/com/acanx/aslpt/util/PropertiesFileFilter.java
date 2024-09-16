package com.acanx.aslpt.util;

import java.io.File;
import java.io.FileFilter;

public class PropertiesFileFilter implements FileFilter {

    /**
     * Tests whether or not the specified abstract pathname should be
     * included in a pathname list.
     *
     * @param pathname The abstract pathname to be tested
     * @return {@code true} if and only if {@code pathname}
     * should be included
     */
    @Override
    public boolean accept(File pathname) {
        return pathname.getName().toLowerCase().endsWith(".properties");
    }
}
