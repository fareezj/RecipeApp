package com.fareez.recipeapp.Config

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.ID
import com.fareez.recipeapp.Models.Recipe


/**
 * SQLite Configuration Setup
 */
class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        val DATABASE_NAME = "recipes.db"
        val TABLE_NAME = "recipes_table"
        val ID = "ID"
        val NAME = "NAME"
        val CATEGORY = "CATEGORY"
        val IMAGE = "IMAGE"
        val INGREDIENTS = "INGREDIENTS"
        val STEPS = "STEPS"

    }

    /**
     * Create new database table
     */
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME (ID INTEGER PRIMARY KEY " +
                "AUTOINCREMENT,NAME TEXT,CATEGORY TEXT,IMAGE TEXT, INGREDIENTS TEXT, STEPS TEXT)")
    }
    /**
     * Condition apply after table creation
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    /**
     * Insert new data function
     */
    fun insertData(name: String, category: String, image: String, ingredients: String, steps: String){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, name)
        contentValues.put(CATEGORY, category)
        contentValues.put(IMAGE, image)
        contentValues.put(INGREDIENTS, ingredients)
        contentValues.put(STEPS, steps)
        db.insert(TABLE_NAME, null, contentValues)
    }

    /**
     * List all data available
     */
    fun listOfRecipeInfo(): ArrayList<Recipe>  {
        val db = this.writableDatabase
        val res = db.rawQuery("select * from " + TABLE_NAME, null)
        val useList = ArrayList<Recipe>()
        while (res.moveToNext()) {
            var recipe = Recipe()
            recipe.id = Integer.valueOf(res.getString(0))
            recipe.name = res.getString(1)
            recipe.category = res.getString(2)
            recipe.image = res.getString(3)
            recipe.ingredients = res.getString(4)
            recipe.steps = res.getString(5)

            useList.add(recipe)
        }
        return useList
    }
    /**
     * Fetch one data from database
     */
    fun getParticularRecipeData(id: Int): Recipe {
        var recipe  = Recipe()
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + ID + " = '" + id + "'"
        val cursor = db.rawQuery(selectQuery, null)
        try {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst()
                if (cursor.getCount() > 0) {
                    do {
                        recipe.id = cursor.getInt(cursor.getColumnIndex(ID))
                        recipe.name = cursor.getString(cursor.getColumnIndex(NAME))
                        recipe.category = cursor.getString(cursor.getColumnIndex(CATEGORY))
                        recipe.image = cursor.getString(cursor.getColumnIndex(IMAGE))
                        recipe.ingredients = cursor.getString(cursor.getColumnIndex(INGREDIENTS))
                        recipe.steps = cursor.getString(cursor.getColumnIndex(STEPS))
                    } while ((cursor.moveToNext()))
                }
            }
        } finally {
            cursor.close();
        }
        return recipe
    }

    /**
     * Delete one data based on ID
     */
    fun deleteData(id : String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME,"ID = ?", arrayOf(id))
    }

    /**
     * Update one data based on ID
     */
    fun updateData(id: String, name: String, category: String, image: String, ingredients: String, steps: String):
            Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, name)
        contentValues.put(CATEGORY, category)
        contentValues.put(IMAGE, image)
        contentValues.put(INGREDIENTS, ingredients)
        contentValues.put(STEPS, steps)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
        return true
    }
}