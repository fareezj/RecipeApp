package com.fareez.recipeapp.MyCookbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.fareez.recipeapp.Config.DatabaseHelper
import com.fareez.recipeapp.Models.Recipe
import com.fareez.recipeapp.R
import com.fareez.recipeapp.ViewAdapter.MyRecipeListAdapter
import kotlinx.android.synthetic.main.activity_my_recipe_list_view.*


class MyRecipeListViewActivity : AppCompatActivity() {

    internal var dbHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_recipe_list_view)

        var listView = findViewById<ListView>(R.id.listView)

        /**
         * Assigning dbHelper object to a variable
         */
        var listofData: ArrayList<Recipe> = dbHelper.listOfRecipeInfo()

        /**
         * Utilizing MyRecipeListAdapter to forward data to the listview
         */
        var listAdapter = MyRecipeListAdapter(this, listofData)
        listView.adapter = listAdapter



        listView.setOnItemClickListener { parent, view, position, id ->

            var intent = Intent(this, MyRecipeDetailsActivity::class.java)
            intent.putExtra("ID",listofData[position].id)
            startActivity(intent)
            dbHelper.close()
        }

        add_my_recipe_btn.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
            dbHelper.close()
        }



    }


}