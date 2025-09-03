package dan.website.blog.blog;

import dan.website.blog.blog.config.BaseTestWithContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BlogApplicationTests extends BaseTestWithContext {

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("dummy test")
	void dummyTest() {
		assertThat(true).isTrue();
	}

}
