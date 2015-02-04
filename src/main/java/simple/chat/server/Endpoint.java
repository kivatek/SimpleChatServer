package simple.chat.server;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class Endpoint {
	private Session session_;
	
	public Session getSession() {
		return session_;
	}

	@OnWebSocketConnect
    public void onConnect(Session session) {
        this.session_ = session;
		EndpointHandler gw = EndpointHandler.getInstance();
		gw.register(this);
    }

	@OnWebSocketMessage
	public void onMessage(Session session, String message) throws IOException {
		EndpointHandler gw = EndpointHandler.getInstance();
		gw.broadcastMessage(message);
	}

	@OnWebSocketClose
    public void onClose(int statusCode, String reason) {
		EndpointHandler gw = EndpointHandler.getInstance();
		gw.removeByInstance(this);
    }
	
	@OnWebSocketError
	public void onError(Throwable cause) {
		EndpointHandler gw = EndpointHandler.getInstance();
		gw.removeByInstance(this);
		cause.printStackTrace(System.err);
	}
}
