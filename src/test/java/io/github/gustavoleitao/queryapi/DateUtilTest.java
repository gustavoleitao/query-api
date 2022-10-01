package io.github.gustavoleitao.queryapi;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    private Date getDateUTC(int year, int month, int day, int hour, int min, int sec, int ms){
        LocalDateTime expected = LocalDateTime.of(year,month,day, hour, min, sec, ms * 1000000);
        return Date.from(expected.toInstant(ZoneOffset.UTC));
    }

    @Test
    public void parseUTC() {
        Date date = DateUtil.parseDate("2022-07-11T00:00:00Z");
        assertEquals(getDateUTC(2022,7, 11, 0,0,0, 0), date);
    }

    @Test
    public void parseWithoutTimezone() {
        Date date = DateUtil.parseDate("2021-09-11T00:00:00");
        assertEquals(getDateUTC(2021,9, 11, 0,0,0, 0), date);
    }

    @Test
    public void parseWithTimeWithoutTimezone() {
        Date date = DateUtil.parseDate("2021-09-11T15:16:17");
        assertEquals(getDateUTC(2021,9, 11, 15,16,17, 0), date);
    }

    @Test
    public void parseWithMillis() {
        Date date = DateUtil.parseDate("2021-10-22T23:19:40.987Z");
        assertEquals(getDateUTC(2021,10, 22, 23,19,40, 987), date);
    }

    @Test
    public void parseWithWuthoutTMillis() {
        Date date = DateUtil.parseDate("2021-10-22 23:19:40.987Z");
        assertEquals(getDateUTC(2021,10, 22, 23,19,40, 987), date);
    }

    @Test
    public void parseWithWuthoutTAndZMillis() {
        Date date = DateUtil.parseDate("1984-10-25 22:23:24.765");
        assertEquals(getDateUTC(1984,10, 25, 22,23,24, 765), date);
    }

    @Test
    public void parseWithoutTime() {
        Date date = DateUtil.parseDate("2022-08-11");
        assertEquals(getDateUTC(2022,8, 11, 0,0,0, 0), date);
    }

}