package at.itkolleg.kalorienzaehler

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import at.itkolleg.kalorienzaehler.Daily
import at.itkolleg.kalorienzaehler.Meal
import at.itkolleg.kalorienzaehler.R
import at.itkolleg.kalorienzaehler.databinding.ItemMealsBinding

class MealAdapter(
    private val meals: MutableList<Meal>,
    private var daily: Daily,
    private val tableLayout: TableLayout
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    class MealViewHolder(val binding: ItemMealsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.tvMealTitle.text = meal.title
            binding.cbDone.isChecked = meal.isChecked
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMealsBinding.inflate(inflater, parent, false)
        return MealViewHolder(binding)
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

        holder.binding.cbDone.setOnCheckedChangeListener { _, isChecked ->
            toggleStrikeThrough(holder.binding.tvMealTitle, isChecked)
            currentMeal.isChecked = isChecked
        }
    }

    override fun getItemCount() = meals.size

    private fun toggleStrikeThrough(tvMeal: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvMeal.paintFlags = tvMeal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvMeal.paintFlags = tvMeal.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    private fun updateTable() {
        tableLayout.removeAllViews()

        if (meals.isEmpty()) {
            tableLayout.visibility = View.VISIBLE
        } else {
            tableLayout.visibility = View.GONE
        }

        for (meal in meals) {
            val mealTextView = TextView(tableLayout.context)
            mealTextView.text = meal.title
            tableLayout.addView(mealTextView)
        }
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
}
