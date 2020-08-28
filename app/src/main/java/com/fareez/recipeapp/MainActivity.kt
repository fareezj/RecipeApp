package com.fareez.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fareez.recipeapp.ExploreRecipe.ChooseRecipeTypeActivity
import com.fareez.recipeapp.MyCookbook.MyRecipeListViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_recipe_btn.setOnClickListener {
            val intent = Intent(this, MyRecipeListViewActivity::class.java)
            startActivity(intent)
        }

        explore_recipe_btn.setOnClickListener {
            val intent = Intent(this, ChooseRecipeTypeActivity::class.java)
            startActivity(intent)
        }

    }


}