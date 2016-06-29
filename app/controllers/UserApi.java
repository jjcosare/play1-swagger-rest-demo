package controllers;

import com.google.gson.Gson;
import models.User;
import play.mvc.Controller;
import swagger.annotations.*;

import java.io.File;
import java.util.*;

@Api(path = "/users", description = "API for User objects.")
@SuppressWarnings("unused")
public class UserApi extends Controller {

  private static final Gson GSON = new Gson();

  private static SortedMap<Integer, User> users = new TreeMap<Integer, User>() {{
    User[] users = { new User(1, "Bas"), new User(2, "Ted"), new User(3, "Sjoerd"), new User(4, "Arian") };
    for (User user : users) {
      put(user.id, user);
    }
  }};

  @GET(
      path = "/{id}",
      summary = "Retrieve a user by id",
      responses = {
          @Response(statusCode = 200, description = "When the user was successfully retrieved"),
          @Response(statusCode = 404, description = "When there is no user with the provided id")
      }
  )
  public static void findUser(@Path(name = "id", description = "The id of the user") int id) {

    User user = users.get(id);

    if (user == null) {
      response.status = 404;
      renderText("No user found with id: " + id);
    }

    renderJSON(user);
  }

  @GET(summary = "Retrieve all users")
  public static void allUsers(@Query(name = "order", required = false, description = "The sort order: ASC or DESC") String order) {

    List<User> userList = new ArrayList<User>(users.values());

    if (order != null) {
      final boolean asc = "ASC".equalsIgnoreCase(order);
      Collections.sort(userList, new Comparator<User>() {
        @Override
        public int compare(User u1, User u2) {
          int compared = u1.name.compareToIgnoreCase(u2.name);
          return asc ? compared : -1 * compared;
        }
      });
    }

    renderJSON(userList);
  }

  @POST(
      summary = "Create a new user",
      responses = {
          @Response(statusCode = 200, description = "When the user was successfully added"),
          @Response(statusCode = 403, description = "When there is already a user with the provided id")
      }
  )
  public static void addUser(@Body(name = "user", description = "The new user") User user) {

    if (user == null) {
      // The user can be provided as query params: /api/users?user.id=4&user.name=Bob (Play
      // will serialize this into a User instance) but if that is not the case, try to fetch
      // it from the body as JSON.
      user = GSON.fromJson(request.params.get("body"), User.class);
    }

    if (users.containsKey(user.id)) {
      response.status = 403;
      renderText("There already is a user with id: " + user.id);
    }

    users.put(user.id, user);
    renderJSON(user);
  }

  @POST(
      path = "/{id}/photo",
      summary = "Add user photo",
      responses = {
            @Response(statusCode = 200, description = "When the photo was successfully added"),
            @Response(statusCode = 404, description = "When there is no user with the provided id")
      }
  )
  public static void addUserPhoto(@Path(name = "id", description = "The id of the user") Integer id,
                                  @Body(name = "photo", description = "The photo of the user") File photo,
                                  @Body(name = "dateTaken", description = "The date of the photo was taken") Date dateTaken) {

      User user = users.get(id);

      if (user == null) {
        response.status = 404;
        renderText("No user found with id: " + id);
      }

      user.photo = photo;
      renderJSON(user);
  }

  @DELETE(
      path = "/{id}",
      summary = "Delete a user by id",
      responses = {
          @Response(statusCode = 200, description = "When the user was successfully deleted"),
          @Response(statusCode = 404, description = "When there is no user with the provided id")
      }
  )
  public static void deleteUser(@Path(name = "id", description = "The id of the user") int id) {

    User user = users.remove(id);

    if (user == null) {
      response.status = 404;
      renderText("No user found with id: " + id);
    }

    renderJSON(user);
  }

  @PUT(
      path = "/{id}",
      summary = "Update an existing user",
      responses = {
          @Response(statusCode = 200, description = "When the user was successfully updated"),
          @Response(statusCode = 404, description = "When there is no user with the provided id")
      }
  )
  public static void updateUser(@Path(name = "id", description = "The id of the user to update") int id,
                                @Body(name = "user", description = "The updated user (the id is ignored)") User user) {

    if (user == null) {
      // The user can be provided as query params: /api/users?user.id=4&user.name=Bob (Play
      // will serialize this into a User instance) but if that is not the case, try to fetch
      // it from the body as JSON.
      user = GSON.fromJson(request.params.get("body"), User.class);
    }

    // Ignore the id in the payload: the id from the path is leading.
    user.id = id;

    if (!users.containsKey(id)) {
      response.status = 404;
      renderText("No user found with id: " + id);
    }

    users.put(id, user);
    renderJSON(users.get(id));
  }

  @PATCH(
      path = "/{id}",
      summary = "Patch an existing user",
      responses = {
          @Response(statusCode = 200, description = "When the user was successfully patched"),
          @Response(statusCode = 404, description = "When there is no user with the provided id")
      }
  )
  public static void patchUser(@Path(name = "id", description = "The id of the user to update") int id,
                               @Body(name = "user", description = "The user to patch (the id is ignored)") User user) {

    if (user == null) {
      // The user can be provided as query params: /api/users?user.id=4&user.name=Bob (Play
      // will serialize this into a User instance) but if that is not the case, try to fetch
      // it from the body as JSON.
      user = GSON.fromJson(request.params.get("body"), User.class);
    }

    if (!users.containsKey(id)) {
      response.status = 404;
      renderText("No user found with id: " + id);
    }

    User existing = users.remove(id);

    // Only update when field is non-null.
    existing.name = user.name == null ? existing.name : user.name;

    users.put(id, existing);
    renderJSON(existing);
  }
}