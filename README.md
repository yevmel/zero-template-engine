# Zero Template Engine

Clint-side solutions for web development like react, vue and co. got a lot of attention in the last years, but server-side rendering in still alive and kicking (and even growing in popularity again 😀).
Traditionally a template engine is used to render HTML on the server, which is then delivered to the client. Most of the time using a template engine boils down to these two features:
* combining static and dynamic content
* composition of UI elements

Both functionalities do not require a template engine these days. All mainstream programming languages support multiline strings and variable interpolation making handling of large static content and combining it with dynamic parts an easy task. Example in Kotlin:

```kotlin
fun renderHtml(title: String, nested: String) = """
  <!DOCTYPE html>
  <html lang="en" class="h-full">
  <head>
     <meta charset="UTF-8" />
     <meta name="viewport" content="width=device-width, initial-scale=1.0" />
     <title>${title}</title>
     <link href="style.css" rel="stylesheet">
  </head>
  <body class="h-full">
    ${nested}
    <script src="index.js"></script>
  </body>
  </html>
""".trimIndent()
```

While in the past "separation of concerns" always was one of the most praised features of template engines it never truly delivered on its promise. A web designer was never able to work with templates in isolation (except maybe in case of [Thymeleaf](https://www.thymeleaf.org/#natural-templates) under very limited circumstances). At the same time solutions like react and vue demonstrated how beneficial co-location of templates and the associated code is. In case of Spring it would be a controller rendering the HTML directly:

```Kotlin
@RestController
class PersonController(
  val personService: PersonService
) {

  @GetMapping("{id}")
  fun getPersonForm(
    @PathVariable id: String
  ): String {
    val person = personService.findById(id)
    return renderFrame(
      nested = renderPersonForm(person)
    )
  }
}

fun renderPersonForm(person: Person) = """
    <form>
        <label for="first-name">First name</label>
        <input name="first-name" value="${person.firstName}">
    </form>
""".trimIndent()

fun renderFrame(
  nested: String = ""
) = """
<html>
    <body>
        ${nested}
    </body>
</html>
""".trimIndent()
```

You are right stating that a template engine provides more features I didn't even mention here, but I would argue they are worth the downside of added complexity, which comes with a template engine.

## Intellij got your back!

### Syntax highlighting and code completion

A functionality of Intellij IDEA called [language injection](https://www.jetbrains.com/help/idea/using-language-injections.html) makes working with HTML (or XML, JSON, etc.) in your code easy by providing syntax highlighting and code completion:
![Screenshot of a string in kotlin containing HTML with syntax highlighting provided by Intellij.](/language-injection-intellij.png)

### Hot reload

Hot reload is a technique where code changes are applied immediately to a running application, without requiring a full restart. This has two major benefits:

* The current state of the application is preserved
* Development cycles are faster, since the application does not need to be restarted

While IntelliJ does not provide true hot reload functionality, it does support hotswap when running in debug mode. You can execute [Reload Changed Classes](https://www.jetbrains.com/help/idea/altering-the-program-s-execution-flow.html#reload_classes) after modifying your template and then trigger a re-render of the affected part of your application (for example, by refreshing the page in your browser).