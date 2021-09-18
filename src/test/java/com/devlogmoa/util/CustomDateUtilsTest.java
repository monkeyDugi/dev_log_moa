package com.devlogmoa.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomDateUtilsTest {

    @Test
    @DisplayName("Date 타입을 LocalDate 타입으로 반환한다.")
    void parseLocalDate() {
        // given
        Date date = new Date();

        // when
        LocalDate localDate = CustomDateUtils.parseLocalDate(date);

        // then
        assertThat(localDate).isInstanceOf(LocalDate.class);
    }

    @Test
    @DisplayName("Date가 null이면 NPE")
    void null_parseLocalDate() {
        // then
        assertThrows(NullPointerException.class, () ->
            CustomDateUtils.parseLocalDate(null)
        );
    }
}