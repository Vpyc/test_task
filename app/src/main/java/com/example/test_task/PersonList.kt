package com.example.test_task

import com.example.test_task.room.PersonInfo
import java.util.Collections


class PersonList {
    private var persons = mutableListOf<Person>()
    var isLoaded = false

    fun addPersons(personsDb: List<PersonInfo>) {
        persons = personsDb.map { user ->
            Person(
                id = user.id,
                firstname = user.firstName,
                lastname = user.lastName,
                company = user.companyName,
                img = user.img
            )
        }.toMutableList()
        isLoaded = true
    }

    fun getPersons(): List<Person> = persons

    companion object {
        private val SRC = mutableListOf(
            "https://grandgames.net/puzzle/source/kotik_1.jpg",
            "https://gas-kvas.com/grafic/uploads/posts/2023-09/1695824612_gas-kvas-com-p-kartinki-lisa-4.jpg",
            "https://fikiwiki.com/uploads/posts/2022-02/1644996638_26-fikiwiki-com-p-lisi-krasivie-kartinki-29.jpg",
            "https://get.wallhere.com/photo/face-cat-Asian-wildlife-fur-nose-kittens-whiskers-wild-cat-Iris-eye-cats-kitten-fauna-mammal-organ-close-up-cat-like-mammal-macro-photography-snout-small-to-medium-sized-cats-carnivoran-tabby-cat-domestic-short-haired-cat-bengal-somali-puss-1152x804-px-lovely-desktop-images-cat-images-cat-photos-cat-wallpapers-cutties-feline-pictures-on-the-wall-pussycats-aegean-cat-802659.jpg"
        )
    }

    fun removePerson(person: Person) {
        val position = persons.indexOfFirst { it.id == person.id }
        if (position == -1) return
        persons.removeAt(position)
        notifyChanges()
    }

    fun movePerson(person: Person, moveBy: Int) {
        val oldPosition = persons.indexOfFirst { it.id == person.id }
        if (oldPosition == -1) return
        val newPosition = oldPosition + moveBy
        Collections.swap(persons, oldPosition, newPosition)
        notifyChanges()
    }
    private var listeners = mutableListOf<PersonListener>() // Все слушатели
    fun addListener(listener: PersonListener) {
        listeners.add(listener)
        listener.invoke(persons)
    }

    fun removeListener(listener: PersonListener) {
        listeners.remove(listener)
        listener.invoke(persons)
    }

    private fun notifyChanges() = listeners.forEach { it.invoke(persons) }
}

typealias PersonListener = (persons: List<Person>) -> Unit

