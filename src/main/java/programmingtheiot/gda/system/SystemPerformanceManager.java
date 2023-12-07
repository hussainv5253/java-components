/**
 * This class is part of the Programming the Internet of Things project.
 * 
 * It is provided as a simple shell to guide the student and assist with
 * implementation for the Programming the Internet of Things exercises,
 * and designed to be modified by the student as needed.
 */ 

package programmingtheiot.gda.system;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;

import programmingtheiot.common.ConfigConst;
import programmingtheiot.common.ConfigUtil;
import programmingtheiot.common.IDataMessageListener;
import programmingtheiot.common.ResourceNameEnum;
import programmingtheiot.data.SystemPerformanceData;


/**
 * This class manages system performance data, including CPU utilization, memory utilization,
 * and disk utilization, and sends this data to a specified listener.
 */
public class SystemPerformanceManager
{
	// private var's
	private static final Logger _Logger = Logger.getLogger(SystemPerformanceManager.class.getName());
	private int pollRate = ConfigConst.DEFAULT_POLL_CYCLES;
	
	private ScheduledExecutorService schedExecSvc = null;
	private SystemCpuUtilTask sysCpuUtilTask = null;
	private SystemMemUtilTask sysMemUtilTask = null;
	private SystemDiskUtilTask sysDiskUtilTask = null;

	private Runnable taskRunner = null;
	private boolean isStarted = false;
	
	private String locationID = ConfigConst.NOT_SET;
	private IDataMessageListener dataMsgListener = null;
	
	// constructors
	
	/**
	 * Default constructor that initializes the SystemPerformanceManager with default values.
	 * It reads the poll rate and location ID from the configuration.
	 */
	public SystemPerformanceManager()
	{
		this.pollRate =  ConfigUtil.getInstance().getInteger(
				ConfigConst.GATEWAY_DEVICE, ConfigConst.POLL_CYCLES_KEY, ConfigConst.DEFAULT_POLL_CYCLES);
		
		if (this.pollRate <= 0) {
			this.pollRate = ConfigConst.DEFAULT_POLL_CYCLES;
		}
		
		this.locationID =
				ConfigUtil.getInstance().getProperty(
					ConfigConst.GATEWAY_DEVICE, ConfigConst.LOCATION_ID_PROP, ConfigConst.NOT_SET);
		
		this.schedExecSvc   = Executors.newScheduledThreadPool(1);
		this.sysCpuUtilTask = new SystemCpuUtilTask();
		this.sysMemUtilTask = new SystemMemUtilTask();
		this.sysDiskUtilTask = new SystemDiskUtilTask();
		
		// Define the Runnable task which is called by the scheduler
		this.taskRunner = () -> {
			this.handleTelemetry();
		};
	}
	
	
	// public methods
	
	/**
	 * Handles the collection of system performance telemetry data and sends it to the listener.
	 * The collected data includes CPU utilization, memory utilization, and disk utilization.
	 */
	public void handleTelemetry()
	{
		float cpuUtil = this.sysCpuUtilTask.getTelemetryValue();
		float memUtil = this.sysMemUtilTask.getTelemetryValue();
		float diskUtil = this.sysDiskUtilTask.getTelemetryValue();
		
		// TODO: change the log level to 'info' for testing purposes
		_Logger.info("CPU utilization: " + cpuUtil + ", Mem utilization: " + memUtil + ", Disk utilization: " + diskUtil);
		
		SystemPerformanceData spd = new SystemPerformanceData();
		spd.setLocationID(this.locationID);
		spd.setCpuUtilization(cpuUtil);
		spd.setMemoryUtilization(memUtil);
		spd.setDiskUtilization(diskUtil);
		
		if (this.dataMsgListener != null) {
			this.dataMsgListener.handleSystemPerformanceMessage(
				ResourceNameEnum.GDA_SYSTEM_PERF_MSG_RESOURCE, spd);
		}
	}
	
	/**
	 * Sets the data message listener to be used for sending system performance data.
	 * @param listener The data message listener to set.
	 */
	public void setDataMessageListener(IDataMessageListener listener)
	{
		if (listener != null) {
			this.dataMsgListener = listener;
		}
	}
	
	/**
	 * Starts the SystemPerformanceManager by scheduling tasks to collect and send system performance data.
	 * @return boolean True if the manager is started successfully, false otherwise.
	 */
	public boolean startManager()
	{
		if (! this.isStarted) {
			_Logger.info("SystemPerformanceManager is starting...");
			
			//Start the scheduler to run a task at a fixed rate every pollRate seconds
			ScheduledFuture<?> futureTask =
				this.schedExecSvc.scheduleAtFixedRate(this.taskRunner, 1L, this.pollRate, TimeUnit.SECONDS);
			
			this.isStarted = true;
			
		} else {
			_Logger.info("SystemPerformanceManager is already started.");
		}
		
		return this.isStarted;
	}

	/**
	 * Stops the SystemPerformanceManager by shutting down the scheduler.
	 * @return boolean True if the manager is stopped successfully, false otherwise.
	 */
	public boolean stopManager()
	{
		//Shut down scheduler and stop the manager
		this.schedExecSvc.shutdown();
		this.isStarted = false;
		
		_Logger.info("SystemPerformanceManager is stopped.");
		
		return true;
	}
}
