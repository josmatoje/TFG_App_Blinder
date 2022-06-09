package es.iesnervion.jmmata.blinder

import android.app.Application

class BlinderApplication :Application() {
    companion object {
        lateinit var instance: BlinderApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}