package Simulation;

import GUI.MapViewer.*;
import IotDomain.*;
import SelfAdaptation.Instrumentation.MoteProbe;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import java.util.*;

/**
 * This class provides the data and functionality of the main simulation.
 * @version 1.0
 */
public class MainSimulation extends Thread {

    /**
     * The current state of the simulation.
     * @since 1.0
     */
    SimulationState simulationState;

    /**
     * Constructs a {@code MainSimulation} object with the simulation state {@code state}.
     * @param state The simulation state of the {@code MainSimulation} object.
     * @since 1.0
     */
    public MainSimulation(SimulationState state) {
        this.simulationState = state;
    }

    /**
     * Starts a DingNet simulation with three motes and four gateways. Two motes are moved along different paths.
     * The simulation state is updated regularly.
     * @exception InterruptedException can occur in {@link Thread#sleep(long)}
     * @since 1.0
     */
    public void runSimulation() throws InterruptedException {
        /*
        Set to enable or disable adaptation of node 0 (D1).
         */
        Boolean adaption = true;

        /*
        generate all the points
         */
        GeoPosition mapzero = new GeoPosition(50.853718, 4.673155);
        Integer mapsize = (int) Math.ceil(1000 *Math.max(Environment.distance(50.853718, 4.673155, 50.878697,   4.673155), Environment.distance(50.853718, 4.673155, 50.853718,   4.701200)));
        GeoPosition leuven = new GeoPosition(50,51,46,4,41,2);
        GeoPosition gw1 = new GeoPosition(50.859722, 4.681944);
        GeoPosition gw2 = new GeoPosition(50.863780, 4.677992);
        GeoPosition gw3 = new GeoPosition(50.867222, 4.678056);
        GeoPosition gw4 = new GeoPosition(50.856667, 4.676389);

        GeoPosition mp1 = new GeoPosition(50.8605, 4.6795);

        GeoPosition wp1 = new GeoPosition(50.856020, 4.675844);
        GeoPosition wp2 = new GeoPosition(50.856545, 4.676743);
        GeoPosition wp3 = new GeoPosition(50.857852, 4.679702);
        GeoPosition wp4 = new GeoPosition(50.860061, 4.683473);
        GeoPosition wp5 = new GeoPosition(50.861985, 4.680993);
        GeoPosition wp6 = new GeoPosition(50.862263, 4.680672);
        GeoPosition wp7 = new GeoPosition(50.862696, 4.680416);
        GeoPosition wp8 = new GeoPosition(50.863049, 4.680321);
        GeoPosition wp9 = new GeoPosition(50.863455, 4.680385);
        GeoPosition wp10 = new GeoPosition(50.863977, 4.680610);
        GeoPosition wp11 = new GeoPosition(50.864770, 4.680898);
        GeoPosition wp12 = new GeoPosition(50.865176, 4.680973);
        GeoPosition wp13 = new GeoPosition(50.865583, 4.680976);
        GeoPosition wp14 = new GeoPosition(50.867980, 4.680381);
        GeoPosition wp15 = new GeoPosition(50.867881, 4.678226);
        GeoPosition wp16 = new GeoPosition(50.868028, 4.678175);
        GeoPosition wp17 = new GeoPosition(50.869650, 4.676740);

        GeoPosition wp21 = new GeoPosition(50.868551, 4.698337);
        GeoPosition wp22 = new GeoPosition(50.866713, 4.695153);
        GeoPosition wp23 = new GeoPosition(50.861330, 4.685687);
        GeoPosition wp24 = new GeoPosition(50.857910, 4.679724);
        GeoPosition wp25 = new GeoPosition(50.856486, 4.676650);

        GeoPosition positionMote2 = new GeoPosition(50.862752, 4.688886);
        /*
         * Create tracks.
         */
        List<GeoPosition> track1 = Arrays.asList(wp1,wp2,wp3,wp4,wp5,wp6,wp7,wp8,wp9,wp10,wp11,wp12,wp13,wp14,wp15,wp16,wp17);
        List<GeoPosition> track2 = Arrays.asList(wp21,wp22,wp23,wp24,wp25,wp1);

        Set<Waypoint> gateWays = new HashSet<>(Arrays.asList(
                new DefaultWaypoint(gw1),
                new DefaultWaypoint(gw2),
                new DefaultWaypoint(gw3),
                new DefaultWaypoint(gw4)));

        Set<Waypoint> mps = new HashSet<>(Arrays.asList(
                new DefaultWaypoint(mp1)));

        GatewayWaypointPainter<Waypoint> gateWayPainter =new GatewayWaypointPainter<>();
        gateWayPainter.setWaypoints(gateWays);

        MoteWaypointPainter<Waypoint> moteWaypointPainter = new MoteWaypointPainter<>();
        moteWaypointPainter.setWaypoints(new HashSet<Waypoint>(Arrays.asList(new DefaultWaypoint(wp5), new DefaultWaypoint(positionMote2))));

        AWaypointPainter<Waypoint> aWaypointPainter = new AWaypointPainter<>();
        aWaypointPainter.setWaypoints(new HashSet<Waypoint>(Arrays.asList(new DefaultWaypoint(wp1))));

        BWaypointPainter<Waypoint> bWaypointPainter = new BWaypointPainter<>();
        bWaypointPainter.setWaypoints(new HashSet<Waypoint>(Arrays.asList(new DefaultWaypoint(wp17))));

        CWaypointPainter<Waypoint> cWaypointPainter = new CWaypointPainter<>();
        cWaypointPainter.setWaypoints(new HashSet<Waypoint>(Arrays.asList(new DefaultWaypoint(wp21))));

        WaypointPainter<Waypoint> MPPainter =new WaypointPainter<>();
        MPPainter.setWaypoints(mps);

        /*
         Prepare simulation environment.
         */
        Characteristic[][] map = new Characteristic[mapsize][mapsize];
        for(int i =0; i < mapsize; i++){
            for(int j =0; j < mapsize /3 ; j++){
                map[j][i] = Characteristic.Forest;
            }
            for(int j =(int) Math.floor(mapsize /3); j < 2*mapsize /3 ; j++){
                map[j][i] = Characteristic.Plain;
            }

            for(int j =(int) Math.floor(2*mapsize /3); j < mapsize ; j++){
                map[j][i] = Characteristic.City;
            }
        }

        Environment environment = new Environment(map,mapzero,new LinkedHashSet<>());
        this.simulationState.setEnvironment(environment);
        /*
        Add motes and gateways.
         */
        Random random = new Random();
        new Gateway(random.nextLong(),(int)Math.round(1000* Environment.distance(mapzero.getLatitude(),mapzero.getLongitude(),mapzero.getLatitude(), gw1.getLongitude())),
                (int)Math.round(1000* Environment.distance(mapzero.getLatitude(),mapzero.getLongitude(),gw1.getLatitude(), mapzero.getLongitude())),
                environment, 14,12);
        new Gateway(random.nextLong(),(int)Math.round(1000* Environment.distance(mapzero.getLatitude(),mapzero.getLongitude(),mapzero.getLatitude(), gw2.getLongitude())),
                (int)Math.round(1000* Environment.distance(mapzero.getLatitude(),mapzero.getLongitude(),gw2.getLatitude(), mapzero.getLongitude())),
                environment, 14,12);
        new Gateway(random.nextLong(),(int)Math.round(1000* Environment.distance(mapzero.getLatitude(),mapzero.getLongitude(),mapzero.getLatitude(), gw3.getLongitude())),
                (int)Math.round(1000* Environment.distance(mapzero.getLatitude(),mapzero.getLongitude(),gw3.getLatitude(), mapzero.getLongitude())),
                environment, 14,12);
        new Gateway(random.nextLong(),(int)Math.round(1000* Environment.distance(mapzero.getLatitude(),mapzero.getLongitude(),mapzero.getLatitude(), gw4.getLongitude())),
                (int)Math.round(1000* Environment.distance(mapzero.getLatitude(),mapzero.getLongitude(),gw4.getLatitude(), mapzero.getLongitude())),
                environment, 14,12);
        new Mote(random.nextLong(),(int)Math.round(1000* Environment.distance(mapzero.getLatitude(),mapzero.getLongitude(),mapzero.getLatitude(), wp1.getLongitude())),
                (int)Math.round(1000* Environment.distance(mapzero.getLatitude(),mapzero.getLongitude(),wp1.getLatitude(), mapzero.getLongitude())),
                environment, 14,12, new LinkedList<>(),0,new LinkedList<>(),10,0.5);

        new Mote(random.nextLong(),(int)Math.round(1000* Environment.distance(mapzero.getLatitude(),mapzero.getLongitude(),mapzero.getLatitude(), positionMote2.getLongitude())),
                (int)Math.round(1000* Environment.distance(mapzero.getLatitude(),mapzero.getLongitude(),positionMote2.getLatitude(), mapzero.getLongitude())),
                environment, 14,12, new LinkedList<>(),0,new LinkedList<>(),10,0.5);
        new Mote(random.nextLong(),toMapXCoordinate(wp21,mapzero),
                toMapYCoordinate(wp21,mapzero),
                environment, 0,12, new LinkedList<>(),0,new LinkedList<>(),10,0.5);
        /*
         Get the motes.
         */
        Mote mote0 = environment.getMotes().get(0);
        Mote mote1 = environment.getMotes().get(1);
        Mote mote2 = environment.getMotes().get(2);

        LinkedList<Mote> motes = new LinkedList<>();
        motes.add(mote0);
        motes.add(mote1);
        motes.add(mote2);

        // Initialize the mote states and add them to the simulation state
        for (int i = 0; i < environment.getMotes().size(); i++)
        {
            MoteState motestate = new MoteState();
            motestate.setId(i);
            this.simulationState.getMoteStates().add(motestate);
        }

        MoteProbe moteProbe = new MoteProbe();

        // Initialize the gateway states and add them to the simulation state
        for (int i = 0; i < environment.getGateways().size(); i++) {
            GatewayState state = new GatewayState();
            state.setEUI(environment.getGateways().get(i).getEUI());
            state.setXPos(environment.getGateways().get(i).getXPos());
            state.setYPos(environment.getGateways().get(i).getXPos());
            state.setSF(environment.getGateways().get(i).getSF());
            state.setTransmissionPower(environment.getGateways().get(i).getTransmissionPower());

            simulationState.getGatewayStates().add(state);
        }

        /*
         Actual simulation
         */
        LinkedList<Integer> powerSetting1 = new LinkedList<>();
        LinkedList<LoraTransmission> HighestPower1 = new LinkedList<>();
        Integer mote1counter = 9;
        Integer mote2counter = random.nextInt(15)+1;
        LinkedList<Integer> indexesMote2 = new LinkedList<>();
        indexesMote2.add(mote2counter);
        Integer trackposition1 = track1.size();
        Integer trackposition2 = track2.size();

        while(  Integer.signum(mote0.getXPos() - toMapXCoordinate(track1.get(track1.size()-1),mapzero ))!= 0 ||
                Integer.signum(mote0.getYPos() - toMapYCoordinate(track1.get(track1.size()-1),mapzero ))!= 0 ||
                Integer.signum(mote2.getXPos() - toMapXCoordinate(track2.get(track2.size()-1),mapzero ))!= 0 ||
                Integer.signum(mote2.getYPos() - toMapYCoordinate(track2.get(track2.size()-1),mapzero ))!= 0
        ){
            for (int i=0; i < environment.getMotes().size(); i++)
            {
                // Shortest distance of the mote to a gateway
                try {
                    simulationState.getMoteStates().get(i).setShortestDistanceToGateway(
                            moteProbe.getShortestDistanceToGateway(environment.getMotes().get(i)));
                }
                catch (NoSuchElementException e) {
                    // Nothing to be done
                }

                // Highest received signal
                try {
                    simulationState.getMoteStates().get(i).setHighestReceivedSignal(
                            moteProbe.getHighestReceivedSignal(environment.getMotes().get(i)));
                }
                catch (NoSuchElementException e) {
                    // Nothing to be done
                }

            }

            // Update the position of mote0
            if(moveMote(track1.get(track1.size()-trackposition1),mote0,mapzero)){
                if(mote1counter == 0) {
                    mote0.sendToGateWay(new Byte[0], new HashMap<>());
                    if(adaption){
                        powerSetting1.add(mote0.getTransmissionPower());
                        HighestPower1.add(naiveAdaptionAlgorithm(mote0));
                    }
                    mote1counter = 9;
                }
                else
                    mote1counter --;

            }
            else if(trackposition1 > 1){
                trackposition1 --;
            }

            // Update the position of mote2
            if(moveMote(track2.get(track2.size()-trackposition2),mote2,mapzero)){
                if(mote2counter == 0) {
                    mote2.sendToGateWay(new Byte[0], new HashMap<>());
                    mote2counter = random.nextInt(15)+1;
                    indexesMote2.add(indexesMote2.getLast() + mote2counter);
                }
                else
                    mote2counter --;
            }
            else if(trackposition2 > 1){
                trackposition2 --;
            }

            // Update the simulation state
            for (int i=0; i < environment.getMotes().size(); i++) {
                simulationState.getMoteStates().get(i).setEUI(motes.get(i).getEUI());
                simulationState.getMoteStates().get(i).setPath(motes.get(i).getPath());
                simulationState.getMoteStates().get(i).setXPos(motes.get(i).getXPos());
                simulationState.getMoteStates().get(i).setYPos(motes.get(i).getYPos());
                simulationState.getMoteStates().get(i).setSF(motes.get(i).getSF());
                simulationState.getMoteStates().get(i).setTransmissionPower(motes.get(i).getTransmissionPower());
                simulationState.getMoteStates().get(i).setSensors(motes.get(i).getSensors());
                simulationState.getMoteStates().get(i).setEnergyLevel(motes.get(i).getEnergyLevel());
                simulationState.getMoteStates().get(i).setPath(motes.get(i).getPath());
                simulationState.getMoteStates().get(i).setSamplingRate(motes.get(i).getSamplingRate());
                simulationState.getMoteStates().get(i).setMovementSpeed(motes.get(i).getMovementSpeed());
                simulationState.getMoteStates().get(i).setStartOffSet(motes.get(i).getStartOffset());
            }

            environment.tick(1500);
            Thread.sleep(1);
        }

       /*
       Data collection mote 1
        */
        LinkedList<LinkedList<LoraTransmission>> transmissionsMote0 = new LinkedList<>();
        Integer transmittedPacketsMote0 = 0;
        Integer lostPacketsMote0 = 0;
        for(Gateway gateway:environment.getGateways()){
            transmissionsMote0.add(new LinkedList<>());
            for(LoraTransmission transmission :gateway.getAllReceivedTransmissions(gateway.getEnvironment().getNumberOfRuns()-1).keySet()){
                if(transmission.getSender() == mote0) {
                    transmittedPacketsMote0++;
                    if (!gateway.getAllReceivedTransmissions(gateway.getEnvironment().getNumberOfRuns()-1).get(transmission))
                        transmissionsMote0.getLast().add(transmission);
                    else {
                        transmissionsMote0.getLast().add(new LoraTransmission(transmission.getSender(),
                                transmission.getReceiver(), -10, transmission.getBandwidth(),
                                transmission.getSpreadingFactor(), transmission.getContent()));
                        lostPacketsMote0++;
                    }
                }
            }
        }

        /*
       Data collection mote 2
        */
        LinkedList<LinkedList<LoraTransmission>> transmissionsMote2 = new LinkedList<>();

        Integer transmittedPacketsMote2 = 0;
        Integer lostPacketsMote2 = 0;
        for(Gateway gateway:environment.getGateways()){
            transmissionsMote2.add(new LinkedList<>());
            for(LoraTransmission transmission :gateway.getAllReceivedTransmissions(gateway.getEnvironment().getNumberOfRuns()-1).keySet()){
                if(transmission.getSender() == mote2) {
                    transmittedPacketsMote2 ++;
                    if (!gateway.getAllReceivedTransmissions(gateway.getEnvironment().getNumberOfRuns()-1).get(transmission))
                        transmissionsMote2.getLast().add(transmission);
                    else {
                        lostPacketsMote2 ++;
                        transmissionsMote2.getLast().add(new LoraTransmission(transmission.getSender(),
                                transmission.getReceiver(), -10, transmission.getBandwidth(),
                                transmission.getSpreadingFactor(), transmission.getContent()));
                    }
                }
            }
        }

        System.out.println("Sent Packets: " +transmittedPacketsMote0);
        System.out.println("Lost Packets: " +lostPacketsMote0);
        System.out.println("Sent Packets: " +transmittedPacketsMote2);
        System.out.println("Lost Packets: " +lostPacketsMote2);
    }

    /**
     * Runs the simulation and sets the {@code isRunning} flag of the simulation state to {@code false} again.
     * @exception RuntimeException can occur in {@link MainSimulation#runSimulation()}
     * @since 1.0
     */
    public void run() {
        try {
            this.runSimulation();
            this.simulationState.setIsRunning(false);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /*
    The naïve adaptation for our paper
     */
    private static LinkedList<Double> algorithmBuffer = new LinkedList<>();
    private static LoraTransmission naiveAdaptionAlgorithm(Mote mote){
        LinkedList<LoraTransmission> lastTransmissions = new LinkedList<>();
        for(Gateway gateway :mote.getEnvironment().getGateways()){
            Boolean placed = false;
            for(int i = gateway.getReceivedTransmissions(gateway.getEnvironment().getNumberOfRuns()-1).size()-1; i>=0 && !placed; i--) {
                if(gateway.getReceivedTransmissions(gateway.getEnvironment().getNumberOfRuns()-1).get(i).getSender() == mote) {
                    lastTransmissions.add(gateway.getReceivedTransmissions(gateway.getEnvironment().getNumberOfRuns()-1).getLast());
                    placed = true;
                }
            }
        }
        LoraTransmission bestTransmission = lastTransmissions.getFirst();
        for (LoraTransmission transmission : lastTransmissions){
            if(transmission.getTransmissionPower() > bestTransmission.getTransmissionPower())
                bestTransmission = transmission;
        }
        algorithmBuffer.add(bestTransmission.getTransmissionPower());
        if(algorithmBuffer.size() ==5){
            double average = 0;
            for (Double power : algorithmBuffer){
                average+= power;
            }
            average = average /5;
            if(average > -42) {
                if (mote.getTransmissionPower() > -3)
                    mote.setTransmissionPower(mote.getTransmissionPower() - 1);
            }
            if(average < -48){
                if(mote.getTransmissionPower() < 14)
                    mote.setTransmissionPower(mote.getTransmissionPower() +1);
            }
            algorithmBuffer = new LinkedList<>();
        }
        return bestTransmission;
    }
    /*
    A more advanced yet unfinished adaptation algorithm
     */
    private static void adaptationAlgorithmRobbe(LinkedList<LoraTransmission> packets){
        if(packets.get(0).getSender().getClass() != Mote.class)
            return;
        Mote endNode = (Mote) packets.get(0).getSender();
        Integer POW = endNode.getTransmissionPower();
        Integer SF = endNode.getSF();
        double DesiredPER = 0;
        Gateway bestGateway;
        double bestSNREstimate = Double.MIN_VALUE;
        double PEREstimated = 1;
        Gateway gateway;
        for(LoraTransmission transmission : packets){
            gateway = (Gateway) transmission.getReceiver();
            transmission.getTransmissionPower();
            double SNREstimate = 0;
            double PERAtGateway = estimatePER(SNREstimate,SF);
            PEREstimated = PEREstimated*PERAtGateway;
            if(SNREstimate>bestSNREstimate){
                bestSNREstimate = SNREstimate;
                bestGateway = gateway;
            }
        }
        if(PEREstimated>DesiredPER){
            if (POW<14)
                endNode.setTransmissionPower(endNode.getTransmissionPower() + 1);
            else if(SF < 12)
                endNode.setSF(endNode.getSF() + 1);
        }
        else{
            if(SF > 1 && estimatePER(bestSNREstimate-SNRDropSFChange(SF),SF)<DesiredPER)
                endNode.setSF(endNode.getSF() - 1);
            else if(POW > -3 && estimatePER(bestSNREstimate-SNRDropPOWChange(POW),SF)<DesiredPER)
                endNode.setTransmissionPower(endNode.getTransmissionPower() - 1);
        }
    }

    private static double estimatePER(double SNR, Integer SF){
        return 0;
    }

    private static double SNRDropSFChange(Integer SF){
        return 0;
    }
    private static double SNRDropPOWChange(Integer POW){
        return 0;
    }

    /**
     * A function that moves a mote to a geoposition 1 step ans returns if the note has moved.
     * @param position
     * @param mote
     * @param mapzero
     * @return If the node has moved
     */
    private static Boolean moveMote(GeoPosition position, Mote mote, GeoPosition mapzero){
        Integer xPos = toMapXCoordinate(position,mapzero);
        Integer yPos = toMapYCoordinate(position,mapzero);
        if(Integer.signum(xPos - mote.getXPos()) != 0 || Integer.signum(yPos - mote.getYPos()) != 0){
            if(Math.abs(mote.getXPos() - xPos) >= Math.abs(mote.getYPos() - yPos)){
                mote.setXPos(mote.getXPos()+ Integer.signum(xPos - mote.getXPos()));

            }
            else{
                mote.setYPos(mote.getYPos()+ Integer.signum(yPos - mote.getYPos()));
            }
            return true;
        }
        return false;
    }

    private static Integer toMapXCoordinate(GeoPosition geoPosition, GeoPosition mapzero){
        return (int)Math.round(1000* Environment.distance(mapzero.getLatitude(),mapzero.getLongitude(),mapzero.getLatitude(), geoPosition.getLongitude()));
    }

    private static Integer toMapYCoordinate(GeoPosition geoPosition, GeoPosition mapzero){
        return (int)Math.round(1000* Environment.distance(mapzero.getLatitude(),mapzero.getLongitude(),geoPosition.getLatitude(), mapzero.getLongitude()));
    }

    public static void main(String[] args) throws InterruptedException {
        MainSimulation simulation = new MainSimulation(new SimulationState());

        simulation.runSimulation();
    }
}
