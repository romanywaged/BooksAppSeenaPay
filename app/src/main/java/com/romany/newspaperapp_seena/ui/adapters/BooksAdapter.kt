package com.romany.newspaperapp_seena.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.romany.newspaperapp_seena.data.models.BookData
import com.romany.newspaperapp_seena.databinding.BookRowBinding
import com.romany.newspaperapp_seena.ui.listeners.IBookClickedListener

class BooksAdapter constructor(private var books:ArrayList<BookData>, private var iBookClickedListener: IBookClickedListener):RecyclerView.Adapter<BooksAdapter.MyBookViewHolder>() {

    var context:Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyBookViewHolder {
        context = parent.context

        return MyBookViewHolder(BookRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyBookViewHolder, position: Int) {
        val book = books[position]

        holder.binding.bookTittle.text = book.title
        holder.binding.publisherName.text = book.publisher
        holder.binding.publishedDate.text = book.createdDate.split(" ")[0]

        Glide.with(context!!).load(book.bookImage).into(holder.binding.bookRowImage)

        holder.binding.bookCard.setOnClickListener {
            iBookClickedListener.onBookClicked(book)
        }
    }

    override fun getItemCount(): Int = books.size

    class MyBookViewHolder (var binding: BookRowBinding):RecyclerView.ViewHolder(binding.root)

}