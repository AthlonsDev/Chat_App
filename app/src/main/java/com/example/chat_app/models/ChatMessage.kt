package com.example.chat_app.models

class ChatMessage(val id: String, val fromId: String, val toId: String, val text: String, val timestamp: Long) {
    constructor() : this("", "", "", "", 0)
}
