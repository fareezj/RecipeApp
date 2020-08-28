package com.fareez.recipeapp.ExploreRecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.fareez.recipeapp.Models.Recipe
import com.fareez.recipeapp.R
import com.fareez.recipeapp.Utils.Constants
import com.fareez.recipeapp.ViewAdapter.PublicRecipeListAdapter
import org.json.JSONException
import org.json.JSONObject

class RecipeListActivity : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    var recipeList: ArrayList<Recipe>? = null
    var adapter: PublicRecipeListAdapter? = null
    var listView: ListView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        /**
         * Fetch submitted Category from previous page
         */
        val recipeCategory: String? = intent.getStringExtra("CATEGORY")

        /**
         * Intialize URL
         */
        var url = "${Constants.BASE_URL}${Constants.QUERY}${recipeCategory}"

        recipeList = ArrayList()
        volleyRequest = Volley.newRequestQueue(this)
        getRecipe(url)
    }

    /**
     * Parsing JSON data to Listview via PublicRecipeListAdapter
     */
    fun getRecipe(url: String) {
        val recipeRequest = JsonObjectRequest(Request.Method.GET,
            url, Response.Listener {
                    response: JSONObject ->
                try {

                    val resultArray = response.getJSONArray("meals")

                    for (i in 0..resultArray.length() - 1) {
                        var recipeObj = resultArray.getJSONObject(i)

                        var title = recipeObj.getString("strMeal")
                        var category = recipeObj.getString("strCategory")
                        var image = recipeObj.getString("strMealThumb")

                        var recipe = Recipe()
                        recipe.name = title
                        recipe.category = category
                        recipe.image = image

                        recipeList!!.add(recipe)

                        adapter = PublicRecipeListAdapter(this, R.layout.list_view_api_item, recipeList!!)
                        listView = findViewById(R.id.listViewId)
                        listView!!.adapter = adapter

                    }

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