package com.example.test_task

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test_task.databinding.ItemPersonBinding

interface PersonActionListener {
    fun onPersonGetId(person: Person)
    fun onPersonRemove(person: Person)
    fun onPersonMove(person: Person, moveBy: Int)
}

class PersonAdapter(private val personActionListener: PersonActionListener) :
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>(), View.OnClickListener {
    var data: List<Person> = emptyList()
        set(newValue) {
            val newData = newValue.toMutableList()
            val personDiffUtil = PersonDiffUtilCallback(field, newData)
            val personDiffUtilResult = DiffUtil.calculateDiff(personDiffUtil)
            field = newData
            personDiffUtilResult.dispatchUpdatesTo(this@PersonAdapter)
        }

    class PersonViewHolder(val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPersonBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.more.setOnClickListener(this)
        return PersonViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = data[position]
        val context = holder.itemView.context
        holder.itemView.tag = person
        holder.binding.more.tag = person

        with(holder.binding) {
            firstnameTextView.text = person.firstname
            lastnameTextView.text = person.lastname
            Glide.with(context).load(person.img)
                .circleCrop()
                .error(R.drawable.ic_person)
                .placeholder(R.drawable.ic_person).into(imageView)

        }
    }

    override fun onClick(view: View) {
        val person: Person = view.tag as Person

        when (view.id) {
            R.id.more -> showPopupMenu(view)
            else -> personActionListener.onPersonGetId(person)
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val person = view.tag as Person
        val position = data.indexOfFirst { it.id == person.id }

        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, "Up").apply {
            isEnabled = position > 0
        }
        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, "Down").apply {
            isEnabled = position < data.size - 1
        }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, "Remove")

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MOVE_UP -> personActionListener.onPersonMove(person, -1)
                ID_MOVE_DOWN -> personActionListener.onPersonMove(person, 1)
                ID_REMOVE -> personActionListener.onPersonRemove(person)
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    companion object {
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_REMOVE = 3
    }
}
