package com.github.polomarcus.model

import java.sql.Timestamp

case class News (title: String,
                 description: String,
                 media: String,
                 timestamp: Timestamp)