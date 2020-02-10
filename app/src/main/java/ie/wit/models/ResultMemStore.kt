package ie.wit.models

import android.util.Log

var rlastId = 0L

internal fun getrId(): Long {
    return rlastId++
}

class ResultMemStore : ResultStore {

    val results = ArrayList<ResultModel>()

    override fun findAll(): List<ResultModel> {
        return results
    }

     fun findById(rId:Long) : ResultModel? {
        val foundResult: ResultModel? = results.find { it.rId == rId }
        return foundResult
    }

    override fun create(result: ResultModel) {
        result.rId = getrId()
        results.add(result)
        logAll()
    }

    fun logAll() {
        Log.v("Result","** Results List **")
        results.forEach { Log.v("Result","${it}") }
    }
}