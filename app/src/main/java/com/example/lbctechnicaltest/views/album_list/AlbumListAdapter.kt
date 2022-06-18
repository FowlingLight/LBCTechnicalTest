package com.example.lbctechnicaltest.views.album_list

import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lbctechnicaltest.R
import com.example.lbctechnicaltest.databinding.ItemAlbumBinding
import com.example.lbctechnicaltest.models.Album
import com.example.lbctechnicaltest.utils.*

class AlbumListAdapter(private val listener: RecyclerViewItemClickListener) :
    RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder>() {

    private val list = mutableListOf<Album>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
        AlbumViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_album, parent, false
            )
        )

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            album = item

            displaySongsThumbnails(this, item)

            itemAlbumLayout.setOnClickListener { listener.onItemClicked(position) }
        }
    }

    private fun displaySongsThumbnails(binding: ItemAlbumBinding, album: Album) {
        //TODO REPLACE BY DYNAMIC GRID LAYOUT
        album.trackList.getOrNull(0)?.let { loadImageWithCache(it.thumbnailUrl, binding.itemAlbumImageFirst) }
        album.trackList.getOrNull(1)?.let { loadImageWithCache(it.thumbnailUrl, binding.itemAlbumImageSecond) }
        album.trackList.getOrNull(2)?.let { loadImageWithCache(it.thumbnailUrl, binding.itemAlbumImageThird) }
        album.trackList.getOrNull(3)?.let { loadImageWithCache(it.thumbnailUrl, binding.itemAlbumImageFourth) }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<Album>) {
        list.clear()
        newList.toCollection(list)

        notifyDataSetChanged()
    }

    class AlbumViewHolder(val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root)
}