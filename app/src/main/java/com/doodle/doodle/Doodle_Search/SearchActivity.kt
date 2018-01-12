package com.doodle.doodle.Doodle_Search

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    // 필명 recyclerView
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private var searchPageAdapter: SearchPageAdapter? = null
    private var editKeyword: EditText? = null
    private var token: String? = null
    private var pref: SharedPreferences? = null
    private var networkService: NetworkService? = null
    var searchNameFragment: SearchNameFragment? = null
    var searchWordingFragment: SearchWordingFragment? = null

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        cancle.setOnClickListener { search_keyword.setText("") }

        tabLayout = findViewById(R.id.search_tabLayout)
        viewPager = findViewById(R.id.search_viewPager)
        editKeyword = findViewById(R.id.search_keyword)

        //fragmentmanger를 이용한 searchpageradapter 초기화
        searchPageAdapter = SearchPageAdapter(supportFragmentManager)
        networkService = ApplicationController.instance!!.networkService


        viewPager!!.adapter = searchPageAdapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        viewPager!!.currentItem = 0

        tabLayout!!.setSelectedTabIndicatorColor(R.color.maincolor)
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
        // 토큰 가져오기
        pref = getSharedPreferences("token", Context.MODE_PRIVATE)
        token = pref!!.getString("token", "")
        editKeyword!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (viewPager!!.currentItem == 0)
                    SearchData.searchNameFragment!!.networking(editKeyword!!.text.toString())
                else if (viewPager!!.currentItem ==1)
                    SearchData.searchWordingFragment!!.networking(editKeyword!!.text.toString())


            }

        })

    }


}
