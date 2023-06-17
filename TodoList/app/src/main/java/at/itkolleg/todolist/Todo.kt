package at.itkolleg.todolist

// Definition der Datenklasse "Todo"
data class Todo (
    // Deklaration der Eigenschaft "title" vom Typ String
    val title: String,

    // Deklaration der Eigenschaft "isChecked" vom Typ Boolean mit einem Standardwert von false
    var isChecked: Boolean = false
)
