package com.abler31.mapapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment(
    private val nameStr: String,
    private val trackStr: String,
    private val dateStr: String,
    private val timeStr: String,
) : BottomSheetDialogFragment(R.layout.bottomsheet_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setDimAmount(0f)

        val name = view.findViewById<TextView>(R.id.tv_name)
        val track = view.findViewById<TextView>(R.id.tv_track)
        val date = view.findViewById<TextView>(R.id.tv_date)
        val time = view.findViewById<TextView>(R.id.tv_time)

        name.text = nameStr
        track.text = trackStr
        date.text = dateStr
        time.text = timeStr
    }
}