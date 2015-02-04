package simple.chat.server;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import javax.servlet.ServletRegistration;

public class SimpleChatServerApplication extends Application<SimpleChatServerConfiguration> {

	public static void main(String[] args) throws Exception {
		new SimpleChatServerApplication().run(args);
	}

	@Override
	public void run(SimpleChatServerConfiguration configuration, Environment environment) throws Exception {
		final ServletRegistration.Dynamic websocket = environment.servlets().addServlet("ws", new ChatServerServlet());
		websocket.setAsyncSupported(true);
		websocket.addMapping("/ws/*");

		final TemplateHealthCheck healthCheck = new TemplateHealthCheck();
		environment.healthChecks().register("template", healthCheck);
	}
}
