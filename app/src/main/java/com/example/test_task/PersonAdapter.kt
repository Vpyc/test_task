package com.example.test_task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test_task.databinding.ItemPersonBinding

class PersonAdapter : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {
    var data: List<Person> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class PersonViewHolder(val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPersonBinding.inflate(inflater, parent, false)

        return PersonViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = data[position]
        val context = holder.itemView.context

        with(holder.binding) {
            firstnameTextView.text = person.firstname
            lastnameTextView.text = person.lastname
            Glide.with(context).load(person.img)
                .circleCrop()
                .error(R.drawable.ic_person)
                .placeholder(R.drawable.ic_person).into(imageView)
        }
    }
}