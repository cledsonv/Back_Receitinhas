package com.flavor.recipes.recipe.entities

import jakarta.persistence.*

@Entity
data class InstructionEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val description : String,

    @OneToOne
    val recipes : RecipeEntity,
)