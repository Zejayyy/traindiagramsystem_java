package com.itsymion.service;


import com.itsymion.domain.Line;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class LineServiceTest
{

    @Autowired
    private ILineService lineService;


    @Test
    void testSave()
    {

        Line line = new Line();

        lineService.save(line);

    }
    @Test
    void testGetAll()
    {

        lineService.list(null);
    }


    @Test
    void testAlgorithm() throws IOException, ClassNotFoundException
    {
        lineService.draw(1,1,1);

    }
}
