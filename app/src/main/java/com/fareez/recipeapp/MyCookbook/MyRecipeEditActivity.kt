package com.fareez.recipeapp.MyCookbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fareez.recipeapp.Config.DatabaseHelper
import com.fareez.recipeapp.R
import kotlinx.android.synthetic.main.activity_my_recipe_edit.*

class MyRecipeEditActivity : AppCompatActivity() {

    internal var dbHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_recipe_edit)

        fun showToast(text: String){
            Toast.makeText(this@MyRecipeEditActivity, text, Toast.LENGTH_LONG).show()
        }
        /**
         * Fetch submitted ID from previous page
         */
        val recipeID: Int = intent.getIntExtra("ID", 0)
        val result = dbHelper.getParticularRecipeData(recipeID)

        /**
         * Populate collected data into UI
         */
        edit_recipe_name.setText(result.name.toString())
        edit_recipe_category.setText(result.category.toString())
        edit_recipe_image.setText(result.image.toString())
        edit_recipe_ingredients.setText(result.ingredients.toString())
        edit_recipe_steps.setText(result.steps.toString())

        edit_recipe_btn.setOnClickListener {
            try {
                /**
                 * Invoking updateData function to update new data
                 */
                dbHelper.updateData(
                    recipeID.toString(),
                    edit_recipe_name.text.toString(),
                    edit_recipe_category.text.toString(),
                    edit_recipe_image.text.toString(),
                    edit_recipe_ingredients.text.toString(),
                    edit_recipe_steps.text.toString()
                )
                showToast("Recipe Edited !")
                val intent = Intent(this, MyRecipeListViewActivity::class.java)
                startActivity(intent)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        dbHelper.close()
    }
}