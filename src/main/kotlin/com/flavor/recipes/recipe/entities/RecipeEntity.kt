package com.flavor.recipes.recipe.entities

import com.flavor.recipes.user.entities.DifficultyRecipes
import jakarta.persistence.*
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "recipe")
data class RecipeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: String?,
    val title: String,
    val subTitle: String,
    val images: List<String>,
    val details: String,
    val timePrepared: Int,
    val difficultyRecipe: DifficultyRecipes,
    val portion: Int,
    val instruction: String,
    val serveFood: String,
    val ingredients: Set<RecipeIngredientEntity>,
    val status: RecipeStatus,
)
