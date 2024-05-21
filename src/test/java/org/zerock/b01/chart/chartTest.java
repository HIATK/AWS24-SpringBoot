package org.zerock.b01.chart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.chartest;
import org.zerock.b01.repository.chartRepository;

import java.util.stream.IntStream;

@SpringBootTest
public class chartTest {

    @Autowired
    private chartRepository chartRepository;

    @Test
    public void insertTest(){
        IntStream.rangeClosed(1,100).forEach(i->{
            chartest chartest1 = chartest
                    .builder().id(1L+i).cell(1+i).item("라면"+i).build();

            chartRepository.save(chartest1);
        });
    }
}
