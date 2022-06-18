package com.example.lbctechnicaltest.views.album_details

import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lbctechnicaltest.R
import com.example.lbctechnicaltest.databinding.ItemTrackBinding
import com.example.lbctechnicaltest.models.*
import com.example.lbctechnicaltest.utils.loadImageWithCache

class AlbumDetailsAdapter : RecyclerView.Adapter<AlbumDetailsAdapter.PlaylistViewHolder>() {

    companion object {
        const val TAG = "AlbumDetailsAdapter"
    }

    private val list = mutableListOf<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        Log.v(TAG, "onCreateViewHolder")

        return PlaylistViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_track, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        Log.v(TAG, "onBindViewHolder")

        val item = list[position]
        holder.binding.apply {
            track = item

            loadImageWithCache(item.pictureUrl, itemTrackImage)
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<Track>) {
        Log.v(TAG, "updateData")

        list.clear()
        newList.toCollection(list)

        notifyDataSetChanged()
    }

    class PlaylistViewHolder(val binding: ItemTrackBinding) : RecyclerView.ViewHolder(binding.root)
}