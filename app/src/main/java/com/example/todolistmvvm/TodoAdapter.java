package com.example.todolistmvvm;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistmvvm.Database.TodoItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private static final String DATE_FORMAT = "dd/MM/yyy";

    private List<TodoItem> todos;
    private LayoutInflater layoutInflater;
    private Context context;
    private OnTodoItemClickListener listener;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    public TodoAdapter(Context context, OnTodoItemClickListener listener) {
        this.todos = new ArrayList<>();
        this.context = context;
        this.listener = listener;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setTodos(List<TodoItem> todos){
        this.todos = todos;
        notifyDataSetChanged();
    }

    public TodoItem getTodoItem(int position) {
        if(todos.size() == 0){
            return null;
        }
        return todos.get(position);
    }

    public interface OnTodoItemClickListener {
        public void onClick(int i);
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.todo_list_item, parent, false);
        return new TodoViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        TodoItem todoItem = todos.get(position);
        holder.bind(todoItem);
    }

    @Override
    public int getItemCount() {
        return todos != null ? todos.size() : 0;
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvDescription, tvDate, tvPriority;
        private OnTodoItemClickListener listener;

        public TodoViewHolder(@NonNull View itemView, OnTodoItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            tvDescription = itemView.findViewById(R.id.tvTodoDescription);
            tvDate = itemView.findViewById(R.id.tvTodoDate);
            tvPriority = itemView.findViewById(R.id.tvPriority);

        }

        public void bind(TodoItem todoItem) {
            String description = todoItem.getDescription();
            int priority = todoItem.getPriority();
            String date = dateFormat.format(todoItem.getUpdateAt());
            tvDescription.setText(description);
            tvDate.setText(date);
            tvPriority.setText(priority+"");
            GradientDrawable priorityCircle = (GradientDrawable) tvPriority.getBackground();
            int bgColor = getPriorityColor(priority);
            priorityCircle.setColor(bgColor);
            itemView.setOnClickListener(this);
        }

        private int getPriorityColor(int priority) {
            int priorityColor = 0;

            switch (priority) {
                case 1:
                    priorityColor = ContextCompat.getColor(context, R.color.materialRed);
                    break;
                case 2:
                    priorityColor = ContextCompat.getColor(context, R.color.materialOrange);
                    break;
                case 3:
                    priorityColor = ContextCompat.getColor(context, R.color.materialYellow);
                    break;
                default:
                    break;
            }
            return priorityColor;
        }

        @Override
        public void onClick(View view) {
            int todoId = todos.get(getAdapterPosition()).getId();
            listener.onClick(todoId);
        }
    }
}
