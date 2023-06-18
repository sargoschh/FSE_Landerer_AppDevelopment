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
            val mealKcal = binding.etKcal.text.toString()

        }

        // Weitere Initialisierungen und Funktionen...

    }
}
