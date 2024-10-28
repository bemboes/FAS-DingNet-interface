package HTTP;

import Simulation.SimulationState;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * This class implements the handler for an HTTP request for the JSON schema of the response to the monitor endpoint.
 * @version 1.0
 */
public class MonitorSchemaHandler implements HttpHandler {
    /**
     * The current state of the simulation to be monitored.
     * @since 1.0
     */
    private final SimulationState state;

    /**
     * Constructs a {@code MonitorSchemaHandler} object with the simulation state {@code state}.
     * @param state The state of the simulation to be monitored.
     * @since 1.0
     */
    public MonitorSchemaHandler(SimulationState state) {
        this.state = state;
    }

    /**
     * Sends a response with the HTTP Status Code {@code 200 OK} and the JSON schema of the monitor endpoint
     * to the HTTP {@code exchange}. Overrides the method {@link HttpHandler#handle(HttpExchange)}.
     * @param exchange The HTTP exchange with the monitor schema endpoint.
     * @exception IOException can occur in {@link HTTPResponse#send(HttpExchange)} )
     * @since 1.0
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        HTTPResponse response = new HTTPResponse(HttpURLConnection.HTTP_OK, Schema.toJsonSchema(state.getClass()));

        exchange.getResponseHeaders().add("Content-Type", "application/json");

        response.send(exchange);
    }
}
