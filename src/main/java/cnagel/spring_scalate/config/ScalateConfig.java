/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cnagel.spring_scalate.config;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;

import org.fusesource.scalate.layout.DefaultLayoutStrategy;
import org.fusesource.scalate.servlet.Config;
import org.fusesource.scalate.servlet.ServletTemplateEngine;
import org.fusesource.scalate.spring.view.ScalateViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.context.ServletContextAware;

import scala.collection.JavaConversions;

/**
 * The scalate configuration. Initializes the template path, default extension and
 * layouts.
 * 
 * @author cnagel
 */
@Configuration
public class ScalateConfig {

	/**
	 * The template path
	 */
	public static final String DEFAULT_PREFIX = "/WEB-INF/templates/";

	/**
	 * The template extension
	 */
	public static final String DEFAULT_SUFFIX = ".jade";

	/**
	 * The default layout to use
	 */
	public static final String DEFAULT_LAYOUT = "/WEB-INF/templates/layouts/default.jade";

	/**
	 * The scalate dummy config configuration. Needs the servlet context.
	 * 
	 * @author cnagel
	 */
	@Configuration
	protected static class ScalateConfigConfiguration implements ServletContextAware {

		private ServletContext servletContext;

		@Override
		public void setServletContext(ServletContext servletContext) {
			this.servletContext = servletContext;
		}

		@Bean
		public Config config() {
			return new Config() {

				@Override
				public ServletContext getServletContext() {
					return servletContext;
				}

				@Override
				public String getName() {
					return "unknown";
				}

				@Override
				public Enumeration<?> getInitParameterNames() {
					return null;
				}

				@Override
				public String getInitParameter(String name) {
					return null;
				}
			};
		}

	}

	/**
	 * The scalate servlet template engine configuration, initializing the default layout.
	 * 
	 * @author cnagel
	 */
	@Configuration
	protected static class ScalateServletTemplateEngineConfiguration {

		@Autowired
		private Config config;

		@Bean
		public ServletTemplateEngine servletTemplateEngine() {
			ServletTemplateEngine engine = new ServletTemplateEngine(config);
			List<String> layouts = new ArrayList<String>(1);
			layouts.add(DEFAULT_LAYOUT);
			engine.layoutStrategy_$eq(new DefaultLayoutStrategy(engine,
					JavaConversions.asScalaBuffer(layouts)));
			return engine;
		}
	}

	/**
	 * The scalate view resolver configuration, initializing the template path and
	 * extension.
	 * 
	 * @author cnagel
	 */
	@Configuration
	protected static class ScalateViewResolverConfiguration {

		@Autowired
		private ServletTemplateEngine servletTemplateEngine;

		@Bean
		public ScalateViewResolver scalateViewResolver() {
			ScalateViewResolver resolver = new ScalateViewResolver();
			resolver.templateEngine_$eq(servletTemplateEngine);
			resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 20);
			resolver.setPrefix(DEFAULT_PREFIX);
			resolver.setSuffix(DEFAULT_SUFFIX);
			return resolver;
		}

	}

}
