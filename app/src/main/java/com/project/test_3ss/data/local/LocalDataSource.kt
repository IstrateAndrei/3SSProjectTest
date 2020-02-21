package com.project.test_3ss.data.local

import com.project.test_3ss.data.models.realmModels.LocationRealmModel
import com.project.test_3ss.data.models.response.WeatherResponse
import io.realm.Realm
import org.koin.standalone.KoinComponent

class LocalDataSource : KoinComponent {

    private var mRealm: Realm? = null

    init {
        mRealm = Realm.getDefaultInstance()
    }


    fun saveLocalLocation(location: LocationRealmModel) {
        if (!isLocationSaved(location.id)) {
            mRealm?.let {
                if (!it.isInTransaction) it.beginTransaction()
                it.copyToRealm(location)
                it.commitTransaction()
            }
        }
    }

    fun removeLocalLocation(locationName: String) {
        mRealm?.let {
            if (!it.isInTransaction) it.beginTransaction()
            val mQuery =
                mRealm!!.where(LocationRealmModel::class.java).contains("name", locationName)
            val results = mQuery.findAll()
            results.deleteAllFromRealm()
            it.commitTransaction()
        }
    }

    fun getLocalLocations(): MutableList<LocationRealmModel> {
        return mRealm?.where(LocationRealmModel::class.java)!!.findAll()
    }

    fun isLocationSaved(locationId: Int): Boolean {
        mRealm?.let {
            if (!it.isInTransaction) it.beginTransaction()
            val results = it.where(LocationRealmModel::class.java).findAll()
            it.commitTransaction()
            if (results.any { item -> locationId == item.id }) return true
        }
        return false
    }
}