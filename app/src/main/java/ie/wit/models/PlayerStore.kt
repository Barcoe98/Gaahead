package ie.wit.models

interface PlayerStore {
    fun findAll() : List<PlayerModel>
    //fun findById(fId: Long) : PlayerModel?
    fun create(player: PlayerModel)
    fun remove(player: PlayerModel)
    //fun length(player: PlayerModel)
    fun update(player: PlayerModel)

}