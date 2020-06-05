package com.six.ui.tabLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.ListPopupWindow
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
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
    private lateinit var popupAnchorTextView: TextView
    private lateinit var popupWindow: ListPopupWindow
    private var listOpen = false

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
//        spinner = view.findViewById(R.id.spinner)
//        spinner.adapter = object: ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, data) {
//            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
//                println("xxl-open")
//                return super.getDropDownView(position, convertView, parent)
//            }
//        }

        rv = view.findViewById(R.id.rv)
        rv.adapter = RVAdapter(requireContext(), data.subList(0, 6))
        rv.layoutManager = GridLayoutManager(requireContext(), 3)

        website = view.findViewById(R.id.label_to_website)
        website.setOnClickListener(this)

        popupAnchorTextView = view.findViewById(R.id.spinner_anchor)
        popupAnchorTextView.setOnClickListener(this)

        popupWindow = ListPopupWindow(requireContext())
        popupWindow.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, data))
        popupWindow.anchorView = popupAnchorTextView
        popupWindow.width = AbsListView.LayoutParams.WRAP_CONTENT
        popupWindow.height = 200 * 3
        println("xxl-anchor-height: ${popupAnchorTextView.height}")
        popupWindow.verticalOffset = -(popupWindow.height + 40 * 3)
        popupWindow.setOnItemClickListener { parent, itemView, position, id ->
            println("xxl-item-click: $position")
            val item = itemView.findViewById<TextView>(android.R.id.text1)
            popupAnchorTextView.text = item.text
            popupWindow.dismiss()
            listOpen = false
        }

    }

    override fun onResume() {
        super.onResume()
//        solution: use spinner
//        println("xxl-width: ${spinner.measuredWidth}; ${view?.width?:0 - (view?.paddingStart ?: 0) - (view?.paddingEnd?:0)}")
//        spinner.dropDownWidth = view?.width?:0 - (view?.paddingStart ?: 0) - (view?.paddingEnd?:0)

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

            R.id.spinner_anchor -> {

                val bgResId = if (!listOpen) {
                    popupWindow.show()
                    R.drawable.spinner_selected
                } else {
                    popupWindow.dismiss()
                    R.drawable.spinner_unselected
                }

                popupAnchorTextView.background = ContextCompat.getDrawable(requireContext(), bgResId)
                listOpen = !listOpen
            }

//            R.id.usage -> usage.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED)

//            R.id.logo -> println("xxl-click-logo")
        }
    }
}