package simple.chat.server;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import simple.chat.server.EndpointHandler.EndpointHandlerBuilder;

public class ChatServerServlet extends WebSocketServlet {
	
	private static final long serialVersionUID = 1L;

	public ChatServerServlet() {
		EndpointHandlerBuilder.buildEndpointHandler();
	}

	@Override
	public void configure(WebSocketServletFactory factory) {
		factory.register(Endpoint.class);
	}

}
