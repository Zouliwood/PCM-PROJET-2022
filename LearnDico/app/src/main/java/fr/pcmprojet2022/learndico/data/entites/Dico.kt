package fr.pcmprojet2022.learndico.data.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
class Dico(var nom: String,@PrimaryKey val url: String, val src: String, val dst: String)
