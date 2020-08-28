package com.fareez.recipeapp.ExploreRecipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.fareez.recipeapp.MyCookbook.MyRecipeDetailsActivity
import com.fareez.recipeapp.R
import kotlinx.android.synthetic.main.activity_choose_recipe_type.*
import kotlinx.android.synthetic.main.activity_main.*

class ChooseRecipeTypeActivity : AppCompatActivity() {

    var chosenCategory: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_recipe_type)

        /**
         * Get data source (Recipe Type)
         */
        val recipes = resources.getStringArray(R.array.Recipes)

        /**
         * Accessing Spinner
         */
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, recipes)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    chosenCategory = recipes[position]
                    Toast.makeText(this@ChooseRecipeTypeActivity,
                                "" + recipes[position], Toast.LENGTH_SHORT).show()

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        explore_btn.setOnClickListener {
            var intent = Intent(this, RecipeListActivity::class.java)
            intent.putExtra("CATEGORY", chosenCategory)
            startActivity(intent)
        }
    }
}