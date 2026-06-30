package es.jccm.edu.shared.configuration.context;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration extends AsyncConfigurerSupport {

	@Value("${async.configuration.corePoolSize}")
	private int corePoolSize;
	@Value("${async.configuration.maxPoolSize}")
	private int maxPoolSize;
	@Value("${async.configuration.queueCapacity}")
	private int queueCapacity;

	@Override
	@Bean(name = "getAsyncExecutor")
	public Executor getAsyncExecutor() {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("Api-Backend-EdcumamosCLM-Thread-");
		executor.initialize();
		return executor;
	}
}
