package com.worldturtlemedia.cyclecheck.data.api.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

/**
 * Complements @Enveloped by performing custom deserialization
 * for a response that is wrapped in a JSON Object.
 * @see Enveloped
 * @see https://medium.com/@naturalwarren/moshi-made-simple-jsonqualifier-b99559c826ad
 */
internal class EnvelopeFactory : JsonAdapter.Factory {

    companion object {
        val INSTANCE = EnvelopeFactory()
    }

    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<*>? {
        val delegateAnnotations = Types.nextAnnotations(
            annotations,
            Enveloped::class.java
        ) ?: return null
        val delegate = moshi.nextAdapter<Any>(
            this,
            type,
            delegateAnnotations
        )
        return EnvelopeJsonAdapter(delegate)
    }

    private class EnvelopeJsonAdapter(val delegate: JsonAdapter<*>) : JsonAdapter<Any>() {

        override fun fromJson(reader: JsonReader): Any? {
            reader.beginObject()
            reader.nextName()
            val envelope = delegate.fromJson(reader)
            reader.endObject()
            return envelope
        }

        override fun toJson(writer: JsonWriter?, value: Any?) = throw UnsupportedOperationException(
            "@Enveloped is only used to deserialize objects."
        )
    }
}
