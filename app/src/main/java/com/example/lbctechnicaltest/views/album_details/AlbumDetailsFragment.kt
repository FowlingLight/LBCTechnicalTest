package com.example.lbctechnicaltest.views.album_details

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.*
import androidx.navigation.fragment.*
import com.example.lbctechnicaltest.databinding.*
import com.example.lbctechnicaltest.models.Album
import com.example.lbctechnicaltest.utils.loadImageWithCache

/**
 * A simple [Fragment] subclass.
 * Use the [AlbumDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumDetailsFragment : Fragment() {
    companion object {
        const val TAG = "AlbumDetailsFragment"
    }

    private val args by navArgs<AlbumDetailsFragmentArgs>()
    private lateinit var binding: FragmentAlbumDetailsBinding

    private lateinit var album: Album
    private var index = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.v(TAG, "onCreateView")

        album = args.album

        binding = FragmentAlbumDetailsBinding.inflate(inflater, container, false)

        binding.album = album

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.v(TAG, "onViewCreated")

        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = AlbumDetailsAdapter()
        binding.albumTrackListRecycler.adapter = adapter

        displaySongsThumbnails(binding)
        adapter.updateData(album.trackList)
    }

    private fun displaySongsThumbnails(binding: FragmentAlbumDetailsBinding) {
        //TODO REPLACE BY DYNAMIC GRID LAYOUT
        album.trackList.getOrNull(0)?.let { loadImageWithCache(it.thumbnailUrl, binding.itemAlbumImageFirst) }
        album.trackList.getOrNull(1)?.let { loadImageWithCache(it.thumbnailUrl, binding.itemAlbumImageSecond) }
        album.trackList.getOrNull(2)?.let { loadImageWithCache(it.thumbnailUrl, binding.itemAlbumImageThird) }
        album.trackList.getOrNull(3)?.let { loadImageWithCache(it.thumbnailUrl, binding.itemAlbumImageFourth) }
    }
}