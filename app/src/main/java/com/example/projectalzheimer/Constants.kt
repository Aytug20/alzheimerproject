package com.example.projectalzheimer

import com.example.projectalzheimer.model.Exercise

object Constants {
    fun defaultExerciseList(): ArrayList<Exercise>{
        val exerciseList = ArrayList<Exercise>()
        val su = Exercise(
            1,
            "su",
            R.drawable.su,
            false,
            false
        )
        exerciseList.add(su)
        val kitap = Exercise(
            2,
            "kitap",
            R.drawable.kitap,
            false,
            false,
        )
        exerciseList.add(kitap)
        val walk = Exercise(
            3,
            "walk",
            R.drawable.walk,
            false,
            false,
        )
        exerciseList.add(walk)
        val yapboz = Exercise(
            4,
            "yapboz",
            R.drawable.yapboz,
            false,
            false,
        )
        exerciseList.add(yapboz)
        val yemek = Exercise(
            5,
            "yemek",
            R.drawable.yemek,
            false,
            false,
        )
        exerciseList.add(yemek)
        val hap = Exercise(
            6,
            "hap",
            R.drawable.hap,
            false,
            false,
        )
        exerciseList.add(hap)
        val dans = Exercise(
            7,
            "dans",
            R.drawable.dans,
            false,
            false,
        )
        exerciseList.add(dans)
        val music = Exercise(
            8,
            "music",
            R.drawable.music,
            false,
            false,
        )
        exerciseList.add(music)
        val tiyatro = Exercise(
            9,
            "tiyatro",
            R.drawable.tiyatro,
            false,
            false,
        )
        exerciseList.add(tiyatro)
        val hayvan = Exercise(
            10,
            "hayvan",
            R.drawable.hayvan,
            false,
            false,
        )
        exerciseList.add(hayvan)
        val resim = Exercise(
            11,
            "resim",
            R.drawable.resim,
            false,
            false,
        )
        exerciseList.add(resim)
        val bardak = Exercise(
            12,
            "bardak",
            R.drawable.bardak,
            false,
            false,
        )
        exerciseList.add(bardak)






        return exerciseList
    }
}