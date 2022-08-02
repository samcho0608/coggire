package com.woodigo.coggire.services

import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import com.slack.api.methods.SlackApiException
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import com.slack.api.methods.request.users.UsersListRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class SlackService {
    private val log = LoggerFactory.getLogger(javaClass)

//    @Value(value = "\${slack.token}") ®//
    var token: String? = System.getenv("SLACK_TOKEN")

    @Value(value = "\${slack.channel.monitor}")
    var channel: String? = null

    fun readUsers() {
        try {
            val methods = Slack.getInstance().methods(token)
            val response = methods.usersList(UsersListRequest.builder().build())
            log.info("${response.members}")
            response.members.filter { u -> !u.isBot }


        } catch (e: IOException) {

        }
    }

    fun postSlackMessage(message: String?) {
        try {
            val methods: MethodsClient = Slack.getInstance().methods(token)
            val request: ChatPostMessageRequest = ChatPostMessageRequest.builder()
                .channel(channel)
                .text(message)
                .build()
            methods.chatPostMessage(request)
            log.info("보냄")
        } catch (e: SlackApiException) {
            log.error(e.message)
        } catch (e: Exception) {
            log.error(e.message)
        }
    }
}