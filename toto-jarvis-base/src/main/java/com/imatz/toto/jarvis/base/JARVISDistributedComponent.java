package com.imatz.toto.jarvis.base;

import com.imatz.toto.jarvis.base.alert.JARVISAEIExecutor;
import com.imatz.toto.jarvis.base.alert.JARVISAEIExecutorFactory;

/**
 * This interface defines the methods that every app that wants to be part of
 * JARVIS has to implement.
 * <p>
 * This interface is going to be autowired into JARVIS base utilities and should
 * thus be implemented in the app and provided as a Spring prototype bean.
 * </p>
 * 
 * @author C308961
 *
 */
public interface JARVISDistributedComponent {

	/**
	 * This method returns the code of the app that implements this interface
	 * (app that wants to be part of the JARVIS distributed behavior).
	 * 
	 * @return the app code (for instance "energy", or "housekeeping")
	 */
	public String getAppCode();

	/**
	 * This method returns the factory for the construction of the
	 * {@link JARVISAEIExecutor}, that can handle the specific AEI that has been
	 * received.
	 * 
	 * @return the factory that can instantiate {@link JARVISAEIExecutor}
	 */
	public JARVISAEIExecutorFactory getAEIExecutorFactory();

}
