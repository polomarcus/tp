package com.github.polomarcus.model

import java.sql.Timestamp


case class News (title: String,
                 date: Timestamp,
                 url: String,
                 media: String,
                 containsWordGlobalWarming : Boolean = false)