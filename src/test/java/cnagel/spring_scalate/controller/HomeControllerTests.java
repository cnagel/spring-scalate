
package cnagel.spring_scalate.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Contains;
import org.mockito.internal.matchers.StartsWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cnagel.spring_scalate.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration("classpath:.")
public class HomeControllerTests {

	@Autowired
	private WebApplicationContext ctx;

	private MockMvc mvc;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	@Test
	public void test_index() throws Exception {
		mvc.perform(get("/")).andExpect(status().isOk()).andExpect(
				content().string(new Contains("<label>Say what ever:</label>"))).andExpect(
				content().string(new StartsWith("<!DOCTYPE html>")));
	}

	@Test
	public void test_post() throws Exception {
		mvc.perform(post("/").param("text", "Hello")).andExpect(status().isOk()).andExpect(
				content().string(new Contains("<li>Hello</li>")));
	}

}
