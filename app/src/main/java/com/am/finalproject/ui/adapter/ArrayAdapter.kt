package com.am.finalproject.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.am.finalproject.R

class MyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_materikelasdetails, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numbersChapter1 = arrayOf("1", "2", "3")
        val adapterChapter1 = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, numbersChapter1)
        val listViewChapter1 = view.findViewById<ListView>(R.id.list_view_chapter_1)
        listViewChapter1.adapter = adapterChapter1

        val numbersChapter2 = arrayOf("4", "5", "6")
        val adapterChapter2 = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, numbersChapter2)
        val listViewChapter2 = view.findViewById<ListView>(R.id.list_view_chapter_2)
        listViewChapter2.adapter = adapterChapter2
    }
}
