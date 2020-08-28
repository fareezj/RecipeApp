package com.fareez.recipeapp.ExploreRecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.fareez.recipeapp.R
import com.fareez.recipeapp.Utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_recipe_details.*
import org.json.JSONException
import org.json.JSONObject

class RecipeDetailsActivity : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    var recipeName: String = ""
    var recipeIngredients1: String = ""
    var recipeIngredients2: String = ""
    var recipeIngredients3: String = ""
    var recipeIngredients4: String = ""
    var recipeIngredients5: String = ""
    var recipeSteps: String = ""
    var recipeImage: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        /**
         * Fetch submitted Title from previous page
         */
        val recipeTitle: String? = intent.getStringExtra("TITLE")

        /**
         * Intialize URL
         */
        var url =  "${Constants.BASE_URL}${Constants.QUERY}${recipeTitle}"

        volleyRequest = Volley.newRequestQueue(this)
        getRecipeDetails(url)
    }
    /**
     * Parsing JSON data to UI
     */
    private fun getRecipeDetails(url: String) {
        val recipeRequest = JsonObjectRequest(Request.Method.GET,
            url, Response.Listener {
                    response: JSONObject ->
                try {

                    val resultArray = response.getJSONArray("meals")

                    for (i in 0..resultArray.length() - 1) {
                        var recipeObj = resultArray.getJSONObject(i)

                        var title = recipeObj.getString("strMeal")
                        var ingredients1 = recipeObj.getString("strIngredient1")
                        var ingredients2 = recipeObj.getString("strIngredient2")
                        var ingredients3 = recipeObj.getString("strIngredient3")
                        var ingredients4 = recipeObj.getString("strIngredient4")
                        var ingredients5 = recipeObj.getString("strIngredient5")
                        var image = recipeObj.getString("strMealThumb")
                        var steps = recipeObj.getString("strInstructions")

                        recipeName = title
                        recipeIngredients1 = ingredients1
                        recipeIngredients2 = ingredients2
                        recipeIngredients3 = ingredients3
                        recipeIngredients4 = ingredients4
                        recipeIngredients5 = ingredients5
                        recipeImage = image
                        recipeSteps = steps
                    }

                    /**
                     * Populate Data into UI
                     */
                    Picasso.with(this)
                        .load(recipeImage)
                        .fit()
                        .placeholder(android.R.drawable.ic_menu_report_image)
                        .error(android.R.drawable.ic_menu_report_image)
                        .into(recipe_image_details_api)

                    recipe_name_details_api.setText(recipeName)
                    recipe_ingredients1_details_api.setText("✔" + recipeIngredients1)
                    recipe_ingredients2_details_api.setText("✔" + recipeIngredients2)
                    recipe_ingredients3_details_api.setText("✔" + recipeIngredients3)
                    recipe_ingredients4_details_api.setText("✔" + recipeIngredients4)
                    recipe_ingredients5_details_api.setText("✔" + recipeIngredients5)
                    recipe_steps_details_api.setText(recipeSteps)

                }catch (e: JSONException) { e.printStackTrace() }

            },
            Response.ErrorListener {
                    error: VolleyError? ->
                try {
                    Log.d("Error:", error.toString())

                } catch (e: JSONException) { e.printStackTrace() }
            })

        volleyRequest!!.add(recipeRequest)
    }

}



