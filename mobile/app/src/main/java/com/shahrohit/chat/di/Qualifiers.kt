package com.shahrohit.chat.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Authenticated

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Unauthenticated