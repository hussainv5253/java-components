/**
 * This class is part of the Programming the Internet of Things project.
 * 
 * It is provided as a simple shell to guide the student and assist with
 * implementation for the Programming the Internet of Things exercises,
 * and designed to be modified by the student as needed.
 */ 

package programmingtheiot.gda.app;

import java.util.logging.Level;
import java.util.logging.Logger;

import programmingtheiot.common.ConfigConst;
import programmingtheiot.common.ConfigUtil;
import programmingtheiot.common.IActuatorDataListener;
import programmingtheiot.common.IDataMessageListener;
import programmingtheiot.common.ResourceNameEnum;

import programmingtheiot.data.ActuatorData;
import programmingtheiot.data.DataUtil;
import programmingtheiot.data.SensorData;
import programmingtheiot.data.SystemPerformanceData;
import programmingtheiot.data.SystemStateData;

import programmingtheiot.gda.connection.CloudClientConnector;
import programmingtheiot.gda.connection.CoapServerGateway;
import programmingtheiot.gda.connection.IPersistenceClient;
import programmingtheiot.gda.connection.IPubSubClient;
import programmingtheiot.gda.connection.IRequestResponseClient;
import programmingtheiot.gda.connection.MqttClientConnector;
import programmingtheiot.gda.connection.RedisPersistenceAdapter;
import programmingtheiot.gda.connection.SmtpClientConnector;

import programmingtheiot.gda.system.SystemPerformanceManager;
/**
 * This class manages device data, including incoming messages, sensor data, actuator command responses,
 * and system performance data. It starts and stops various components based on configuration settings.
 */
public class DeviceDataManager implements IDataMessageListener
{
	// static
	
	private static final Logger _Logger =
		Logger.getLogger(DeviceDataManager.class.getName());
	
	// private var's
	
	private boolean enableMqttClient = true;
	private boolean enableCoapServer = false;
	private boolean enableCloudClient = false;
//	private boolean enableSmtpClient = false;
	private boolean enablePersistenceClient = false;
	private boolean enableSystemPerf = false;
	
	private IActuatorDataListener actuatorDataListener = null;
	private IPubSubClient mqttClient = null;
	private IPubSubClient cloudClient = null;
	private IPersistenceClient persistenceClient = null;
	private IRequestResponseClient smtpClient = null;
	private CoapServerGateway coapServer = null;
	private SystemPerformanceManager sysPerfMgr = null;
	
	// constructors
	/**
	 * Default constructor that reads configuration settings and initializes the manager.
	 */
	public DeviceDataManager()
	{
		super();
		
		ConfigUtil configUtil = ConfigUtil.getInstance();
		
		this.enableMqttClient =
			configUtil.getBoolean(
				ConfigConst.GATEWAY_DEVICE, ConfigConst.ENABLE_MQTT_CLIENT_KEY);
		
		this.enableCoapServer =
			configUtil.getBoolean(
				ConfigConst.GATEWAY_DEVICE, ConfigConst.ENABLE_COAP_SERVER_KEY);
		
		this.enableCloudClient =
			configUtil.getBoolean(
				ConfigConst.GATEWAY_DEVICE, ConfigConst.ENABLE_CLOUD_CLIENT_KEY);
		
		this.enablePersistenceClient =
			configUtil.getBoolean(
				ConfigConst.GATEWAY_DEVICE, ConfigConst.ENABLE_PERSISTENCE_CLIENT_KEY);
		
		initManager();
	}
	
	/**
	 * Constructor that allows setting the enablement of various components.
	 */
	public DeviceDataManager(
		boolean enableSystemPerf,
		boolean enableMqttClient,
		boolean enableCoapServer,
		boolean enableCloudClient,
//		boolean enableSmtpClient,
		boolean enablePersistenceClient)
	{
		super();
		this.enableSystemPerf = enableSystemPerf;
		this.enableMqttClient = enableMqttClient;
		this.enableCoapServer = enableCoapServer;
		this.enableCloudClient = enableCloudClient;
//		this.enableSmtpClient = enableSmtpClient;
		this.enablePersistenceClient = enablePersistenceClient;
		
		initManager();
	}
	
	
	// public methods
	/**
	 * Handles actuator command responses and triggers further analysis.
	 */
	@Override
	public boolean handleActuatorCommandResponse(ResourceNameEnum resourceName, ActuatorData data)
	{
		if (data != null) {
			_Logger.info("Handling actuator response: " + data.getName());
			
			this.handleIncomingDataAnalysis(resourceName, data);
			
			if (data.hasError()) {
				_Logger.warning("Error flag set for ActuatorData instance.");
			}
			
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Handles generic incoming messages (not further processed at the moment).
	 */
	@Override
	public boolean handleIncomingMessage(ResourceNameEnum resourceName, String msg)
	{
		if (msg != null) {
			_Logger.info("Handling incoming generic message: " + msg);
			
			// For now, just log the message without further processing
			
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Handles incoming sensor data, converts it to JSON, and triggers upstream transmission.
	 */
	@Override
	public boolean handleSensorMessage(ResourceNameEnum resourceName, SensorData data)
	{
		if (data != null) {
			_Logger.info("Handling sensor message: " + data.getName());
			
			
			if (data.hasError()) {
				_Logger.warning("Error flag set for SensorData instance.");
			}
			
			String sensorDataJson = DataUtil.getInstance().sensorDataToJson(data);
						
			handleUpstreamTransmission(resourceName, sensorDataJson, 1);
			
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Handles incoming system performance data, converts it to JSON, and triggers upstream transmission.
	 */
	@Override
	public boolean handleSystemPerformanceMessage(ResourceNameEnum resourceName, SystemPerformanceData data)
	{
		if (data != null) {
			_Logger.info("Handling system performance message: " + data.getName());
						
			if (data.hasError()) {
				_Logger.warning("Error flag set for SystemPerformanceData instance.");
			}
			
			String performanceDataJson = DataUtil.getInstance().systemPerformanceDataToJson(data);
									
			handleUpstreamTransmission(resourceName, performanceDataJson, 1);
			
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * Sets the actuator data listener (not fully implemented yet).
	 */
	public void setActuatorDataListener(String name, IActuatorDataListener listener)
	{
		// TODO: Implement this method
	}
	
	/**
	 * Starts the DeviceDataManager, initializing and starting various components.
	 */
	public void startManager()
	{
		_Logger.info("DeviceDataManager is starting...");
		
		try {
			if ( this.sysPerfMgr.startManager()) {
				_Logger.info("Device Data Manager started successfully.");
			} else {
				_Logger.warning("Failed to start system performance manager!");;
				
				this.stopManager();
			}
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to start Device Data Manager. Exiting.", e);
			this.stopManager();
		}

		if (this.enableMqttClient) {
			if (this.mqttClient != null && this.mqttClient instanceof MqttClientConnector) {
				boolean mqttConnectResult = ((MqttClientConnector) this.mqttClient).connectClient();
				_Logger.info("MQTT Client connection result: " + mqttConnectResult);
			}
		}

		if (this.enableCoapServer) {
			// Optional: Implement CoAP server start logic here
		}

		if (this.enableCloudClient) {
			// Optional: Implement Cloud client connection logic here
		}

		_Logger.info("DeviceDataManager started.");
	}
	
	/**
	 * Stops the DeviceDataManager, stopping various sub-components.
	 */	
	public void stopManager()
	{
		_Logger.info("DeviceDataManager is stopping...");

		if (this.enableMqttClient) {
			if (this.mqttClient != null && this.mqttClient instanceof MqttClientConnector) {
				boolean mqttDisconnectResult = ((MqttClientConnector) this.mqttClient).disconnectClient();
				_Logger.info("MQTT Client disconnection result: " + mqttDisconnectResult);
			}
		}

		if (this.enableCoapServer) {
			// Optional: Implement CoAP server stop logic here during relevant lab module
		}

		if (this.enableCloudClient) {
			// Optional: Implement Cloud client disconnection logic here during relevant lab module
		}

		if (this.sysPerfMgr != null) {
			try  {
				if (this.sysPerfMgr.stopManager()) {
					_Logger.info("Device Data Manager stopped successfully.");
				} else {
					_Logger.warning("Failed to stop the system performance manager!");
				}
			} catch (Exception e) {
				_Logger.log(Level.SEVERE, "Failed to cleanly stop Device Dat amanager. Exiting.", e);
			}
		}

		_Logger.info("DeviceDataManager stopped.");
	}
	
	// private methods
	
	/**
	 * Initializes the enabled connections. This will NOT start them, but only create the
	 * instances that will be used in the {@link #startManager() and #stopManager()) methods.
	 * 
	 */
	private void initManager()
	{
		ConfigUtil configUtil = ConfigUtil.getInstance();
		
		this.enableSystemPerf =
			configUtil.getBoolean(ConfigConst.GATEWAY_DEVICE,  ConfigConst.ENABLE_SYSTEM_PERF_KEY);
		
		if (this.enableSystemPerf) {
			this.sysPerfMgr = new SystemPerformanceManager();
			this.sysPerfMgr.setDataMessageListener(this);
		}
		
		if (this.enableMqttClient) {
			// TODO: implement this in Lab Module 7
		}
		
		if (this.enableCoapServer) {
			// TODO: implement this in Lab Module 8
		}
		
		if (this.enableCloudClient) {
			// TODO: implement this in Lab Module 10
		}
		
		if (this.enablePersistenceClient) {
			// TODO: implement this as an optional exercise in Lab Module 5
		}
	}
	
	// Private method for future implementation
	private void handleIncomingDataAnalysis(ResourceNameEnum resourceName, ActuatorData data)
	{
		_Logger.fine("Handling incoming data analysis for actuator: " + data.getName());
		// TODO: Implement logic for handling incoming actuator data analysis
	}

	// Private method for future implementation
	private void handleIncomingDataAnalysis(ResourceNameEnum resourceName, SystemStateData data)
	{
		_Logger.fine("Handling incoming data analysis for system state: " + data.getName());
		// TODO: Implement logic for handling incoming system state data analysis
	}

	// Private method for future implementation
	private boolean handleUpstreamTransmission(ResourceNameEnum resourceName, String jsonData, int qos)
	{
		_Logger.fine("Handling upstream transmission for resource: " + resourceName + ", QoS: " + qos);
		// TODO: Implement logic for handling upstream transmission (e.g., publish to the cloud service)
		return false;
	}
	
}
