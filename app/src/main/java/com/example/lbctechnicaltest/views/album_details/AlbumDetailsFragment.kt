package com.example.lbctechnicaltest.views.album_details

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.lbctechnicaltest.databinding.FragmentAlbumDetailsBinding
import com.example.lbctechnicaltest.models.Album
import com.example.lbctechnicaltest.utils.loadImageWithCache

class AlbumDetailsFragment : Fragment() {
    companion object {
        const val TAG = "AlbumDetailsFragment"
    }

    private val args by navArgs<AlbumDetailsFragmentArgs>()
    private lateinit var binding: FragmentAlbumDetailsBinding

    private lateinit var album: Album

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.v(TAG, "onCreateView")

        binding = FragmentAlbumDetailsBinding.inflate(inflater, container, false)

        album = args.album
        binding.album = album

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.v(TAG, "onViewCreated")

        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = AlbumDetailsAdapter()
        binding.albumTrackListRecycler.adapter = adapter

        adapter.updateData(album.trackList)
        displayAlbumThumbnail(binding)
    }

    /**
     * Takes the first 4 song and get their thumbnail to generate an album picture
     */
    private fun displayAlbumThumbnail(binding: FragmentAlbumDetailsBinding) {
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
}