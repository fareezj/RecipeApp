package com.fareez.recipeapp.MyCookbook

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fareez.recipeapp.Config.DatabaseHelper
import com.fareez.recipeapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_my_recipe_details.*


class MyRecipeDetailsActivity : AppCompatActivity() {

    internal var dbHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_recipe_details)

        fun showToast(text: String){
            Toast.makeText(this@MyRecipeDetailsActivity, text, Toast.LENGTH_LONG).show()
        }

        /**
         * Fetch submitted ID from previous page
         */
        val recipeID: Int = intent.getIntExtra("ID", 0)

        /**
         * Invoke getParticularRecipeData function to get data
         */
        val result = dbHelper.getParticularRecipeData(recipeID)
        Picasso.with(this)
            .load(result.image.toString())
            .fit()
            .placeholder(android.R.drawable.ic_menu_report_image)
            .error(android.R.drawable.ic_menu_report_image)
            .into(recipe_image_details)
        recipe_name_details.setText(result.name.toString())
        recipe_ingredients_details.setText(result.ingredients.toString())
        recipe_steps_details.setText(result.steps.toString())


        delete_recipe_btn.setOnClickListener {
            try {
                dbHelper.deleteData(recipeID.toString())
                showToast("Recipe Deleted !")
                val intent = Intent(this, MyRecipeListViewActivity::class.java)
                startActivity(intent)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        edit_recipe_btn.setOnClickListener {
            var intent = Intent(this, MyRecipeEditActivity::class.java)
                intent.putExtra("ID", recipeID)
                startActivity(intent)
        }

        dbHelper.close()

    }

}


