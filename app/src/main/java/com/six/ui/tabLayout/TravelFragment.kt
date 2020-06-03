package com.six.ui.tabLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.six.ui.R
import com.six.ui.recyclerview.RVAdapter


/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-19.
 */
class TravelFragment : Fragment(), View.OnClickListener {
//    private lateinit var usage: SixProgressbar
//    private lateinit var logo: ImageView


    private lateinit var spinner: AppCompatSpinner
    private lateinit var rv: RecyclerView
    private lateinit var website: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        TextView titleTextView = new TextView(getActivity());
//        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
//        titleTextView.setText(R.string.txt_travel);
//        return titleTextView;
//        val root = inflater.inflate(R.layout.frag_travel, container, false)
//        usage = root.findViewById(R.id.usage)
//        usage.setOnClickListener(this)
        return inflater.inflate(R.layout.frag_weird_scrolling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        logo = view.findViewById(R.id.logo)
//        logo.setOnClickListener(this)

        val data = getData()
        spinner = view.findViewById(R.id.spinner)
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, data)

        rv = view.findViewById(R.id.rv)
        rv.adapter = RVAdapter(requireContext(), data.subList(0, 6))
        rv.layoutManager = GridLayoutManager(requireContext(), 3)

        website = view.findViewById(R.id.label_to_website)
        website.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        println("xxl-width: ${spinner.measuredWidth}; ${view?.width?:0 - (view?.paddingStart ?: 0) - (view?.paddingEnd?:0)}")
        spinner.dropDownWidth = view?.width?:0 - (view?.paddingStart ?: 0) - (view?.paddingEnd?:0)

    }

    private fun getData(): List<String> {
        val data = mutableListOf<String>()

        for (i in 0..100) {
            data.add("Item No.$i")
        }

        return data
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.label_to_website -> {
                val popup = PopupMenu(requireContext(), website)
                popup.menuInflater.inflate(R.menu.menu_toolbar, popup.menu)
                popup.show()
            }

//            R.id.usage -> usage.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED)

//            R.id.logo -> println("xxl-click-logo")
        }
    }
}