package com.flavor.recipes.recipe.repositories

import com.flavor.recipes.recipe.entities.RecipeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository()
interface RecipeRepository : MongoRepository<RecipeEntity, String> {
    fun findByStatusNot(status: String): List<RecipeEntity>
}