/**
 * This class is part of the Programming the Internet of Things project.
 * 
 * It is provided as a simple shell to guide the student and assist with
 * implementation for the Programming the Internet of Things exercises,
 * and designed to be modified by the student as needed.
 */ 

package programmingtheiot.data;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.google.gson.Gson;

/**
 * This class, DataUtil, serves as a utility for converting data objects to and from JSON format. 
 * The class includes methods for converting ActuatorData, SensorData, SystemPerformanceData, 
 * and SystemStateData instances using the Google Gson library.
 */
public class DataUtil
{
	// static
	
	private static final DataUtil _Instance = new DataUtil();

	/**
	 * Returns the Singleton instance of this class.
	 * 
	 * @return ConfigUtil
	 */
	public static final DataUtil getInstance()
	{
		return _Instance;
	}
	
	
	// private var's
	
	
	// constructors
	
	/**
	 * Default (private).
	 * 
	 */
	private DataUtil()
	{
		super();
	}
	
	
	// public methods
		
	/**
	 * Converts ActuatorData to JSON format.
	 *
	 * @param actuatorData The ActuatorData instance to convert.
	 * @return String The JSON representation of the ActuatorData.
	 */
	public String actuatorDataToJson(ActuatorData actuatorData)
	{
		String jsonData = null;
		
		if (actuatorData != null) {
			Gson gson = new Gson();
			jsonData = gson.toJson(actuatorData);
		}
		
		return jsonData;
	}
	
	/**
	 * Converts JSON data to ActuatorData.
	 *
	 * @param jsonData The JSON representation of ActuatorData.
	 * @return ActuatorData The ActuatorData instance.
	 */
	public ActuatorData jsonToActuatorData(String jsonData)
	{
		ActuatorData data = null;
		
		if (jsonData != null && jsonData.trim().length() > 0) {
			Gson gson = new Gson();
			data = gson.fromJson(jsonData, ActuatorData.class);
		}
		
		return data;
	}
		
	/**
	 * Converts SensorData to JSON format.
	 *
	 * @param sensorData The SensorData instance to convert.
	 * @return String The JSON representation of the SensorData.
	 */
	public String sensorDataToJson(SensorData sensorData)
	{
		String jsonData = null;
		
		if (sensorData != null) {
			Gson gson = new Gson();
			jsonData = gson.toJson(sensorData);
		}
		
		return jsonData;
	}
	
	/**
	 * Converts JSON data to SensorData.
	 *
	 * @param jsonData The JSON representation of SensorData.
	 * @return SensorData The SensorData instance.
	 */
	public SensorData jsonToSensorData(String jsonData)
	{
		SensorData data = null;
		
		if (jsonData != null && jsonData.trim().length() > 0) {
			Gson gson = new Gson();
			data = gson.fromJson(jsonData, SensorData.class);
		}
		
		return data;
	}
			
	/**
	 * Converts SystemPerformanceData to JSON format.
	 *
	 * @param sysPerfData The SystemPerformanceData instance to convert.
	 * @return String The JSON representation of the SystemPerformanceData.
	 */
	public String systemPerformanceDataToJson(SystemPerformanceData sysPerfData)
	{
		String jsonData = null;
		
		if (sysPerfData != null) {
			Gson gson = new Gson();
			jsonData = gson.toJson(sysPerfData);
		}
		
		return jsonData;
	}
	
	/**
	 * Converts JSON data to SystemPerformanceData.
	 *
	 * @param jsonData The JSON representation of SystemPerformanceData.
	 * @return SystemPerformanceData The SystemPerformanceData instance.
	 */
	public SystemPerformanceData jsonToSystemPerformanceData(String jsonData)
	{
		SystemPerformanceData data = null;
		
		if (jsonData != null && jsonData.trim().length() > 0) {
			Gson gson = new Gson();
			data = gson.fromJson(jsonData, SystemPerformanceData.class);
		}
		
		return data;
	}
	
	/**
	 * Converts SystemStateData to JSON format.
	 *
	 * @param sysStateData The SystemStateData instance to convert.
	 * @return String The JSON representation of the SystemStateData.
	 */
	public String systemStateDataToJson(SystemStateData sysStateData)
	{
		String jsonData = null;
		
		if (sysStateData != null) {
			Gson gson = new Gson();
			jsonData = gson.toJson(sysStateData);
		}
		
		return jsonData;
	}
	
	/**
	 * Converts JSON data to SystemStateData.
	 *
	 * @param jsonData The JSON representation of SystemStateData.
	 * @return SystemStateData The SystemStateData instance.
	 */
	public SystemStateData jsonToSystemStateData(String jsonData)
	{
		SystemStateData data = null;
		
		if (jsonData != null && jsonData.trim().length() > 0) {
			Gson gson = new Gson();
			data = gson.fromJson(jsonData, SystemStateData.class);
		}
		
		return data;
	}		
}
