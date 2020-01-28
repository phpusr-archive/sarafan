package letscode.sarafan.service

import letscode.sarafan.domain.*
import letscode.sarafan.dto.EventType
import letscode.sarafan.dto.MessagePageDto
import letscode.sarafan.dto.MetaDto
import letscode.sarafan.dto.ObjectType
import letscode.sarafan.util.WsSender
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.function.BiConsumer
import java.util.regex.Pattern

@Service
class MessageService(
        private val messageRepo: MessageRepo,
        private val wsSender: WsSender,
        private val userSubscriptionRepo: UserSubscriptionRepo
) {
    private val UrlPattern = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+"
    private val ImagePattern = "\\.(jpeg|jpg|gif|png)$"
    private val UrlRegex = Pattern.compile(UrlPattern, Pattern.CASE_INSENSITIVE)
    private val ImgRegex = Pattern.compile(ImagePattern, Pattern.CASE_INSENSITIVE)

    private val messageSender: BiConsumer<EventType, Message>
        get() = wsSender.getSender(ObjectType.Message, Views.IdName::class.java)

    fun findForUser(pageable: Pageable, user: User): MessagePageDto {
        var channels = userSubscriptionRepo.findBySubscriber(user).map { subscription -> subscription.channel }
        channels = channels.toMutableList()
        channels.add(user)
        val page = messageRepo.findByAuthorIn(channels, pageable)
        return MessagePageDto(page.content, page.number, page.totalPages)
    }

    fun create(message: Message, user: User): Message {
        message.creationDate = LocalDateTime.now()
        fillMeta(message)
        message.author = user
        val createdMessage = messageRepo.save(message)
        messageSender.accept(EventType.Create, createdMessage)

        return createdMessage
    }

    fun update(dbMessage: Message, message: Message): Message {
        BeanUtils.copyProperties(message, dbMessage, "id", "author", "comments")
        fillMeta(dbMessage)
        val updatedMessage = messageRepo.save(dbMessage)
        messageSender.accept(EventType.Update, updatedMessage)

        return updatedMessage
    }

    fun delete(message: Message) {
        messageRepo.delete(message)
        messageSender.accept(EventType.Remove, message)
    }

    private fun fillMeta(message: Message) {
        val text = message.text ?: return

        var matcher = UrlRegex.matcher(text)

        if (matcher.find()) {
            val url = text.substring(matcher.start(), matcher.end())
            matcher = ImgRegex.matcher(url)

            message.link = url

            if (matcher.find()) {
                message.linkCover = url
            } else if (!url.contains("youtu")) {
                val meta = getMeta(url)
                message.linkCover = meta.cover
                message.linkTitle = meta.title
                message.linkDescription = meta.description
            }
        }
    }

    private fun getMeta(url: String): MetaDto {
        val doc = Jsoup.connect(url).get()
        val title = doc.select("meta[name$=title],meta[property$=title]")
        val description = doc.select("meta[name$=description],meta[property$=description]")
        val cover = doc.select("meta[name$=cover],meta[property$=cover]")

        return MetaDto(
                getContent(title.firstOrNull()),
                getContent(description.firstOrNull()),
                getContent(cover.firstOrNull())
        )
    }

    private fun getContent(element: Element?): String {
        return if (element == null) "" else element.attr("content")
    }

}