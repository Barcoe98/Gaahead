package ie.wit.models

interface FixtureStore {
    fun findAll() : List<FixtureModel>
    fun findById(id: Long) : FixtureModel?
    fun create(fixture: FixtureModel)
}