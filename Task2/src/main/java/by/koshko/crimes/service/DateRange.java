package by.koshko.crimes.service;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Objects.nonNull;

public class DateRange implements Iterable<String> {

    private YearMonth startDate;
    private YearMonth endDate;
    private List<String> datesBetween = new ArrayList<>();

    private DateRange(YearMonth startDate, YearMonth endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        listDatesBetweenRange();
    }

    public List<String> listDatesBetween() {
        return datesBetween;
    }

    public int monthBetween() {
        return datesBetween.size();
    }

    @Override
    public Iterator<String> iterator() {
        return new DateIterator();
    }

    public static DateRange build(String startDate, String endDate) throws ApplicationException {
        boolean bothDatesNotNull = nonNull(startDate) && nonNull(endDate);
        if (bothDatesNotNull) {
            YearMonth startDate0 = parse(startDate);
            YearMonth endDate0 = parse(endDate);
            checkRange(startDate0, endDate0);
            return new DateRange(startDate0, endDate0);
        }
        if (nonNull(startDate)) {
            YearMonth date = parse(startDate);
            return new DateRange(date, date);
        }
        if ((nonNull(endDate))) {
            YearMonth date = YearMonth.parse(endDate);
            return new DateRange(date, date);
        }
        throw new ApplicationException("No date provided.");
    }

    private void listDatesBetweenRange() {
        YearMonth current = startDate;
        datesBetween.add(current.toString());
        while(current.isBefore(endDate)) {
            current = current.plusMonths(1);
            datesBetween.add(current.toString());
        }
    }

    private static YearMonth parse(String date) throws ApplicationException {
        try {
            return YearMonth.parse(date);
        } catch (DateTimeParseException e) {
            throw new ApplicationException("Invalid date format. Use YYYY-mm");
        }
    }

    private static void checkRange(YearMonth startDate, YearMonth endDate) throws ApplicationException {
        if (startDate.isAfter(endDate)) {
            String errMessage = String.format("Invalid date range [%s/%s].", startDate, endDate);
            throw new ApplicationException(errMessage);
        }
    }

    private class DateIterator implements Iterator<String> {

        YearMonth current = YearMonth.of(startDate.getYear(), startDate.getMonth());

        @Override
        public boolean hasNext() {
            return current.isBefore(endDate) || current.equals(endDate);
        }

        @Override
        public String next() {
            YearMonth date = of(current);
            current = current.plusMonths(1);
            return date.toString();
        }

        private YearMonth of(YearMonth yearMonth) {
            return YearMonth.of(yearMonth.getYear(), yearMonth.getMonth());
        }
    }

    @Override
    public String toString() {
        return String.format("[%s/%s]", startDate, endDate);
    }
}
