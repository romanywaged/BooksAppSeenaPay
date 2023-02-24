package com.romany.newspaperapp_seena.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.romany.newspaperapp_seena.R
import com.romany.newspaperapp_seena.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var mBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        setSupportActionBar(mBinding.appBarMain.toolbar)

        navController = findNavController(R.id.nav_host_fragment)


        setupActionBarWithNavController(navController)

        mBinding.appBarMain.content.todayDate.text = setTodayDate()

    }

    private fun setTodayDate(): String {
        val local = Locale("en", "US")

        val sdf = SimpleDateFormat("dd MMM yyyy", local)
        val newCalender: Calendar = Calendar.getInstance()

        val currentDate = sdf.format(newCalender.time)
        val simpleDateFormat = SimpleDateFormat("EEEE", local)
        val date = Date()
        val dayName = simpleDateFormat.format(date)
        return "$dayName, $currentDate"
    }
}