package com.woodigo.coggire.scheduler

import com.woodigo.coggire.services.SlackService
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled


@RequiredArgsConstructor
@EnableScheduling
@Configuration
class SlackScheduler(
    private val slackService: SlackService
) {
    @Scheduled(cron = "0 0/1 * * * *") //1분
    fun todayCocktail() {
//        slackService.readUsers()
        slackService.postSlackMessage("안녕..")
    }
}