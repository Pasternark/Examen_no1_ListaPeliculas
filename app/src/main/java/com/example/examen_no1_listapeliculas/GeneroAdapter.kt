package com.example.examen_no1_listapeliculas

import android.content.Context
import android.content.Intent
import android.hardware.biometrics.BiometricManager
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class GeneroAdapter (context: Context) :
RecyclerView.Adapter<GeneroAdapter.GeneroViewHolder>() {

    private val filteredGeneros: List<String> = context.resources.getStringArray(R.array.generos).toList()

    class GeneroViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val button = view.findViewById<Button>(R.id.button_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneroViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        // Setup custom accessibility delegate to set the text read
        layout.accessibilityDelegate = Accessibility

        return GeneroViewHolder(layout)
    }

    override fun onBindViewHolder(holder: GeneroViewHolder, position: Int) {
        val item = filteredGeneros.get(position)
        holder.button.text = item.toString()

        holder.button.setOnClickListener {
            val context = holder.view.context

            val intent = Intent(context, DetailActivity::class.java)

            intent.putExtra(DetailActivity.GENERO, holder.button.text.toString())

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = filteredGeneros.size

    // Setup custom accessibility delegate to set the text read with
    // an accessibility service
    companion object Accessibility : View.AccessibilityDelegate() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onInitializeAccessibilityNodeInfo(
            host: View?,
            info: AccessibilityNodeInfo?
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            // With `null` as the second argument to [AccessibilityAction], the
            // accessibility service announces "double tap to activate".
            // If a custom string is provided,
            // it announces "double tap to <custom string>".
            val customString = host?.context?.getString(R.string.look_up_genres)
            val customClick =
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    customString
                )
            info?.addAction(customClick)
        }
    }
}