package ru.magtu.pairs.access.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Incorrect token")
class UnknownTokenException : Exception()
