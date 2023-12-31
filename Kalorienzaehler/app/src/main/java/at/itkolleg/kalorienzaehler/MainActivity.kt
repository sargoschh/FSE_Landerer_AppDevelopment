package at.itkolleg.kalorienzaehler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TableLayout
import androidx.recyclerview.widget.LinearLayoutManager
import at.itkolleg.kalorienzaehler.Daily
import at.itkolleg.kalorienzaehler.Meal
import at.itkolleg.kalorienzaehler.R
import at.itkolleg.kalorienzaehler.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mealAdapter: MealAdapter
    private lateinit var mealsList: MutableList<Meal>
    private lateinit var daily: Daily
    private lateinit var tblDailys: TableLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        daily = Daily()
        tblDailys = binding.includedTableLayout.tblDailys

        mealsList = mutableListOf()

        mealAdapter = MealAdapter(mealsList, daily, tblDailys)

        binding.rvTodoItems.layoutManager = LinearLayoutManager(this)
        binding.rvTodoItems.adapter = mealAdapter

        binding.btnAdd.setOnClickListener {
            val mealTitle = binding.etMeal.text.toString()
            val mealKcal = binding.etKcal.text.toString().toIntOrNull()
            val mealFat = binding.etFat.text.toString().toDoubleOrNull()
            val mealPro = binding.etProtein.text.toString().toDoubleOrNull()
            val mealSug = binding.etSugar.text.toString().toDoubleOrNull()

            if(mealTitle.isNotEmpty() && mealKcal != null && mealFat != null && mealPro != null && mealSug != null) {
                val meal = Meal(mealTitle, mealKcal, mealFat, mealPro, mealSug)

                mealAdapter.addMeal(meal)

                binding.etMeal.text.clear()
                binding.etKcal.text.clear()
                binding.etFat.text.clear()
                binding.etProtein.text.clear()
                binding.etSugar.text.clear()
            } else {
                Log.d("MainActivity", "Invalid input")
            }
        }

        binding.btnDelete.setOnClickListener {
            mealAdapter.deleteWrongMeals()
        }
    }
}
