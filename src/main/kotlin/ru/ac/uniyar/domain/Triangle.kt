package ru.ac.uniyar.domain

import kotlin.math.sqrt

class Triangle (var sideA: Float, var sideB: Float, var sideC: Float){
   fun perimetr(): Float{
       return sideA + sideC + sideB
   }

    fun area(): Float{
        return sqrt(perimetr()/2*(perimetr()/2-sideA)*(perimetr()/2-sideB)*(perimetr()/2-sideC))
    }
}
