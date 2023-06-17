package at.itkolleg.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import at.itkolleg.todolist.databinding.ItemTodoBinding

// Der TodoAdapter ist spezialisiert auf die Verwaltung der Darstellung von Todo-Objekten in einer RecyclerView.
class TodoAdapter(private val todos: MutableList<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    // Die innere Klasse TodoViewHolder definiert, wie eine einzelne Ansicht (ein "Todo-Element") in der RecyclerView aussieht.
    class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    // Die Methode onCreateViewHolder wird aufgerufen, wenn ein neues Ansichtselement (ViewHolder) für die Darstellung von Daten erstellt wird.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        // Hier wird das Layout für ein einzelnes Todo-Element aufgeblasen (aus einer XML-Layout-Datei in ein tatsächliches Ansichtsobjekt umgewandelt).
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // Erstellt und gibt einen neuen ViewHolder zurück, der das aufgeblasene Layout enthält.
        return TodoViewHolder(binding)
    }

    // Diese Methode fügt ein neues Todo zur Liste hinzu und informiert den Adapter über die Änderung.
    fun addTodo(todo: Todo) {
        todos.add(todo)
        // Informiert den Adapter, dass ein neues Element eingefügt wurde, damit er das Layout aktualisieren kann.
        notifyItemInserted(todos.size - 1)
    }

    // Diese Methode entfernt alle erledigten Todos aus der Liste und informiert den Adapter über die Änderung.
    fun deleteDoneTodos() {
        todos.removeAll { todo ->
            todo.isChecked
        }
        // Informiert den Adapter, dass die Daten geändert wurden, damit er das Layout aktualisieren kann.
        notifyDataSetChanged()
    }

    // Diese private Methode wird verwendet, um den Text eines Todo durchzustreichen, wenn es als erledigt markiert ist, und die Durchstreichung zu entfernen, wenn es als unerledigt markiert ist.
    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    // Die Methode onBindViewHolder wird aufgerufen, um die Daten eines bestimmten Todos in einem ViewHolder darzustellen.
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        // Es wird das Todo an der gegebenen Position in der Todo-Liste abgerufen.
        val currentTodo = todos[position]
        // Setzt den Text des TextViews in diesem ViewHolder auf den Titel des aktuellen Todos.
        holder.binding.tvTodoTitle.text = currentTodo.title
        // Setzt den Checked-Status der CheckBox in diesem ViewHolder auf den isChecked-Wert des aktuellen Todos.
        holder.binding.cbDone.isChecked = currentTodo.isChecked
        // Führt die Funktion zum Durchstreichen des Texts aus, abhängig vom isChecked-Wert des aktuellen Todos.
        toggleStrikeThrough(holder.binding.tvTodoTitle, currentTodo.isChecked)

        // Fügt einen Listener hinzu, der auf Änderungen beim Checked-Status der CheckBox reagiert.
        holder.binding.cbDone.setOnCheckedChangeListener { _, isChecked ->
            // Wenn der Checked-Status geändert wird, aktualisiert die Funktion den Durchstreichungsstatus des Texts und den isChecked-Wert des aktuellen Todos.
            toggleStrikeThrough(holder.binding.tvTodoTitle, isChecked)
            currentTodo.isChecked = !currentTodo.isChecked
        }
    }

    // Diese Methode gibt die Anzahl der Todos in der Liste zurück, so dass der Adapter weiß, wie viele Elemente er darstellen muss.
    override fun getItemCount() = todos.size
}
