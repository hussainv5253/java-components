/**
 * This class is part of the Programming the Internet of Things project.
 * 
 * It is provided as a simple shell to guide the student and assist with
 * implementation for the Programming the Internet of Things exercises,
 * and designed to be modified by the student as needed.
 */ 

package programmingtheiot.gda.system;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import java.util.logging.Logger;

import programmingtheiot.common.ConfigConst;


/**
 * Represents a task to measure CPU utilization.
 */
public class SystemCpuUtilTask extends BaseSystemUtilTask
{
	// constructors
	
	/**
	 * Default constructor.
	 * Initializes SystemCpuUtilTask with default type ID.
	 */
	public SystemCpuUtilTask()
	{
		super(ConfigConst.NOT_SET, ConfigConst.DEFAULT_TYPE_ID);
	}
	
	
	// public methods

	/**
	 * Get the telemetry value representing CPU utilization.
	 *
	 * @return The CPU utilization as a floating point value.
	 */
	@Override
	public float getTelemetryValue()
	{
		//Implement custom getTelemetry function for CPU Utilization
		OperatingSystemMXBean mxBean = ManagementFactory.getOperatingSystemMXBean();
		double cpuUtil = mxBean.getSystemLoadAverage();
		
		return (float) cpuUtil;
	}
}
