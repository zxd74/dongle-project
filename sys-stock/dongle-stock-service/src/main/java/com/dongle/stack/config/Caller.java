package com.dongle.stack.config;

/**
 * @author Dongle
 * @desc
 * @since 2023/9/9 009 13:46
 */
@FunctionalInterface
public interface Caller<T>  {
    T apply();
}
