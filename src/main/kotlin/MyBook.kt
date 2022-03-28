package un9.jdbc.ejemplos.jdbcDAO

data class MyBook(val id: String, var title: String, val author: String, val genre: String,
                  val price: Double, val publish_date: java.sql.Date, val description: String)