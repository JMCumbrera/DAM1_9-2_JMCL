package un6.eje6_5

import ConnectionBuilder
import un9.jdbc.ejemplos.jdbcDAO.MyBook
import java.sql.Connection
import java.sql.Date
import java.sql.DriverManager
import java.sql.SQLException

fun main() {
    val c = ConnectionBuilder()
    println("Conectando.....")

    if (c.connection.isValid(10)) {
        println("Conexión válida")

        c.connection.use {
            val postgresDAO = CatalogoLibrosDAO(c.connection)

            println("¿Que desea hacer?")
            println("")
            println("1. Preparar tabla. Esto crea una nueva tabla o la vacía si ya existe")
            println("2. Insertar libro")
            println("3. Seleccionar libro (por id)")
            println("4. Seleccionar todos los libros")
            println("5. Borrar libro")
            println("6. Actualizar libro")
            println("7. Salir")
            println("")
            println("Introduzca su elección: ")
            val eleccion: Int? = readln().toInt()

            when (eleccion) {
                1 -> postgresDAO.prepareTable()
                2 -> {
                    var u = postgresDAO.selectBook("bk113")

                    if (u == null) {
                        postgresDAO.insertBook(MyBook(id = "bk113", title = "Example", author = "Example", genre = "Example", price = 20.99, publish_date = Date(
                            2022,3,21), description = "Example"))
                    } else {}
                }
                3 -> {
                    println("¿Que libro desea consultar?")
                    val libro: String = readln()
                    val u = postgresDAO.selectBook(libro)

                    if (u != null) {
                        println(u)
                    } else {
                        println("ERROR: No existe ese libro")
                    }
                }
                4 -> println(postgresDAO.selectAllBooks().forEach { println(it ) })
                5 -> {
                    println("¿Que libro desea eliminar?")
                    val libro: String = readln()
                    var u = postgresDAO.selectBook(libro)

                    if (u != null) {
                        postgresDAO.deleteBook(libro)
                    } else {}
                }
                6 -> {
                    println("¿Que libro desea actualizar?")
                    val libro: String = readln()
                    var u = postgresDAO.selectBook(libro)

                    if (u != null) {
                        u.title = "Nuevo libro"
                        postgresDAO.updateBook(u)
                    } else {}
                }
                7 -> System.exit(-1)
                else -> println("Elección no válida")
            }
        }
    } else
        println("Fallo en la conexión")
}

class CatalogoLibrosDAO(private val c: Connection): CatalogoLibros {

    companion object {
        private const val SCHEMA = "default"
        private const val TABLE = "books"
        private const val TRUNCATE_TABLE_BOOKS_SQL = "TRUNCATE TABLE books"
        private const val CREATE_TABLE_BOOKS_SQL =
            "CREATE TABLE books (id VARCHAR(5) PRIMARY KEY, title VARCHAR(50), author VARCHAR(30), genre VARCHAR(16), price numeric, publish_date date, description VARCHAR(1000))"
        private const val INSERT_BOOKS_SQL = "INSERT INTO books (id,title,author,genre,price,publish_date,description) VALUES " + " (?, ?, ?, ?, ?, ?, ?);"
        private const val SELECT_BOOK_BY_ID = "SELECT id,title,author,genre,price,publish_date,description from books where id = ?"
        private const val SELECT_ALL_BOOKS = "SELECT * FROM books"
        private const val DELETE_BOOKS_SQL = "DELETE FROM books WHERE id = ?;"
        private const val UPDATE_BOOKS_SQL = "UPDATE books SET title = ?, author = ?, genre = ?, price = ?, publish_date = ?, description = ? WHERE id = ?;"
    }

    fun prepareTable() {
        val metaData = c.metaData
        val rs = metaData.getTables(null, SCHEMA, TABLE, null)

        if (!rs.next()) createTable() else truncateTable()
    }

    private fun truncateTable() {
        println(TRUNCATE_TABLE_BOOKS_SQL)
        // try-with-resource statement will auto close the connection.
        try {
            c.createStatement().use { st ->
                st.execute(TRUNCATE_TABLE_BOOKS_SQL)
            }
            //Commit the change to the database
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
    }

    private fun createTable() {
        println(CREATE_TABLE_BOOKS_SQL)
        // try-with-resource statement will auto close the connection.
        try {
            //Get and instance of statement from the connection and use
            //the execute() method to execute the sql
            c.createStatement().use { st ->
                //SQL statement to create a table
                st.execute(CREATE_TABLE_BOOKS_SQL)
            }
            //Commit the change to the database
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
    }

    fun insertBook(book: MyBook) {
        println(INSERT_BOOKS_SQL)
        // try-with-resource statement will auto close the connection.
        try {
            c.prepareStatement(INSERT_BOOKS_SQL).use { st ->
                st.setString(1, book.id)
                st.setString(2, book.title)
                st.setString(3, book.author)
                st.setString(4, book.genre)
                st.setDouble(5, book.price)
                st.setDate(6, book.publish_date)
                st.setString(7, book.description)
                println(st)
                st.executeUpdate()
            }
            //Commit the change to the database
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
    }

    fun selectBook(id: String): MyBook? {
        var book: MyBook? = null
        // Step 1: Establishing a Connection
        try {
            c.prepareStatement(SELECT_BOOK_BY_ID).use { st ->
                st.setString(1, id)
                println(st)
                // Step 3: Execute the query or update query
                val rs = st.executeQuery()

                // Step 4: Process the ResultSet object.
                while (rs.next()) {
                    val title = rs.getString("title")
                    val author = rs.getString("author")
                    val genre = rs.getString("genre")
                    val price = rs.getDouble("price")
                    val publish_date = rs.getDate("publish_date")
                    val description = rs.getString("description")
                    book = MyBook(id, title, author, genre, price, publish_date, description)
                }
            }

        } catch (e: SQLException) {
            printSQLException(e)
        }
        return book
    }

    fun selectAllBooks(): List<MyBook> {
        // using try-with-resources to avoid closing resources (boiler plate code)
        val libros: MutableList<MyBook> = ArrayList()
        // Step 1: Establishing a Connection
        try {
            c.prepareStatement(SELECT_ALL_BOOKS).use { st ->
                println(st)
                // Step 3: Execute the query or update query
                val rs = st.executeQuery()

                // Step 4: Process the ResultSet object.
                while (rs.next()) {
                    val id = rs.getString("id")
                    val title = rs.getString("title")
                    val author = rs.getString("author")
                    val genre = rs.getString("genre")
                    val price = rs.getDouble("price")
                    val publish_date = rs.getDate("publish_date")
                    val description = rs.getString("description")
                    libros.add(MyBook(id, title, author, genre, price, publish_date, description))
                }
            }

        } catch (e: SQLException) {
            printSQLException(e)
        }
        return libros
    }

    fun deleteBook(id: String): Boolean {
        var rowDeleted = false

        try {
            c.prepareStatement(DELETE_BOOKS_SQL).use { st ->
                st.setString(1, id)
                rowDeleted = st.executeUpdate() > 0
            }
            //Commit the change to the database
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
        return rowDeleted
    }

    fun updateBook(book: MyBook): Boolean {
        var rowUpdated = false

        try {
            c.prepareStatement(UPDATE_BOOKS_SQL).use { st ->
                st.setString(1, book.title)
                st.setString(2, book.author)
                st.setString(3, book.genre)
                st.setDouble(4, book.price)
                st.setDate(5, book.publish_date)
                st.setString(6, book.description)
                st.setString(7, book.id)
                rowUpdated = st.executeUpdate() > 0
            }
            //Commit the change to the database
            c.commit()
        } catch (e: SQLException) {
            printSQLException(e)
        }
        return rowUpdated
    }

    private fun printSQLException(ex: SQLException) {
        for (e in ex) {
            if (e is SQLException) {
                e.printStackTrace(System.err)
                System.err.println("SQLState: " + e.sqlState)
                System.err.println("Error Code: " + e.errorCode)
                System.err.println("Message: " + e.message)
                var t = ex.cause
                while (t != null) {
                    println("Cause: $t")
                    t = t.cause
                }
            }
        }
    }

    override fun existeLibro(idLibro: String): Boolean {
        var bool = false
        val book = selectBook(idLibro)
        if (book.serializeToMap().isNotEmpty()) bool = true
        return bool
    }

    override fun infoLibro(idLibro: String): Map<String, Any> {
        val book = selectBook(idLibro)
        val emptyMap: Map<String, Any> = mapOf()

        if (existeLibro(idLibro)) { return book.serializeToMap() } else { return emptyMap }
    }
}