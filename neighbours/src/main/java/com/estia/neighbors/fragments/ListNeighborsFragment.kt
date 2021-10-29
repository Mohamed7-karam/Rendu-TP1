package com.estia.neighbors.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estia.neighbors.fragments.adapters.ListNeighborHandler
import com.estia.neighbors.fragments.adapters.ListNeighborsAdapter
import com.estia.neighbors.fragments.data.NeighborRepository
import com.estia.neighbors.fragments.data.service.DummyNeighborApiService
import com.estia.neighbors.fragments.models.Neighbor
import com.hkaram.neighbours.R

class ListNeighborsFragment : Fragment(), ListNeighborHandler {
    private lateinit var recyclerView: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list_neighbors_fragment, container, false)
        recyclerView = view.findViewById(R.id.neighbors_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh()
    }

    override fun onDeleteNeighbor(neighbor: Neighbor) {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.dialog_title)
            builder.apply {
                setPositiveButton(R.string.oui) { dialog, _ ->
                    val dummyNeighborApiService = DummyNeighborApiService()
                    dummyNeighborApiService.deleteNeighbour(neighbor)
                    dialog.dismiss()
                    NeighborRepository.getInstance().deleteNeighbour(neighbor)
                    refresh()
                }
                setNegativeButton(R.string.non) { dialog, _ ->
                    dialog.dismiss()
                }
            }
            builder.create().show()
        }
    }
    private fun refresh() {
        val neighbors = NeighborRepository.getInstance().getNeighbours()
        val adapter = ListNeighborsAdapter(neighbors, this)
        recyclerView.adapter = adapter
    }


}