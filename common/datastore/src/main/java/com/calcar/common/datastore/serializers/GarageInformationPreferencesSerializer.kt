package com.calcar.common.datastore.serializers

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.calcar.common.datastore.GarageInformationPreferences
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

class GarageInformationPreferencesSerializer : Serializer<GarageInformationPreferences> {
    override val defaultValue: GarageInformationPreferences =
        GarageInformationPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): GarageInformationPreferences =
        try {
            GarageInformationPreferences.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read GarageInformationPreferences.", e)
        }

    override suspend fun writeTo(t: GarageInformationPreferences, output: OutputStream) {
        t.writeTo(output)
    }
}