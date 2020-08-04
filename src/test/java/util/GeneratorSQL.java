package util;

import com.airlines.model.*;

import java.util.*;

public class GeneratorSQL {

    private static final Random rand = new Random();

    private GeneratorSQL() {
    }

    public static Route getRandomRoute() {
        return new Route(UUID.randomUUID(), getRandomString(), getRandomString());
    }

    public static Airship getRandomAirship() {
        return new Airship(UUID.randomUUID(),
                getRandomString(),
                getRandomInt(5, 10),
                getRandomInt(5, 10),
                getRandomInt(5, 10));
    }

    public static Flight getRandomFlight() {
        return new Flight(UUID.randomUUID(),
                getRandomDate(),
                getRandomDate(),
                getRandomAirship(),
                getRandomRoute());
    }

    public static Client getRandomClient() {
        return new Client(UUID.randomUUID(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomFloat(0, 500));
    }

    public static Ticket getRandomTicket() {
        return new Ticket(UUID.randomUUID(),
                getRandomFlight(),
                Category.values()[getRandomInt(0, Category.values().length)],
                getRandomFloat(1000, 5000),
                getRandomFloat(1, 50),
                Status.values()[getRandomInt(0, Status.values().length)]);
    }

    public static String getRandomString() {
        int leftLimit = 1040; // letter 'А'
        int rightLimit = 1103; // letter 'я'
        int targetStringLength = 10;

        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static Date getRandomDate() {

        GregorianCalendar gc = new GregorianCalendar();
        Random random = new Random();

        int year = random.nextInt((2020 - 2000) + 1) + 2000;
        gc.set(Calendar.YEAR, year);

        int dayOfYear = random.nextInt((gc.getActualMaximum(Calendar.DAY_OF_YEAR) - 1) + 1) + 1;
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);

        gc.set(Calendar.HOUR_OF_DAY, getRandomInt(0, 24));
        gc.set(Calendar.MINUTE, getRandomInt(0, 60));
        gc.set(Calendar.SECOND, getRandomInt(0, 60));
        gc.set(Calendar.MILLISECOND, 0);

        return gc.getTime();
    }

    public static int getRandomInt(int min, int max) {
        return rand.nextInt(max - min) + min;
    }

    public static float getRandomFloat(int min, int max) {
        return rand.nextFloat() * (max - min) + min;
    }
}
