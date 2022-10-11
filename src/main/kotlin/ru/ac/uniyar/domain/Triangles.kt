package ru.ac.uniyar.domain

class Triangles(val triangles: MutableList<Triangle> = mutableListOf<Triangle>()) {
   fun add(triangle: Triangle){
       triangles.add(triangle)
   }

    fun fetchOne(index: Int): Triangle{
        return triangles[index]
    }

    fun fetchAllTriangles(): Iterable<IndexedValue<Triangle>>{
         return triangles.withIndex()
    }


}