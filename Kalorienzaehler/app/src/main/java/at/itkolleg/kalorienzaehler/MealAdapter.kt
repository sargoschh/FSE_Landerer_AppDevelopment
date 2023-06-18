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

    class MealViewHolder(val binding: ItemMealsBinding, val tvTblKcal: TextView, val tvTblFat: TextView, val tvTblProtein: TextView, val tvTblSugar: TextView) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val tvTblKcal = binding.root.findViewById<TextView>(R.id.tvTblKcal)
        val tvTblFat = binding.root.findViewById<TextView>(R.id.tvTblFat)
        val tvTblProtein = binding.root.findViewById<TextView>(R.id.tvTblPro)
        val tvTblSugar = binding.root.findViewById<TextView>(R.id.tvTblSug)

        return MealViewHolder(binding, tvTblKcal, tvTblFat, tvTblProtein, tvTblSugar)
    }


    fun addMeal(meal: Meal) {
        meals.add(meal)
        daily.kcal += meal.kcal
        daily.fat += meal.fat
        daily.protein += meal.protein
        daily.sugar += meal.sugar

        notifyItemInserted(meals.size - 1)
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

        holder.tvTblKcal.text = daily.kcal.toString()
        holder.tvTblFat.text = daily.fat.toString()
        holder.tvTblProtein.text = daily.protein.toString()
        holder.tvTblSugar.text = daily.sugar.toString()

        holder.binding.cbDone.setOnCheckedChangeListener { _, isChecked ->
            // Wenn der Checked-Status geändert wird, aktualisiert die Funktion den Durchstreichungsstatus des Texts und den isChecked-Wert des aktuellen Todos.
            toggleStrikeThrough(holder.binding.tvMealTitle, isChecked)
            currentTodo.isChecked = !currentTodo.isChecked
        }
    }


    override fun getItemCount() = meals.size

}