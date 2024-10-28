package Simulation;

import IotDomain.Environment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class responsible for storing the state of the simulation in DingNet.
 * Only non-null values are included when an instance of this class is mapped to JSON.
 */
@Data
@Getter(onMethod_ = { @Synchronized })
@Setter(onMethod_ = { @Synchronized })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimulationState {

    /**
     * Constructs an empty {@code SimulationState} object. The private variables are
     * initialized using the {@code reset} function.
     * @since 1.0
     */
    public SimulationState() {
        this.reset();
    }

    /**
     * The environment of the simulation in DingNet.
     */
    @JsonIgnore
    private Environment environment;

    /**
     * A Boolean that keeps track of whether the simulation is running or not.
     */
    private Boolean isRunning;

    /**
     * A list of MoteStates.
     */
    private List<MoteState> moteStates;

    /**
     * A list of GatewayStates/
     */
    private List<GatewayState> gatewayStates;

    /**
     * Initializes all the private variables of SimulationState.
     */
    public void reset() {
        this.isRunning = false;
        this.moteStates = new ArrayList<>();
        this.gatewayStates = new ArrayList<>();
        this.environment = null;
    }
}
