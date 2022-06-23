package com.example.lbctechnicaltest.views.album_list

import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lbctechnicaltest.R
import com.example.lbctechnicaltest.databinding.ItemAlbumBinding
import com.example.lbctechnicaltest.models.Album
import com.example.lbctechnicaltest.utils.*

class AlbumListAdapter(private val listener: RecyclerViewItemClickListener) :
    RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder>() {

    companion object {
        const val TAG = "AlbumListAdapter"
    }

    private val list = mutableListOf<Album>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        Log.v(TAG, "onCreateViewHolder")

        return AlbumViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_album, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        Log.v(TAG, "onBindViewHolder")

        val item = list[position]
        holder.binding.apply {
            album = item

            displayAlbumThumbnail(this, item)

            itemAlbumLayout.setOnClickListener { listener.onItemClicked(position) }
        }
    }

    /**
     * Takes the first 4 song and get their thumbnail to generate an album picture
     */
    private fun displayAlbumThumbnail(binding: ItemAlbumBinding, album: Album) {
        Log.v(TAG, "displayAlbumThumbnail")

        album.trackList.getOrNull(0)
            ?.let { loadImageWithCache(it.thumbnailUrl, binding.itemAlbumPicture.itemAlbumImageFirst) }
        album.trackList.getOrNull(1)
            ?.let { loadImageWithCache(it.thumbnailUrl, binding.itemAlbumPicture.itemAlbumImageSecond) }
        album.trackList.getOrNull(2)
            ?.let { loadImageWithCache(it.thumbnailUrl, binding.itemAlbumPicture.itemAlbumImageThird) }
        album.trackList.getOrNull(3)
            ?.let { loadImageWithCache(it.thumbnailUrl, binding.itemAlbumPicture.itemAlbumImageFourth) }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<Album>) {
        Log.v(TAG, "updateData")

        list.clear()
        newList.toCollection(list)

        notifyDataSetChanged()
    }

    class AlbumViewHolder(val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root)
}