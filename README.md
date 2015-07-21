# Play1 Swagger + REST demo

A small demo how to create RESTful services on top of the (old!) Play 1 framework and
annotate its endpoints so that a Swagger UI can be used to create a REST client in your
browser.

## How does it work

To see how to set up a service/api, have a look at the class `controllers.UserApi` in 
the `app/` folder. The annotations are pretty self-explanatory. After the Play app is 
started, the first request coming in will trigger the method `swagger.SwaggerBootstrap#doJob()`.
This method will scan the classpath looking for classes annotated with `Api`. These files
are then inspected for annotated methods like `GET`, `POST`, `PUT`, etc. At the end of 
`swagger.SwaggerBootstrap#doJob()`, all discovered endpoints are dynamically added to
Play's `Router`. No need to define the endpoints manually to `./conf/routes`!

After all classes, methods and their parameters are scanned, a `swagger.model.SwaggerObject`
instance is created which is used by the [static Swagger UI](https://github.com/swagger-api/swagger-ui) 
placed in the folder `/public/swagger` and, after running the project, available under 
[localhost:9000/swagger](http://localhost:9000/swagger)

This SwaggerObject is based on the official 
[Swagger 2.0 specs](https://github.com/swagger-api/swagger-spec/blob/master/versions/2.0.md#swagger-object). 
Only a small part of the spec is implemented, but it already produces a nice REST client.

## Get it running

Assuming you have `play 1.2.x` in your PATH, to get it running, clone this repository and 
execute Play's `run` command:

```
git clone https://github.com/bkiers/play1-swagger-rest-demo
cd play1-swagger-rest-demo
play run
```

Then open a browser and point it to [localhost:9000/swagger](http://localhost:9000/swagger). You 
should see something like this:

![swagger](https://raw.githubusercontent.com/bkiers/play1-swagger-rest-demo/master/static/swagger.png)

## IDE

To integrate the project in your IDE, cd into the project and execute either `play idealize` or, if 
you must, `play eclipsify` :)