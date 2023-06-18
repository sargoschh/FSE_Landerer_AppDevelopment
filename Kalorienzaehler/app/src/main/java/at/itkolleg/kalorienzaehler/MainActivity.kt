package at.itkolleg.kalorienzaehler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
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
        tblDailys = findViewById(R.id.tblDailys)

        mealsList = mutableListOf() // Leere Liste erstellen

        mealAdapter = MealAdapter(mealsList, daily, tblDailys)

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
            }
        }

        binding.btnDelete.setOnClickListener {
            mealAdapter.deleteWrongMeals()
        }

        // Weitere Initialisierungen und Funktionen...

    }
}
