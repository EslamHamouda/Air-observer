package com.example.airobserver.ui.home.home_fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.ekndev.gaugelibrary.Range
import com.example.airobserver.databinding.FragmentHomeBinding
import com.example.airobserver.domain.model.gasmodel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*binding.aqi.settings.javaScriptEnabled = true;
        binding.aqi.loadUrl("file:///android_asset/index.html")
        binding.aqi.setOnTouchListener { v, event ->
            event.action == MotionEvent.ACTION_MOVE
        }
        binding.aqi.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                view.evaluateJavascript("setDial(500)", null);
            }
        }*/
        val range = Range()
        range.color = Color.parseColor("#FF0000")
        range.from = 0.0
        range.to = 50.0

        val range2 = Range()
        range2.color = Color.parseColor("#ffff00")
        range2.from = 50.0
        range2.to = 100.0



        //add color ranges to gauge
        binding.gauge.addRange(range)
        binding.gauge.addRange(range2)


        //set min max and current value
        binding.gauge.minValue = 0.0
        binding.gauge.maxValue = 150.0
        binding.gauge.value = 50.0

        val list = arrayListOf<gasmodel>()
        list.add(gasmodel("PMS2.5","Particulate Matter",22,"#FF0000","Good"))
        list.add(gasmodel("PMS10","Particulate Matter",25,"#8f3f97","Good"))
        list.add(gasmodel("CO","Carbon monoxide",22,"#7e0023","Good"))
        list.add(gasmodel("NO2","Nitrogen dioxide",33,"#00e400","Good"))
        list.add(gasmodel("SO2","PSulfur dioxide",15,"#ffff00","Good"))
        list.add(gasmodel("O3","Ozone",10,"#ff7e00","Good"))
        binding.rvDetailedReadings.adapter=DetailedReadingsAdapter(list)

    }

}