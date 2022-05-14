package com.example.chat_app.models

class User( val uid: String, val username: String, val profileImageUrl: String) {
        constructor() : this("", "", "")
}
