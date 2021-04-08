package uz.bdmgroup.onlineshop

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.orhanobut.hawk.Hawk
import uz.bdmgroup.onlineshop.db.AppDatabese

class App: MultiDexApplication(){
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        Hawk.init(this).build()
        AppDatabese.initDatabase(this)
        //Comment
    }

}