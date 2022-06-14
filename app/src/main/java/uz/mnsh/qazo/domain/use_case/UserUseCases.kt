package uz.mnsh.qazo.domain.use_case

data class UserUseCases(
    val addUser: AddUser,
    val getUser: GetUser,
    val addPrayer: AddPrayer,
    val updatePrayers: UpdatePrayers,
    val getPrayers: GetPrayers,
    val getLivePrayers: GetLivePrayers,
)