package com.flavor.recipes.recipe.controller

import com.flavor.recipes.core.BusinessException
import com.flavor.recipes.core.HandleException
import com.flavor.recipes.recipe.entities.RecipeEntity
import com.flavor.recipes.recipe.entities.RecipeStatus
import com.flavor.recipes.recipe.repositories.RecipeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping

@RestController
@RequestMapping("/recipe")
class RecipeController {
    @Autowired
    lateinit var recipeRepository: RecipeRepository
    @GetMapping()
    fun list(authentication: Authentication): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(recipeRepository.findByStatusNot(RecipeStatus.removed.name));
        } catch (e: Exception) {
            return HandleException().handle(e)
        }
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ResponseEntity<Any> {
        try {
            val recipe = recipeRepository.findById(id)
            if (!recipe.isPresent) {
               throw BusinessException("Receita não encontrada")
            }
            if (recipe.get().status == RecipeStatus.removed) {
                throw BusinessException("Está receita foi removida")
            }

            return ResponseEntity.ok(recipe.get())
        } catch (e: Exception) {
            return HandleException().handle(e)
        }
    }

    @PostMapping
    fun create(@RequestBody body: RecipeEntity): ResponseEntity<Any> {
        try {
            if (body.status == RecipeStatus.removed) {
                throw BusinessException("Não pode criar uma receita com status removido")
            }
            val result = recipeRepository.save(body)
            return ResponseEntity.ok(result)
        } catch (e: Exception) {
            return HandleException().handle(e)
        }
    }

    @PutMapping("/{id}")
    fun update(@RequestBody body: RecipeEntity, @PathVariable id: String): ResponseEntity<Any> {
        try {
            if (id != body.id) throw BusinessException("Id path e Id do body diferentes")
            
            val recipe = recipeRepository.findById(id)
            if (!recipe.isPresent) throw BusinessException("Receita não encontrada")
            if (recipe.get().status == RecipeStatus.removed) {
                throw BusinessException("Está receita não pode ser alterada, removida")
            }

            val result = recipeRepository.save(recipe.get().copy(
                details = body.details,
                difficultyRecipe = body.difficultyRecipe,
                images = body.images,
                ingredients = body.ingredients,
                instruction = body.instruction,
                portion = body.portion,
                serveFood = body.serveFood,
                subTitle = body.subTitle,
                timePrepared = body.timePrepared,
                title = body.title,
                status = body.status
            ))
            return ResponseEntity.ok(result)
        } catch (e: Exception) {
            return HandleException().handle(e)
        }
    }

}