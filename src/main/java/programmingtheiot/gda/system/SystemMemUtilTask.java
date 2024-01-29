/**
 * This class is part of the Programming the Internet of Things project.
 * 
 * It is provided as a simple shell to guide the student and assist with
 * implementation for the Programming the Internet of Things exercises,
 * and designed to be modified by the student as needed.
 */ 

package programmingtheiot.gda.system;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

import java.util.logging.Logger;

import programmingtheiot.common.ConfigConst;

/**
 * Represents a task to measure memory utilization.
 */
public class SystemMemUtilTask extends BaseSystemUtilTask
{
		
	// constructors
	
	/**
	 * Default constructor.
	 * Initializes SystemMemUtilTask with default type ID.
	 */
	public SystemMemUtilTask()
	{
		super(ConfigConst.NOT_SET, ConfigConst.DEFAULT_TYPE_ID);
	}
	
	
	// public methods

	/**
	 * Get the telemetry value representing memory utilization.
	 *
	 * @return The memory utilization as a floating point value.
	 */
	@Override
	public float getTelemetryValue()
	{
		//Implement custom getTelemetry function for Memory Utilization
		MemoryUsage memUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		double memUsed = (double) memUsage.getUsed();
		double memMax  = (double) memUsage.getMax();
		
		_Logger.fine("Mem used: " + memUsed + "; Mem Max: " + memMax);
		
		double memUtil = (memUsed / memMax) * 100.0d;
		
		return (float) memUtil;
	}
	
}
