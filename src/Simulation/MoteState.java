package Simulation;

import IotDomain.MoteSensor;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.jxmapviewer.viewer.GeoPosition;

import java.util.LinkedList;

/**
 * This class is a container class for the state of a mote in a DingNet run.
 * Only non-null values are included when an instance of this class is mapped to JSON.
 * @version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoteState {
    /**
     * The unique id of the mote used for the simulation.
     * @since 1.0
     */
    private Integer id = null;

    /**
     * The device's unique identifier.
     * @since 1.0
     */
    private Long EUI = null;

    /**
     * The x-coordinate of the mote.
     * @since 1.0
     */
    private Integer XPos = null;

    /**
     * The y-coordinate of the mote.
     * @since 1.0
     */
    private Integer YPos = null;

    /**
     * The spreading factor of the mote.
     * @since 1.0
     */
    private Integer SF = null;

    /**
     * The transmission power of the mote.
     * @since 1.0
     */
    private Integer transmissionPower = null;

    /**
     * A list of sensors on the mote.
     * @since 1.0
     */
    private LinkedList<MoteSensor> sensors = null;

    /**
     * The energy level of the mote.
     * @since 1.0
     */
    private Integer energyLevel = null;

    /**
     * The path the mote will follow as a list of {@link GeoPosition} objects.
     * @since 1.0
     */
    private LinkedList<GeoPosition> path = null;

    /**
     * The sampling rate of the mote.
     * @since 1.0
     */
    private Integer samplingRate = null;

    /**
     * The movement speed of the mote.
     * @since 1.0
     */
    private Double movementSpeed = null;

    /**
     * The offset the mote has at the start.
     * @since 1.0
     */
    private Integer startOffSet = null;

    /**
     * The distance to the nearest gateway.
     * @since 1.0
     */
    private Double shortestDistanceToGateway = null;

    /**
     * The highest received signal by any of the gateways.
     * @since 1.0
     */
    private Double highestReceivedSignal = null;
}
