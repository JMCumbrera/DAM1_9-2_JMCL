package un6.eje6_5

import ConnectionBuilder
import java.util.logging.Level
import java.util.logging.LogManager

internal val l = LogManager.getLogManager().getLogger("").apply { level = Level.ALL }
internal fun i(tag: String, msg: String) {
    l.info("[$tag] - $msg")
}

fun main() {
    val portatil = "C:\\Users\\3kxhoDark\\IdeaProjects\\DAM1-6_5-JMCL\\src\\main\\kotlin\\Catalog.xml"
    //var casa = "/home/usuario/Documentos/workspace/IdeaProjects/IESRA-DAM/ejercicios/src/main/kotlin/un5/eje5_4/Catalog.xml"

    val jsonLibros = """[{"id":"bk101","autor":"Gambardella, Matthew","title":"XML Developer's Guide","genre":"Computer","price":44.95,"publish_date":"Oct 1, 2000 12:00:00 AM","description":"An in-depth look at creating applications with XML."}
        |,{"id":"bk102","autor":"Ralls, Kim","title":"Midnight Rain","genre":"Fantasy","price":5.95,"publish_date":"Dec 16, 2000 12:00:00 AM","description":"A former architect battles corporate zombies, an evil sorceress, and her own childhood to become queen of the world."}
        |,{"id":"bk103","autor":"Corets, Eva","title":"Maeve Ascendant","genre":"Fantasy","price":5.95,"publish_date":"Nov 17, 2000 12:00:00 AM","description":"After the collapse of a nanotechnology society in England, the young survivors lay the foundation for a new society."}
        |,{"id":"bk104","autor":"Corets, Eva","title":"Oberon's Legacy","genre":"Fantasy","price":5.95,"publish_date":"Mar 10, 2001 12:00:00 AM","description":"In post-apocalypse England, the mysterious nagent known only as Oberon helps to create a new life for the inhabitants of London. Sequel to Maeve Ascendant."}
        |,{"id":"bk105","autor":"Corets, Eva","title":"The Sundered Grail","genre":"Fantasy","price":5.95,"publish_date":"Sep 10, 2001 12:00:00 AM","description":"The two daughters of Maeve, half-sisters, battle one another for control of England. Sequel to Oberon's Legacy."}
        |,{"id":"bk106","autor":"Randall, Cynthia","title":"Lover Birds","genre":"Romance","price":4.95,"publish_date":"Sep 02, 2000 12:00:00 AM","description":"When Carla meets Paul at an ornithology conference, tempers fly as feathers get ruffled."}
        |,{"id":"bk107","autor":"Thurman, Paula","title":"Splish Splash","genre":"Romance","price":4.95,"publish_date":"Nov 02, 2000 12:00:00 AM","description":"A deep sea diver finds true love twenty thousand leagues beneath the sea."}
        |,{"id":"bk108","autor":"Knorr, Stefan","title":"Creepy Crawlies","genre":"Horror","price":4.95,"publish_date":"Dec 06, 2000 12:00:00 AM","description":"An anthology of horror stories about roaches, centipedes, scorpions  and other insects."}
        |,{"id":"bk109","autor":"Kress, Peter","title":"Paradox Lost","genre":"Science Fiction","price":6.95,"publish_date":"Nov 02, 2000 12:00:00 AM","description":"After an inadvertant trip through a Heisenberg Uncertainty Device, James Salway discovers the problems of being quantum."}
        |,{"id":"bk110","autor":"O'Brien, Tim","title":"Microsoft .NET: The Programming Bible","genre":"Computer","price":36.95,"publish_date":"Dec 09, 2000 12:00:00 AM","description":"Microsoft's .NET initiative is explored in detail in this deep programmer's reference."}
        |,{"id":"bk111","autor":"O'Brien, Tim","title":"MSXML3: A Comprehensive Guide","genre":"Computer","price":36.95,"publish_date":"Dec 01, 2000 12:00:00 AM","description":"The Microsoft MSXML3 parser is covered in detail, with attention to XML DOM interfaces, XSLT processing, SAX and more."}
        |,{"id":"bk112","autor":"Galos, Mike","title":"Visual Studio 7: A Comprehensive Guide","genre":"Computer","price":49.95,"publish_date":"Apr 16, 2001 12:00:00 AM","description":"Microsoft Visual Studio 7 is explored in depth, looking at how Visual Basic, Visual C++, C#, and ASP+ are integrated into a comprehensive development environment."}
        |]""".trimMargin()

    val c = ConnectionBuilder()

    val gestorDeLibros = gestionLibros(CatalogoLibrosXML(portatil))
    val gestorDeLibros2 = gestionLibros(CatalogoLibrosJSON(jsonLibros))
    val gestorDeLibros3 = gestionLibros(CatalogoLibrosDAO(c.connection))

    gestorDeLibros.eleccion()   // XML
    println()
    gestorDeLibros2.eleccion()  // JSON
    println()
    gestorDeLibros3.eleccion() // Base de Datos Postgres (DAO)
}

/**
 * La interfaz [GestorLibrosIU] implementa, en parte, el apartado b) del ejercicio
 */

interface GestorLibrosIU {
    fun preguntarPorUnLibro(cat: CatalogoLibros) {}
    fun mostrarInfoDeUnLibro(cat: CatalogoLibros) {}
}

/**
 * La clase [GestorDeLibrosIUT1] implementa los métodos [preguntarPorUnLibro] y [mostrarInfoDeUnLibro] en Español.
 * La traducción en Inglés se encuentra en la clase siguiente.
 */

class GestorDeLibrosIUT1(var cat: CatalogoLibros): GestorLibrosIU {
    override fun preguntarPorUnLibro(cat: CatalogoLibros) {
        println("Introduzca un ID: ")
        var idLibro = readln()
        if (cat.existeLibro(idLibro))
            println("El libro $idLibro existe!")
        else
            println("El libro $idLibro NO existe!")
    }

    override fun mostrarInfoDeUnLibro(cat: CatalogoLibros)
    {
        println("Introduzca un ID: ")
        var idLibro = readln()
        var infoLibro = cat.infoLibro(idLibro)
        if (!infoLibro.isEmpty())
            println("La información sobre el libro es la siguiente:\n$infoLibro")
        else
            println("No se encontró información sobre el libro")
    }
}

/**
 * La clase [GestorDeLibrosIUT2] tendrá los mismos métodos que la clase
 * anterior, pero con la diferencia de implementarlos en Inglés
 */

class GestorDeLibrosIUT2(var cat: CatalogoLibros): GestorLibrosIU {
    override fun preguntarPorUnLibro(cat: CatalogoLibros) {
        println("Enter an ID: ")
        var idLibro = readln()
        if (cat.existeLibro(idLibro))
            println("The book $idLibro exists!")
        else
            println("The book $idLibro DOES NOT exist!")
    }

    override fun mostrarInfoDeUnLibro(cat: CatalogoLibros)
    {
        println("Enter an ID: ")
        var idLibro = readln()
        var infoLibro = cat.infoLibro(idLibro)
        if (!infoLibro.isEmpty())
            println("The info about the book is the following:\n$infoLibro")
        else
            println("No info was found about the book")
    }
}

/**
 * La clase [Eleccion] actuará como menú, para poder elegir idioma, a través del método [interfazDeUsuario]
 */

class Eleccion(var cat: CatalogoLibros): GestorLibrosIU {
    var gestLibIUES: GestorDeLibrosIUT1 = GestorDeLibrosIUT1(cat)
    var gestLibIUEN: GestorDeLibrosIUT2 = GestorDeLibrosIUT2(cat)

    fun interfazDeUsuario() {
        println("Elija un idioma: ")
        println("1. Español")
        println("2. Inglés")
        println("")
        var choose: Int = readln().toInt()
        when (choose) {
            1 -> interfazDeUsuarioES()
            2 -> interfazDeUsuarioEN()
            else -> println("Por favor, introduzca un valor válido")
        }
    }

    private fun interfazDeUsuarioES() {
        println("¿Que operación  desea realizar?")
        println("1. Saber si un libro existe")
        println("2. Mostrar información acerca de un libro")
        println("")
        var choose: Int = readln().toInt()
        when (choose){
            1 -> gestLibIUES.preguntarPorUnLibro(cat)
            2 -> gestLibIUES.mostrarInfoDeUnLibro(cat)
            else -> println("Por favor, introduzca un valor válido por teclado")
        }
    }

    private fun interfazDeUsuarioEN() {
        println("What operation do you want to perform?")
        println("1. Find out if a book exists")
        println("2. Show info about a book")
        println("")
        var choose: Int = readln().toInt()
        when (choose){
            1 -> gestLibIUEN.preguntarPorUnLibro(cat)
            2 -> gestLibIUEN.mostrarInfoDeUnLibro(cat)
            else -> println("Please, enter a valid value by keyboard")
        }
    }
}

/**
 * La interfaz [CatalogoLibros] implementa el apartado a) del ejercicio
 */

interface CatalogoLibros {
    fun existeLibro(idLibro: String): Boolean
    fun infoLibro(idLibro: String): Map<String, Any>
}

class gestionLibros(catalogoLibros: CatalogoLibros)
{
    var cat: CatalogoLibros = catalogoLibros
    var el: Eleccion = Eleccion(cat)

    fun eleccion() {
        el.interfazDeUsuario()
    }
}