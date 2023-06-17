package at.itkolleg.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import at.itkolleg.todolist.databinding.ActivityMainBinding

// Definition der Hauptklasse "MainActivity"
class MainActivity : AppCompatActivity() {

    // Deklaration der Variablen "binding" und "todoAdapter"
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter

    // "onCreate" Funktion, die beim Start der App aufgerufen wird
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisieren des Adapters mit einer leeren Liste
        todoAdapter = TodoAdapter(mutableListOf())

        // Initialisieren des Bindings
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Festlegen des Layouts der App
        setContentView(binding.root)

        // Setzen des Adapters und des LayoutManagers für das RecyclerView
        binding.rvTodoItems.adapter = todoAdapter
        binding.rvTodoItems.layoutManager = LinearLayoutManager(this)

        // Setzen eines ClickListeners für den Hinzufügen-Button
        binding.btnAddTodo.setOnClickListener {

            // Extrahieren des Texts aus dem EditText
            val todoTitle = binding.etTodoTitle.text.toString()

            // Prüfen, ob der Text nicht leer ist
            if(todoTitle.isNotEmpty()) {

                // Erstellen eines neuen Todo-Objekts und Hinzufügen zum Adapter
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)

                // Löschen des Texts aus dem EditText
                binding.etTodoTitle.text.clear()
            }
        }

        // Setzen eines ClickListeners für den Löschen-Button
        binding.btnDeleteDoneTodos.setOnClickListener {

            // Aufrufen der Funktion zum Löschen aller erledigten Todos im Adapter
            todoAdapter.deleteDoneTodos()
        }
    }
}
