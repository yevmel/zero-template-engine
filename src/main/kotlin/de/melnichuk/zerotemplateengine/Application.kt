package de.melnichuk.zerotemplateengine

import org.intellij.lang.annotations.Language
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@RestController
class ZeroTemplateEngineController {

    @GetMapping
    fun getHomeScreen() = renderFrame(
        renderPersonTable(listOf(
            Person("John", "Doe"),
            Person("Jane", "Doe")
        ))
    )
}

@Language("HTML")
fun renderFrame(
    nested: String = ""
) = """
    <!DOCTYPE html>
    <html lang="en" class="h-full bg-gray-100">
    <head>
       <meta charset="UTF-8" />
       <meta name="viewport" content="width=device-width, initial-scale=1.0" />
       <title>Zero Template Engine Example</title>
       <link href="/style.css" rel="stylesheet">
    </head>
    <body class="h-full p-4">
    <div id="app" class="h-full">
        ${nested}
    </div>
    </body>
    </html>
""".trimIndent()

@Language("HTML")
fun renderPersonTableItem(
    person: Person
) = """
    <tr>
        <td class="py-4 pr-3 pl-4 text-sm font-medium whitespace-nowrap text-gray-900 sm:pl-6 dark:text-white">${person.firstName.lowercase()}.${person.lastName.lowercase()}</td>
        <td class="px-3 py-4 text-sm whitespace-nowrap text-gray-500 dark:text-gray-400">${person.firstName}</td>
        <td class="px-3 py-4 text-sm whitespace-nowrap text-gray-500 dark:text-gray-400">${person.lastName}</td>
    </tr>
""".trimIndent()

@Language("HTML")
fun renderPersonTable(
    personList: List<Person> = emptyList()
) = """
    <div class="px-4 sm:px-6 lg:px-8">
      <div class="mt-8 flow-root">
        <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
          <div class="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
            <div class="overflow-hidden shadow-sm outline-1 outline-black/5 sm:rounded-lg dark:shadow-none dark:-outline-offset-1 dark:outline-white/10">
              <table class="relative min-w-full divide-y divide-gray-300 dark:divide-white/15">
                <thead class="bg-gray-50 dark:bg-gray-800/75">
                  <tr>
                    <th scope="col" class="py-3.5 pr-3 pl-4 text-left text-sm font-semibold text-gray-900 sm:pl-6 dark:text-gray-200">Username</th>
                    <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900 dark:text-gray-200">First name</th>
                    <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900 dark:text-gray-200">Last name</th>
                  </tr>
                </thead>
                <tbody class="divide-y divide-gray-200 bg-white dark:divide-white/10 dark:bg-gray-800/50">
                  ${personList.joinToString("", transform = ::renderPersonTableItem )}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
""".trimIndent()

class Person(
    val firstName: String,
    val lastName: String
)