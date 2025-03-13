package com.jutt.frinterview

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom Application class for the FR Interview app.
 *
 * This class serves as the entry point for the application and is annotated with
 * [HiltAndroidApp] to enable dependency injection using Dagger Hilt throughout the app.
 *
 * The application class is registered in the AndroidManifest.xml file and is instantiated
 * when the app process is created.
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
@HiltAndroidApp
class FRInterviewApp : Application()
