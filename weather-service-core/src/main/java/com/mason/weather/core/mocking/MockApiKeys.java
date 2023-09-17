package com.mason.weather.core.mocking;

/**
 * Enumerates mock API keys for testing and development purposes.
 *
 * @author: Mason
 * @date: 2023/9/17
 */
public enum MockApiKeys {

    API_KEY_1("api_key_1"),
    API_KEY_2("api_key_2"),
    API_KEY_3("api_key_3"),
    API_KEY_4("api_key_4"),
    API_KEY_5("api_key_5");

    private final String value;

    MockApiKeys(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
