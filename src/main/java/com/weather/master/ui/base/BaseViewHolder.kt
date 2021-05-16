package com.weather.master.ui.base

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<M>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //Getter Methods for Resources
    fun getString(holder: RecyclerView.ViewHolder, id: Int): String {
        return holder.itemView.context.getString(id)
    }

    fun getString(holder: RecyclerView.ViewHolder, id: Int, vararg formatArgs: Any): String {
        return holder.itemView.context.getString(id, *formatArgs)
    }

    fun getString(id: Int, vararg formatArgs: Any): String {
        return itemView.context.getString(id, *formatArgs)
    }

    fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any): String {
        return itemView.context.resources.getQuantityString(id, quantity, *formatArgs)
    }

    fun getDrawable(holder: RecyclerView.ViewHolder, id: Int): Drawable? {
        return ContextCompat.getDrawable(holder.itemView.context, id)
    }

    fun getDrawable(id: Int): Drawable? {
        return ContextCompat.getDrawable(this.itemView.context, id)
    }

    fun getColor(holder: RecyclerView.ViewHolder, id: Int): Int {
        return ContextCompat.getColor(holder.itemView.context, id)
    }

    fun getColor(id: Int): Int {
        return ContextCompat.getColor(itemView.context, id)
    }


    fun getFont(id: Int): Typeface? {
        return ResourcesCompat.getFont(itemView.context, id)
    }

    fun getDrawableFromTheme(id: Int): Drawable? {
        return ResourcesCompat.getDrawable(itemView.context.resources, id, itemView.context.theme)
    }

    abstract fun bindViewHolder(item: M)

    open fun onViewDetachedFromWindow() {}

}
