package controllers;

import models.Book;
import play.mvc.Controller;
import swagger.annotations.Api;
import swagger.annotations.GET;
import swagger.annotations.Path;
import swagger.annotations.Response;

import java.util.SortedMap;
import java.util.TreeMap;

@Api(path = "/books", description = "API for Book objects.")
@SuppressWarnings("unused")
public class BookApi extends Controller {

  private static SortedMap<String, Book> books = new TreeMap<String, Book>(){{
    Book[] array = { new Book("1", "De Meester en Margarita"), new Book("2", "Schuld en Boete") };
    for (Book book : array) {
      put(book.isbn, book);
    }
  }};

  @GET(
      path = "/{isbn}",
      summary = "Retrieve a Book by ISBN",
      responses = {
          @Response(statusCode = 200, description = "When the Book was successfully retrieved"),
          @Response(statusCode = 404, description = "When there is no Book with the provided ISBN")
      }
  )
  public static void findBook(@Path(name = "isbn", description = "The ISBN of the Book") String isbn) {

    Book book = books.get(isbn);

    if (book == null) {
      response.status = 404;
      renderText("No Book found with id: " + isbn);
    }

    renderJSON(book);
  }

  @GET(summary = "Retrieve all Books")
  public static void allBooka() {
    renderJSON(books.values());
  }
}