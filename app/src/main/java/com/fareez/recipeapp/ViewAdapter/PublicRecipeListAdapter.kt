package com.fareez.recipeapp.ViewAdapter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.fareez.recipeapp.ExploreRecipe.RecipeDetailsActivity
import com.fareez.recipeapp.Models.Recipe
import com.fareez.recipeapp.R
import com.squareup.picasso.Picasso

class PublicRecipeListAdapter (var mCtx: Context, var resource: Int, var items: List<Recipe>):
    ArrayAdapter<Recipe>(mCtx, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)

        val view: View = layoutInflater.inflate(resource, null)

        var thumbnail: ImageView = view.findViewById(R.id.recipe_thumbnail)
        var title: TextView = view.findViewById(R.id.recipe_name_label)
        var recipeDetailsBtn: Button = view.findViewById(R.id.recipe_details_btn)

        /**
         * Populate data to UI from fetched items
         */
        val recipe: Recipe = items[position]
        title.text = recipe.name
        if(!TextUtils.isEmpty(recipe.image)){
            Picasso.with(mCtx)
                .load(recipe.image)
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_menu_report_image)
                .into(thumbnail)
        }

        recipeDetailsBtn.setOnClickListener {
            var intent = Intent(mCtx, RecipeDetailsActivity::class.java)
            intent.putExtra("TITLE", recipe.name.toString())
            mCtx.startActivity(intent)
        }

        return view
    }
}