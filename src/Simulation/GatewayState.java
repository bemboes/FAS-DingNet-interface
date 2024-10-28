package Simulation;

import lombok.Data;

/**
 * The Class responsible for storing the values of a gateway that is monitored.
 */
@Data
public class GatewayState {
    /**
     * The gateway identifier.
     */
    private Long EUI = null;

    /**
     * The x-coordinate of the gateway.
     */
    private Integer XPos = null;

    /**
     * The y-coordinate of the gateway.
     */
    private Integer YPos = null;

    /**
     * The spreading factor of the gateway.
     */
    private Integer SF = null;

    /**
     * The transmission power of the gateway.
     */
    private Integer transmissionPower = null;
}
