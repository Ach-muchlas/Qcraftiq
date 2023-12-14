package com.am.finalproject.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.am.finalproject.R


class MateriKelasDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_materikelasdetails, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chapter1Items = arrayOf(
            "1. Tujuan Mengikuti Kelas Design System",
            "2. Pengenalan Design System",
            "3. Contoh Dalam Membangun Design System"
        )
        val adapterChapter1 = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, chapter1Items)
        val listViewChapter1 = view.findViewById<ListView>(R.id.list_view_chapter_1)
        listViewChapter1.adapter = adapterChapter1

        val chapter2Items = arrayOf(
            "1. Color Palette",
            "2. Installation"
        )
        val adapterChapter2 = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, chapter2Items)
        val listViewChapter2 = view.findViewById<ListView>(R.id.list_view_chapter_2)
        listViewChapter2.adapter = adapterChapter2
    }
}