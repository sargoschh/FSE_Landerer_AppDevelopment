package at.itkolleg.kalorienzaehler

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import at.itkolleg.kalorienzaehler.databinding.ItemMealsBinding

class MealAdapter(private val meals: MutableList<Meal>, private var daily: Daily, private val tblDailys: TableLayout) : RecyclerView.Adapter<MealAdapter.MealViewHolder> () {

    class MealViewHolder(val binding: ItemMealsBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvTblKcal: TextView = binding.root.findViewById(R.id.tvTblKcal)
        val tvTblFat: TextView = binding.root.findViewById(R.id.tvTblFat)
        val tvTblProtein: TextView = binding.root.findViewById(R.id.tvTblPro)
        val tvTblSugar: TextView = binding.root.findViewById(R.id.tvTblSug)
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MealViewHolder(binding)
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
        tblDailys.findViewById<TextView>(R.id.tvTblKcal).text = daily.kcal.toString()
        tblDailys.findViewById<TextView>(R.id.tvTblFat).text = daily.fat.toString()
        tblDailys.findViewById<TextView>(R.id.tvTblPro).text = daily.protein.toString()
        tblDailys.findViewById<TextView>(R.id.tvTblSug).text = daily.sugar.toString()
    }


    private fun toggleStrikeThrough(tvMeal: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvMeal.paintFlags = tvMeal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvMeal.paintFlags = tvMeal.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val currentTodo = meals[position]

        val mealDetails = holder.itemView.context.getString(
            R.string.meal_details,
            currentTodo.title,
            currentTodo.kcal,
            currentTodo.fat,
            currentTodo.protein,
            currentTodo.sugar
        )

        holder.binding.tvMealTitle.text = mealDetails

        holder.binding.cbDone.isChecked = currentTodo.isChecked

        holder.tvTblKcal.text = "Kcal: " + currentTodo.kcal
        holder.tvTblFat.text = "Fat: " + currentTodo.fat
        holder.tvTblProtein.text = "Protein: " + currentTodo.protein
        holder.tvTblSugar.text = "Sugar: " + currentTodo.sugar

        holder.binding.cbDone.setOnCheckedChangeListener { _, isChecked ->
            // Wenn der Checked-Status geändert wird, aktualisiert die Funktion den Durchstreichungsstatus des Texts und den isChecked-Wert des aktuellen Todos.
            toggleStrikeThrough(holder.binding.tvMealTitle, isChecked)
            currentTodo.isChecked = !currentTodo.isChecked
        }
    }


    override fun getItemCount() = meals.size

}