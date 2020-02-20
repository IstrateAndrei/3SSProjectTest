package com.project.test_3ss.data.models.realmModels

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LocationRealmModel : RealmObject() {

    @PrimaryKey
    open var id: Int = 0
    open var name: String = ""
    open var description: String = ""
    open var weatherMain: String = ""
    open var temperature: Double = 0.0

}