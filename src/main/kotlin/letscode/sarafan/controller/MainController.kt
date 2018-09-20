package letscode.sarafan.controller

import letscode.sarafan.exceptions.NotFoundException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("message")
class MainController {


    private val messages = mutableListOf(
            mutableMapOf("id" to "1", "text" to "First message"),
            mutableMapOf("id" to "2", "text" to "Second message"),
            mutableMapOf("id" to "3", "text" to "Third message")
    )
    private var counter = messages.size + 1

    @GetMapping
    fun list(): List<Map<String, String>> {
        return messages
    }

    @GetMapping("{id}")
    fun getOne(@PathVariable id: String) = getMessage(id)

    private fun getMessage(id: String) = messages.find { it["id"] == id } ?: throw NotFoundException()

    @PostMapping
    fun create(@RequestBody message: MutableMap<String, String>): MutableMap<String, String> {
        message["id"] = counter++.toString()
        messages.add(message)

        return message
    }

    @PutMapping("{id}")
    fun update(@RequestBody message: MutableMap<String, String>, @PathVariable id: String): MutableMap<String, String> {
        val dbMessage = getMessage(id)
        dbMessage.putAll(message)
        dbMessage["id"] = id

        return dbMessage
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: String): String {
        messages.remove(getMessage(id))

        return "OK"
    }

}