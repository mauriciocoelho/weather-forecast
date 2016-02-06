package com.mauscoelho.controllers.util;


public final class ArrayUtils {

    public static Object resizeArray(Object array, int newSize) {
        int oldSize = java.lang.reflect.Array.getLength(array);
        Class elementType = array.getClass().getComponentType();
        Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);
        int preserveLength = Math.min(oldSize, newSize);
        if (preserveLength > 0) System.arraycopy(array, 0, newArray, 0, preserveLength);
        return newArray;
    }

}
