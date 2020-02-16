package ie.wit.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.helpers.*
import java.util.*

val JSON_FILE_result = "results.json"
val resultGsonBuilder = GsonBuilder().setPrettyPrinting().create()
val resultListType = object : TypeToken<ArrayList<ResultModel>>() {}.type

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

    override fun update(result: ResultModel) {

        val resultsList = findAll() as ArrayList<ResultModel>
        var foundResult: ResultModel? = resultsList.find { p -> p.rId == result.rId }
        if (foundResult != null) {
            foundResult.teamAName = result.teamAName
            foundResult.teamBName = result.teamBName
            foundResult.teamAScore = result.teamAScore
            foundResult.teamBScore = result.teamBScore
            foundResult.type = result.type
        }
        serialize()
    }

    override fun remove(result: ResultModel) {
        results.remove(result)
        serialize()
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