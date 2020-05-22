package com.six.ui.tabLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.six.ui.R

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-19.
 */
class OffersFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        TextView titleTextView = new TextView(getActivity());
//        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
//        titleTextView.setText(R.string.txt_offers);
//        return titleTextView;
        return inflater.inflate(R.layout.frag_offer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val wv = view.findViewById<WebView>(R.id.wv)
        wv.loadUrl("https://www.google.ca/")

        val et = view.findViewById<EditText>(R.id.et_online)
        et.setText(R.string.card_num_hint)
    }
}