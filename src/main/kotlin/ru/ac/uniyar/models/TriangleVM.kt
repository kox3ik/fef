package ru.ac.uniyar.models

import org.http4k.template.ViewModel
import kotlin.math.sqrt

data class TriangleVM constructor(val first: Float,val second: Float,val third: Float): ViewModel{
    val S: Float
    val P: Float
    init {
        P = first + second + third
        S = sqrt(P/2*(P/2-first)*(P/2-second)*(P/2-third))
    }
}