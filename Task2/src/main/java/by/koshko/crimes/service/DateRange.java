package by.koshko.crimes.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

public class DateRange implements Iterable<String> {

    private static final String DATE_PATTERN = "u-MM-dd";
    private LocalDate startDate;
    private LocalDate endDate;

    public DateRange(String startDate, String endDate) {
        build(startDate, endDate);
    }

    @Override
    public Iterator<String> iterator() {
        return stream().iterator();
    }

    public Stream<String> stream() {
        return Stream
                .iterate(startDate, d -> d.plusMonths(1))
                .limit(ChronoUnit.MONTHS.between(startDate, endDate) + 1)
                .map(localDate -> localDate.format(DateTimeFormatter.ofPattern("u-MM")));
    }

    private void build(String startDate, String endDate) {
        boolean bothNotNull = nonNull(startDate) && nonNull(endDate);
        if (bothNotNull) {
            setStartDate(startDate);
            setEndDate(endDate);
            return;
        }
        if (nonNull(startDate)) {
            setStartDate(startDate);
            setEndDate(startDate);
        } else {
            setStartDate(endDate);
            setEndDate(endDate);
        }
    }

    private void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate + "-01", DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    private void setEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate + "-01", DateTimeFormatter.ofPattern(DATE_PATTERN));
    }
}
