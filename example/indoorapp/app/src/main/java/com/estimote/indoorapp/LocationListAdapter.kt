package com.estimote.indoorapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.estimote.indoorsdk_module.cloud.Location

/**
 * Adapter for list of locations
 *
 */
class LocationListAdapter (private var mLocations: List<Location>) : RecyclerView.Adapter<LocationListAdapter.LocationHolder>() {

    var mListener: ((String) -> Unit)? = null

    class LocationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.location_name) as TextView
        val id: TextView = itemView.findViewById(R.id.location_id) as TextView
        fun setOnClickListener(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): LocationListAdapter.LocationHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.location_list_item, parent, false)
        val vh = LocationHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        holder.name.text = mLocations[position].name
        holder.id.text = mLocations[position].identifier
        holder.setOnClickListener(View.OnClickListener { mListener?.invoke(mLocations[position].identifier) })
    }

    override fun getItemCount(): Int {
        return mLocations.size
    }

    fun setLocations(list: List<Location>) {
        this.mLocations = list
        notifyDataSetChanged()
    }


    fun setOnClickListener(listener: (String) -> Unit) {
        mListener = listener
    }

}