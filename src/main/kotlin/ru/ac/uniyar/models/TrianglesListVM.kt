package ru.ac.uniyar.models


import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.Triangle

data class TrianglesListVM(val triangles: Iterable<IndexedValue<Triangle>>) : ViewModel {
}
