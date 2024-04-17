package com.example.test_task

import com.github.javafaker.Faker
import java.util.Locale


class PersonList {
    private var persons = mutableListOf<Person>()

    init {
        val faker = Faker(Locale("ru"))

        persons = (1..100).map {
            Person(
                firstname = faker.name().firstName(),
                lastname = faker.name().lastName(),
                company = faker.company().name(),
                img = SRC[it % SRC.size],
            )
        }.toMutableList()
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
}