package com.chinasofti.test;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {
    static final long MB = 1024 * 1024;
    public static void main(String[] args) {
        OperatingSystemMXBean system = ManagementFactory.getOperatingSystemMXBean();
        long totalPhysicalMemory = getLongFromOperatingSystem(system, "getTotalPhysicalMemorySize");
        long freePhysicalMemory = getLongFromOperatingSystem(system, "getFreePhysicalMemorySize");
        long usedPhysicalMemorySize = totalPhysicalMemory - freePhysicalMemory;

        System.out.println("总物理内存(M):" + totalPhysicalMemory / MB);
        System.out.println("已用物理内存(M):" + usedPhysicalMemorySize / MB);
        System.out.println("剩余物理内存(M):" + freePhysicalMemory / MB);
    }
    private static long getLongFromOperatingSystem(OperatingSystemMXBean operatingSystem, String methodName) {
        try {
            final Method method = operatingSystem.getClass().getMethod(methodName,
                    (Class<?>[]) null);
            method.setAccessible(true);
            return (Long) method.invoke(operatingSystem, (Object[]) null);
        } catch (final InvocationTargetException e) {
            if (e.getCause() instanceof Error) {
                throw (Error) e.getCause();
            } else if (e.getCause() instanceof RuntimeException) {
                throw (RuntimeException) e.getCause();
            }
            throw new IllegalStateException(e.getCause());
        } catch (final NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
