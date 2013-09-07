package com.alexkorovyansky.carwashfor;

import timber.log.Timber;

/**
 * TimberInjector
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class TimberInjector {
    
    public static Timber inject() {
        return BuildConfig.DEBUG ? Timber.DEBUG : Timber.PROD;
    }
    
    private TimberInjector() {
        
    }
}
