package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import ie.wit.R
import ie.wit.models.PinModel
import kotlinx.android.synthetic.main.card_pin.view.*


interface PinListener {
    fun onPinClick(pin: PinModel)
}

class PinAdapter(options: FirebaseRecyclerOptions<PinModel>,
                 private val listener: PinListener?)
    : FirebaseRecyclerAdapter<PinModel,
        PinAdapter.PinViewHolder>(options) {


    class PinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(pin: PinModel, listener: PinListener) {
            with(pin) {

                ///card//////////////////model
                itemView.tag = pin
                //itemView.pinTitle.text = pin.title
                //itemView.pinComment.text = pin.comment


                if(pin.isPinned) itemView.pinFavIcon.setImageResource(android.R.drawable.star_big_on)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinViewHolder {

        return PinViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_pin, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PinViewHolder, position: Int, model: PinModel) {
        holder.bind(model, listener!!)
    }

    override fun onDataChanged() {
        // Called each time there is a new data snapshot. You may want to use this method
        // to hide a loading spinner or check for the "no documents" state and update your UI.
        // ...
    }
}