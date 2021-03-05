package com.solarexsoft.androidpractice.livedata

import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by houruhou on 3/5/21/5:55 PM
 * Desc:
 */
class ListLiveData<T>: LiveData<ListHolder<T>>() {
    val size: Int
        get() = value?.size() ?: -1

    fun addItem(item: T, position: Int = value?.size() ?: 0) {
        value?.addItem(position, item)
        value = value
    }

    fun removeAt(position: Int) {
        value?.removeItemAt(position)
        value = value
    }

    fun setItem(position: Int, item: T) {
        value?.setItem(position, item)
        value = value
    }

    operator fun get(position: Int): T? {
        return value?.list?.get(position)
    }
}

data class ListHolder<T>(val list: MutableList<T> = mutableListOf()) {
    var indexChanged: Int = -1
    private var updateType: UpdateType? = null

    fun addItem(position: Int, item: T) {
        list.add(position, item)
        indexChanged = position
        updateType = UpdateType.INSERT
    }

    fun removeItemAt(position: Int) {
        list.removeAt(position)
        indexChanged = position
        updateType = UpdateType.REMOVE
    }

    fun setItem(position: Int, item: T) {
        list[position] = item
        indexChanged = position
        updateType = UpdateType.CHANGE
    }

    fun size() = list.size

    private enum class UpdateType {
        INSERT {
            override fun notifyChange(
                adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
                indexChanged: Int,
            ) = adapter.notifyItemInserted(indexChanged)
        },
        REMOVE {
            override fun notifyChange(
                adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
                indexChanged: Int
            ) = adapter.notifyItemRemoved(indexChanged)
        },
        CHANGE {
            override fun notifyChange(
                adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
                indexChanged: Int,
            ) = adapter.notifyItemChanged(indexChanged)
        };

        abstract fun notifyChange(
            adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
            indexChanged: Int,
        )
    }
}