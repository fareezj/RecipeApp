package com.fareez.recipeapp.ViewAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.fareez.recipeapp.Models.Recipe
import com.fareez.recipeapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_view_item.view.*

class MyRecipeListAdapter(context: Context, arrayOfData: ArrayList<Recipe>)  : BaseAdapter() {

    var arrayOfData : ArrayList<Recipe>;
    private val mInflator: LayoutInflater
    var mCtx: Context = context

    init {

        this.arrayOfData = arrayOfData
        this.mInflator = LayoutInflater.from(context)
    }

    override fun getItem(position: Int): Any {
        return arrayOfData[position];
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayOfData.size
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ListRowHolder
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.list_view_item, parent, false)
            vh = ListRowHolder(view)
            if (view != null) {
                view.tag = vh

                /**
                 * Populate data to UI from fetched items
                 */
                vh.tvName.text = arrayOfData[position].name
                vh.tvCategory.text = ""+arrayOfData[position].category

                var image: ImageView = view.findViewById(R.id.recipe_image)
                Picasso.with(mCtx)
                    .load(arrayOfData[position].image)
                    .fit()
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(image)
            }
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        return view
    }

    private class ListRowHolder(row: View?) {
        val tvName: TextView
        val tvCategory : TextView
        val recipeImage : ImageView

        init {
            this.tvName = row?.findViewById(R.id.tv_name) as TextView
            this.tvCategory = row?.findViewById(R.id.tv_category) as TextView
            this.recipeImage = row?.findViewById(R.id.recipe_image) as ImageView
        }
    }


}