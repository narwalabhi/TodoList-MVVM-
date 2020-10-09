package com.example.todolistmvvm.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {

    private TodoDao mDao;
    private LiveData<List<TodoItem>> todos;

    public TodoRepository(Context context){
        TodoDatabase mDB = TodoDatabase.getInstance(context);
        mDao = mDB.dao();
        todos = mDao.getAllTodos();
    }

    public void addTodo(TodoItem todoItem){
        new AddTodoAsync(mDao).execute(todoItem);
    }

    public void updateTodo(TodoItem todoItem){
        new UpdateTodoAsync(mDao).execute(todoItem);
    }

    public void deleteTodo(TodoItem todoItem){
        new DeleteTodoAsync(mDao).execute(todoItem);
    }

    public LiveData<List<TodoItem>> getAllTodos(){
        return todos;
    }

    public LiveData<TodoItem> getTodoByID(int id){
        return mDao.getTodoByID(id);
    }

    static class AddTodoAsync extends AsyncTask<TodoItem, Void, Void>{

        private TodoDao mDao;

        public AddTodoAsync(TodoDao mDao) {
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(TodoItem... todoItems) {
            mDao.insertTodo(todoItems[0]);
            return null;
        }
    }

    static class UpdateTodoAsync extends AsyncTask<TodoItem, Void, Void>{

        private TodoDao mDao;

        public UpdateTodoAsync(TodoDao mDao) {
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(TodoItem... todoItems) {
            mDao.updateTodo(todoItems[0]);
            return null;
        }
    }

    static class DeleteTodoAsync extends AsyncTask<TodoItem, Void, Void>{

        private TodoDao mDao;

        public DeleteTodoAsync(TodoDao mDao) {
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(TodoItem... todoItems) {
            mDao.deleteTodo(todoItems[0]);
            return null;
        }
    }

}
