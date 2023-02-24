package com.romany.newspaperapp_seena.ui.fragments

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.romany.newspaperapp_seena.data.models.BookData
import com.romany.newspaperapp_seena.databinding.FragmentBookDetailsBinding
import com.romany.newspaperapp_seena.ui.activities.MainActivity
import com.romany.newspaperapp_seena.ui.activities.ShowImageActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_main.*

@AndroidEntryPoint
class BookDetailsFragment : Fragment() {

    private lateinit var binding:FragmentBookDetailsBinding
    private val args: BookDetailsFragmentArgs by navArgs()
    private lateinit var book: BookData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentBookDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar
            ?.setDisplayHomeAsUpEnabled(false)

        (requireActivity() as MainActivity).title_layout.visibility = View.GONE

        book = args.book

        binding.bookDetailsTittle.text = book.title
        binding.author.text = book.author
        binding.publisherDetailsTxt.text = book.publisher
        binding.summaryTxt.text = book.description
        binding.publisherDateDetails.text = book.updatedDate.split(" ")[0]

        Glide.with(context!!).load(book.bookImage).into(binding.bookImage)

        binding.bookImage.setOnClickListener {

            val intent = Intent(context, ShowImageActivity::class.java)
            intent.putExtra("url", book.bookImage)

            val pair1 = Pair.create<View, String>(binding.bookImage, "sharedName")

            val optionsCompat: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, pair1)

            startActivity(intent, optionsCompat.toBundle())
        }
    }

}