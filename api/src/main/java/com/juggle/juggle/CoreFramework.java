package com.juggle.juggle;


import com.juggle.juggle.framework.common.session.UserInterface;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.data.json.context.ExtViewContext;

public class CoreFramework {

	private CoreFramework() {
	}
	
	public static CoreFramework getInstance() {
		return new CoreFramework();
	}
	
	public void start() {
		this.cleanup();
	}

	public CoreFramework asUser(UserInterface user) {
		UserSession.setAuthorize(new UserSession.Authorize<>(user));
		return this;
	}

	public void run(Executor executor) {
		try {
			this.start();
			executor.run();
			this.stop();

		}
		catch (Exception ex) {
			this.rollback();
		}
	}
	
	public void stop() {
		this.cleanup();
	}

	public void rollback() {
		this.cleanup();
	}
	
	private void cleanup() {
		ExtViewContext.destroy();
	}

	interface Executor {
		void run();
	}
}
