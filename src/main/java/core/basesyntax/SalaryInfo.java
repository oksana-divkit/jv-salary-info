package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class SalaryInfo {
    private static final int INDEX_OF_DATE = 0;
    private static final int INDEX_OF_NAME = 1;
    private static final int INDEX_OF_HOURS = 2;
    private static final int INDEX_OF_RATE = 3;
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo) {
        int[] salaries = new int[names.length];
        List<String> namesList = Arrays.asList(names);

        LocalDate parsedDateFrom = parseDate(dateFrom);
        LocalDate parsedDateTo = parseDate(dateTo);

        for (String record : data) {
            String[] parsedRecord = record.split(" ");
            LocalDate date = parseDate(parsedRecord[INDEX_OF_DATE]);
            String name = parsedRecord[INDEX_OF_NAME];
            int hours = Integer.parseInt(parsedRecord[INDEX_OF_HOURS]);
            int rate = Integer.parseInt(parsedRecord[INDEX_OF_RATE]);

            int indexOfRow = namesList.indexOf(name);

            if (date.compareTo(parsedDateFrom) >= 0
                    && date.compareTo(parsedDateTo) <= 0
                    && indexOfRow != -1) {
                salaries[indexOfRow] += hours * rate;
            }
        }

        String header = "Report for period " + dateFrom + " - " + dateTo;

        return generateReport(header, names, salaries);
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date, formatter);
    }

    private String generateReport(String header, String[] names, int[] salaries) {
        StringBuilder reportBuilder = new StringBuilder(header);

        for (int i = 0; i < names.length; i++) {
            reportBuilder.append(LINE_SEPARATOR)
                    .append(names[i])
                    .append(" - ")
                    .append(salaries[i]);
        }

        return reportBuilder.toString();
    }
}
