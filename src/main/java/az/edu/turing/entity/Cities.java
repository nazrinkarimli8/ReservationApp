package az.edu.turing.entity;

public enum Cities {
    LONDON, PARIS, NEW_YORK, TOKYO, DUBAI, ISTANBUL, SINGAPORE, KUALA_LUMPUR, SEOUL, HONG_KONG, BAKU, BANGKOK,
    LOS_ANGELES, BARCELONA, ROME, MILAN, MADRID, BERLIN, MUNICH, FRANKFURT, AMSTERDAM, VIENNA, ZURICH, GENEVA;

    public static Cities fromString(String name) {
        try {
            return Cities.valueOf(name.trim().replace(" ", "_").toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid city name: " + name);
            return null;
        }
    }
}

