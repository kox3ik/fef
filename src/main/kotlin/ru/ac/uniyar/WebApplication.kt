package ru.ac.uniyar

import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.body.form
import org.http4k.filter.DebuggingFilters.PrintRequestAndResponse
import org.http4k.routing.*
import org.http4k.server.Undertow
import org.http4k.server.asServer
import org.http4k.template.PebbleTemplates
import org.http4k.template.TemplateRenderer
import ru.ac.uniyar.models.NewTriangleDataVM
import ru.ac.uniyar.models.TriangleVM
import ru.ac.uniyar.domain.Triangle
import ru.ac.uniyar.domain.Triangles
import ru.ac.uniyar.models.TrianglesListVM


fun respondWithPong(): HttpHandler = {
    Response(OK).body("pong")
}

fun app(renderer: TemplateRenderer, triangles: Triangles): HttpHandler = routes(
    "/ping" bind GET to respondWithPong(),
    "/triangle/new" bind GET to showNewTriangleForm(renderer),
    "/triangle/new" bind Method.POST to createNewTriangle(triangles),
    "/triangle" bind GET to showTriangle(renderer, triangles),
    "/triangle/{number}" bind GET to showTriangle(renderer, triangles),
    "/" bind GET to redirectToTriangleList(),

    static(ResourceLoader.Classpath("/ru/ac/uniyar/public/")),
)

fun main() {
    val triangles = Triangles()
    val renderer = PebbleTemplates().HotReload("src/main/resources")
    val printingApp: HttpHandler = PrintRequestAndResponse().then(app(renderer, triangles))
    val server = printingApp.asServer(Undertow(9000)).start()
    println("Server started on http://localhost:" + server.port() + "/triangle")
}
fun showTriangle(renderer: TemplateRenderer, triangles: Triangles): HttpHandler = {
    request -> val number = request.path("number").orEmpty().toInt()-1
    val triangle = triangles.fetchOne(number)
    val triangle1 = TriangleVM(triangle.sideA,triangle.sideB,triangle.sideC)
    val d = renderer(triangle1)

    Response(OK).body(d)
}

fun showNewTriangleForm(renderer: TemplateRenderer): HttpHandler = {
    val form = NewTriangleDataVM(0)
    val d = renderer(form)
    Response(OK).body(d)
}

fun createNewTriangle(triangles: Triangles): HttpHandler = {
    request ->  val form = request.form()
            val first = form.findSingle("side_one").orEmpty().toFloat()
    val second = form.findSingle("side_two").orEmpty().toFloat()
    val third = form.findSingle("side_three").orEmpty().toFloat()
    val triangle = Triangle(1.1f,1.1f,1.1f)
    triangle.sideA = first
    triangle.sideB = second
    triangle.sideC = third
    triangles.add(triangle)

    Response(FOUND).header("Location", "/triangle")
}

fun showTriangleList(renderer: TemplateRenderer, triangles: Triangles): HttpHandler = {
    val trianglesVm = TrianglesListVM(triangles.fetchAllTriangles())
    val d = renderer(trianglesVm)

    Response(OK).body(d)
}

fun redirectToTriangleList(): HttpHandler = {
    Response(FOUND).header("Location", "/triangle")
}