package ru.magtu.pairs.access

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Incorrect token")
class UnknownTokenException : Exception()
