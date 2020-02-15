package ie.wit.models

interface FixtureStore {
    fun findAll() : List<FixtureModel>
    //fun findById(fId: Long) : FixtureModel?
    fun create(fixture: FixtureModel)
    fun remove(fixture: FixtureModel)
    //fun length(fixture: FixtureModel)
    fun update(fixture: FixtureModel)

}