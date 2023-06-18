package at.itkolleg.kalorienzaehler

class Meal (
    val title: String,
    val kcal: Int,
    val fat: Double,
    val protein: Double,
    val sugar: Double,
    var isChecked: Boolean = false
        )