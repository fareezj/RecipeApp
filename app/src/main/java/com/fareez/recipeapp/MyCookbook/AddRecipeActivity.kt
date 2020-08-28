package com.fareez.recipeapp.MyCookbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.fareez.recipeapp.Config.DatabaseHelper
import com.fareez.recipeapp.R
import kotlinx.android.synthetic.main.activity_add_recipe.*
import java.lang.Exception

class AddRecipeActivity : AppCompatActivity() {

    internal var dbHelper = DatabaseHelper(this)

    fun showToast(text: String){
        Toast.makeText(this@AddRecipeActivity, text, Toast.LENGTH_LONG).show()
    }
    /**
     * Clear text input function
     */
    fun clearTextInputs(){
        input_recipe_name.setText("")
        input_recipe_category.setText("")
        input_recipe_image.setText("")
        input_recipe_ingredients.setText("")
        input_recipe_steps.setText("")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        handleInsertData()
        dbHelper.close()
    }

    /**
     * Invoke insertData function
     */
    fun handleInsertData() {
        publish_recipe_btn.setOnClickListener{
            try{
                dbHelper.insertData(
                    input_recipe_name.text.toString(),
                    input_recipe_category.text.toString(),
                    input_recipe_image.text.toString(),
                    input_recipe_ingredients.text.toString(),
                    input_recipe_steps.text.toString()
                )
                showToast("Recipe Added to Database !")
                clearTextInputs()
                val intent = Intent(this, MyRecipeListViewActivity::class.java)
                startActivity(intent)

            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }


}