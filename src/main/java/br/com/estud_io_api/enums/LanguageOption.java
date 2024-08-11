package br.com.estud_io_api.enums;

public enum LanguageOption {
    PT_BR(1, "Portuguese (Brazil)"),
    ENGLISH(2, "English");

    private final int value;
    private final String displayName;

    LanguageOption(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static LanguageOption fromValue(int value) {
        for (LanguageOption languageOption : LanguageOption.values()) {
            if (languageOption.value == value) {
                return languageOption;
            }
        }
        throw new IllegalArgumentException("Invalid language option value: " + value);
    }
}

