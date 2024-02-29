package com.slyked.poojasamagri.ui

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.databinding.ActivityAboutUsBinding
import com.slyked.poojasamagri.utils.CommonMethods
import java.lang.String.format


class AboutUsActivity : AppCompatActivity() {
    lateinit var binding:ActivityAboutUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val s: String = getString(R.string.about_us_description)
        binding.notFound.text = Html.fromHtml(s)
    }
}