package com.example.lbctechnicaltest.views.album_list

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lbctechnicaltest.*
import com.example.lbctechnicaltest.databinding.FragmentAlbumListBinding
import com.example.lbctechnicaltest.models.utils.NetworkState
import com.example.lbctechnicaltest.utils.RecyclerViewItemClickListener
import com.example.lbctechnicaltest.viewmodels.AlbumListViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AlbumListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumListFragment : Fragment() {
    companion object {
        const val TAG = "AlbumListFragment"
    }

    private val viewModel: AlbumListViewModel by viewModels()

    private lateinit var binding: FragmentAlbumListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.v(TAG, "onCreateView")

        binding = FragmentAlbumListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.v(TAG, "onViewCreated")

        super.onViewCreated(view, savedInstanceState)

        val adapter = AlbumListAdapter(object : RecyclerViewItemClickListener {
            override fun onItemClicked(position: Int) {
                viewModel.albums.value?.get(position)?.let {

                    Log.d(TAG, "Displaying album ${it.id}")

                    findNavController().navigate(
                        AlbumListFragmentDirections.actionAlbumListFragmentToAlbumDetailFragment(it)
                    )
                }
            }
        })
        binding.albumListRecycler.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.albumListRecycler.adapter = adapter
        viewModel.albums.observe(viewLifecycleOwner) {
            it?.let {
                Log.d(TAG, "Setting up the album list")

                adapter.updateData(it)
            }
        }
        viewModel.loaderVisible.observe(viewLifecycleOwner) {
            (requireActivity() as MainActivity).loaderVisible.postValue(it)
        }
        viewModel.networkState.observe(viewLifecycleOwner) {
            if (it == NetworkState.ERROR) {
                Toast.makeText(requireContext(), R.string.network_error, Toast.LENGTH_SHORT).show()
                viewModel.networkState.postValue(NetworkState.PENDING)
            }
        }

        viewModel.getAlbums()
    }
}