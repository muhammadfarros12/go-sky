package com.binar.gosky.presentation.ui.search.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.gosky.data.network.model.tickets.TicketsItem
import com.binar.gosky.databinding.ItemTripBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

class SearchResultAdapter(private val itemClick: (TicketsItem) -> Unit) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<TicketsItem>() {
        override fun areItemsTheSame(oldItem: TicketsItem, newItem: TicketsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TicketsItem, newItem: TicketsItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(movie: List<TicketsItem>?) {
        differ.submitList(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = ItemTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultViewHolder(binding, itemClick)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class SearchResultViewHolder(private val binding: ItemTripBinding, private val itemClick: (TicketsItem) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: TicketsItem) {
            with(binding) {
                with(item) {
                    Glide.with(itemView)
                        .load(imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivAirlineLogo)
                    tvFrom.text = from
                    tvTo.text = to
                    tvTicketPrice.text = convertRupiah(price)
                    tvDepartureTime.text = convertISOtoDate(departureTime)
                    tvDurationDeparture.text = duration?.let { convertMinutesToHourAndMinutes(it) }
                    tvDurationReturn.text = duration?.let { convertMinutesToHourAndMinutes(it) }
                    Log.d("duration", duration.toString())
                    if (returnTime.isNullOrEmpty()) {
                        txtReturn.isVisible = false
                        tvReturnTime.isVisible = false
                        ivArrowReturn.isVisible = false
                        tvArrivalTimeReturn.isVisible = false
                        tvDurationReturn.isVisible = false
                    } else {
                        tvReturnTime.text = convertISOtoDate(returnTime)
                        tvFromReturn.text = to
                        tvToReturn.text = from
                    }

                    itemView.setOnClickListener {
                        itemClick(this)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertMinutesToHourAndMinutes(duration: Long): String {
        val durationTime = Duration.ofMinutes(duration)
        val hours = durationTime.toHours()
        val minutes = durationTime.minusHours(hours).toMinutes()

        return "${hours}h ${minutes}m"
    }

    fun convertRupiah(intPrice: Int?): String {
        val localId = Locale("in", "ID")
        val formatter = NumberFormat.getCurrencyInstance(localId)
        return formatter.format(intPrice)
    }

    fun convertISOtoDate(isoString: String?): String {
        val localeID = Locale("in", "ID")
        var formattedDate = ""
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", localeID)
        try {
            cal.time = dateFormat.parse(isoString)
            val c = cal.time
            val dformat = SimpleDateFormat("dd MMM yyyy\nHH:mm", localeID)
            formattedDate = dformat.format(c)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formattedDate
    }
}