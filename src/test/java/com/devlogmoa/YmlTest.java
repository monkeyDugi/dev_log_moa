package com.devlogmoa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class YmlTest {

    @Autowired
    Yml yml;

    @Test
    public void yml() {
        List<MyPojo> keys = yml.getBlog();

        for (MyPojo key : keys) {
            System.out.println("key.toString() = " + key.toString());
        }
    }
}