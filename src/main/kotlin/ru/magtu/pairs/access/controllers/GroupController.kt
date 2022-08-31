package ru.magtu.pairs.access.controllers

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.jsoup.Jsoup.connect
import org.jsoup.nodes.Document
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("/groups")
class GroupController {
    fun webClient(): WebClient {
        val connectTimeout = 1000
        val readTimeout = 5000L

        val httpClient = HttpClient.create()
            .tcpConfiguration { tcpClient ->
                tcpClient
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                    .doOnConnected { it.addHandlerLast(ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS)) }
            }

        val connector = ReactorClientHttpConnector(httpClient)

        return WebClient
            .builder()
            .baseUrl("https://newlms.magtu.ru/mod/folder")
            .clientConnector(connector)
            .build()
    }

    @GetMapping()
    fun groups() =
        gatherGroups()
        .map {
            combineGroups(
                parseGroup(it.t1),
                parseGroup(it.t2),
                parseGroup(it.t3),
                parseGroup(it.t4),
            )
        }

    private fun parseGroup(groups: Document) =
        groups.getElementsByClass("fp-filename")
            .map { it.text() }.filter { it.isNotBlank() }.map { it.split(".").first() }

    private fun gatherGroups() =
        Mono.zip(
            Mono.just(connect("https://newlms.magtu.ru/mod/folder/view.php?id=1223698").get()),
            Mono.just(connect("https://newlms.magtu.ru/mod/folder/view.php?id=1223699").get()),
            Mono.just(connect("https://newlms.magtu.ru/mod/folder/view.php?id=1223700").get()),
            Mono.just(connect("https://newlms.magtu.ru/mod/folder/view.php?id=1223701").get()),
        )

    private fun combineGroups(t1: List<String>, t2: List<String>, t3: List<String>, t4: List<String>): MutableList<String> {
        val list = mutableListOf<String>()

        list.addAll(t1)
        list.addAll(t2)
        list.addAll(t3)
        list.addAll(t4)

        return list
    }

}