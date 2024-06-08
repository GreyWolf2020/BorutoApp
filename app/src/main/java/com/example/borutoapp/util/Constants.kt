package com.example.borutoapp.util

object Constants {
    const val DETAILS_ARGUMENT_KEY = "heroId"
    const val HERO_DATABASE_TABLE = "hero_table"
    const val HERO_REMOTE_KEYS_DATABASE_TABLE = "hero_remote_keys_table"
    const val BORUTO_DATABASE = "boruto_database"

    const val PREFERENCE_NAME = "boruto_preferences"
    const val PREFERENCES_KEY = "onboarding_completed"

    const val IP_ADDRESS = "192.168.1.23" // assign the IP Address (server address as seen by the mobile device, hostspoting android device. Android device and server host should be in the same subnet) to IP_ADDRESS constant variable, the variable of String type.

    const val BASE_URL = "http://${IP_ADDRESS}:8080"


    const val   ONBOARDING_PAGE_COUNT = 3
    const val LAST_ONBOARDING_PAGE = 2

    const val ITEMS_PER_PAGE = 3
    const val ABOUT_TEXT_MAX_LINES = 7
    const val PAGING_INDICATOR_SPACING = 5
}