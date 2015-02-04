package simple.chat.server;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EndpointHandler {
	public static class EndpointHandlerBuilder {
		public static void buildEndpointHandler() {
			instance_ = new EndpointHandler();
		}
	}

	private static EndpointHandler instance_;
	
	public static EndpointHandler getInstance() {
		return instance_;
	}
	
	private Map<String, Endpoint> endpoints_ = new ConcurrentHashMap<>();
	
	public void register(Endpoint endpoint) {
		endpoints_.put(String.valueOf(endpoint.hashCode()), endpoint);
	}

	public Endpoint remove(String sessionId) {
		return endpoints_.remove(sessionId);
	}

	public Endpoint removeByInstance(Endpoint endpoint) {
		return remove(String.valueOf(endpoint.hashCode()));
	}

	public Endpoint get(String sessionId) {
		return endpoints_.get(sessionId);
	}
	
	public void broadcastMessage(String message) {
		endpoints_.values().forEach(p -> {
			try {
				p.getSession().getRemote().sendString(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}
