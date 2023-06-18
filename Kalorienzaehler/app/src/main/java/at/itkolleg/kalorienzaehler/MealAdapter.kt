package at.itkolleg.kalorienzaehler

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import at.itkolleg.kalorienzaehler.databinding.ItemMealsBinding
import at.itkolleg.kalorienzaehler.databinding.ItemTableBinding

class MealAdapter(private val meals: MutableList<Meal>, private var daily: Daily, private val tblDailys: TableLayout) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    class MealViewHolder(val binding: ItemMealsBinding, val tableLayout: TableLayout) : RecyclerView.ViewHolder(binding.root) {
        val tvTblKcal: TextView = tableLayout.findViewById(R.id.tvTblKcal)
        val tvTblFat: TextView = tableLayout.findViewById(R.id.tvTblFat)
        val tvTblProtein: TextView = tableLayout.findViewById(R.id.tvTblPro)
        val tvTblSugar: TextView = tableLayout.findViewById(R.id.tvTblSug)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMealsBinding.inflate(inflater, parent, false)
        val tableLayoutBinding = ItemTableBinding.inflate(inflater, parent, false)
        val clItem = tableLayoutBinding.root as ConstraintLayout

        val tableLayout = clItem.findViewById<TableLayout>(R.id.tblDailys)

        binding.root.addView(clItem)

        return MealViewHolder(binding, tableLayout)
    }

    fun addMeal(meal: Meal) {
        meals.add(meal)
        daily.kcal += meal.kcal
        daily.fat += meal.fat
        daily.protein += meal.protein
        daily.sugar += meal.sugar

        notifyItemInserted(meals.size - 1)
        updateTable()
    }

    fun deleteWrongMeals() {
        val iterator = meals.iterator()
        while (iterator.hasNext()) {
            val meal = iterator.next()
            if (meal.isChecked) {
                daily.kcal -= meal.kcal
                daily.fat -= meal.fat
                daily.protein -= meal.protein
                daily.sugar -= meal.sugar
                iterator.remove()
            }
        }
        notifyDataSetChanged()
        updateTable()
    }

    private fun updateTable() {
        // Aktualisiere die Werte in der Tabelle
        tblDailys.findViewById<TextView>(R.id.tvTblKcal).text = "%2d".format(daily.kcal)
        tblDailys.findViewById<TextView>(R.id.tvTblFat).text = "%.2f".format(daily.fat)
        tblDailys.findViewById<TextView>(R.id.tvTblPro).text = "%.2f".format(daily.protein)
        tblDailys.findViewById<TextView>(R.id.tvTblSug).text = "%.2f".format(daily.sugar)
    }

    private fun toggleStrikeThrough(tvMeal: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvMeal.paintFlags = tvMeal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvMeal.paintFlags = tvMeal.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val currentMeal = meals[position]

        val mealDetails = holder.itemView.context.getString(
            R.string.meal_details,
            currentMeal.title,
            currentMeal.kcal,
            currentMeal.fat,
            currentMeal.protein,
            currentMeal.sugar
        )

        holder.binding.tvMealTitle.text = mealDetails
        holder.binding.cbDone.isChecked = currentMeal.isChecked

        holder.tvTblKcal.text = "Kcal: %2d".format(currentMeal.kcal)
        holder.tvTblFat.text = "Fat: %.2f".format(currentMeal.fat)
        holder.tvTblProtein.text = "Protein: %.2f".format(currentMeal.protein)
        holder.tvTblSugar.text = "Sugar: %.2f".format(currentMeal.sugar)

        holder.binding.cbDone.setOnCheckedChangeListener { _, isChecked ->
            toggleStrikeThrough(holder.binding.tvMealTitle, isChecked)
            currentMeal.isChecked = isChecked
        }
    }

    override fun getItemCount() = meals.size
}
