package br.bruno.greenmiledesafio;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GreenmileDesafioApplicationTests {

	@Test
	public void contextLoads() {
		int expected = 1;
		int actual = 1;
		Assert.assertEquals(expected,actual);
	}

}
