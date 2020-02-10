package ie.wit.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ie.wit.models.FixtureModel
import ie.wit.models.FixtureStore
import org.jetbrains.anko.AnkoLogger
import org.wit.helpers.*
import java.util.*

val JSON_FILE_result = "results.json"
val resultGsonBuilder = GsonBuilder().setPrettyPrinting().create()
val resultListType = object : TypeToken<ArrayList<FixtureModel>>() {}.type

fun generateRandomRid(): Long {
    return Random().nextLong()
}

class ResultJSONStore : ResultStore, AnkoLogger {

    val context: Context
    var results = mutableListOf<ResultModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE_result)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<ResultModel> {
        return results
    }

    override fun create(result: ResultModel) {
        result.rId = generateRandomRid()
        results.add(result)
        serialize()
    }


    fun update(result: ResultModel) {
        // todo
    }

    private fun serialize() {
        val jsonString = resultGsonBuilder.toJson(results, resultListType)
        write(context, JSON_FILE_result, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE_result)
        results = Gson().fromJson(jsonString, resultListType)
    }
}