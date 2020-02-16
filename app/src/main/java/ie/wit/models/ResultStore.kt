package ie.wit.models

interface ResultStore {
    fun findAll() : List<ResultModel>
    //fun findById(id: Long) : ResultModel?
    fun create(result: ResultModel)
    fun remove(result: ResultModel)
    fun update(result: ResultModel)
}