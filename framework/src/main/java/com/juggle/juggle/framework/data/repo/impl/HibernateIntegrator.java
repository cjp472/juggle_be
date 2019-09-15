package com.juggle.juggle.framework.data.repo.impl;

import java.util.Date;

import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.common.utils.UuidUtils;
import com.juggle.juggle.framework.data.entity.Audit;
import com.juggle.juggle.framework.data.entity.uuid.UuidEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class HibernateIntegrator implements Integrator {

	@Override
	public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		
		final EventListenerRegistry eventRegistry = serviceRegistry.getService(EventListenerRegistry.class);
		
		eventRegistry.appendListeners(EventType.PRE_UPDATE, new PreUpdateEventListener() {

			@Override
			public boolean onPreUpdate(PreUpdateEvent event) {
				if (event.getEntity() instanceof Audit) {
					Audit entity = (Audit)event.getEntity();
					Date now = new Date();
					entity.setUpdatedTime(now);
					entity.setUpdatedBy(UserSession.getUserInterface().getId());
				}
				
				return false;
			}
		});

		eventRegistry.appendListeners(EventType.PRE_INSERT, new PreInsertEventListener() {

			@Override
			public boolean onPreInsert(PreInsertEvent event) {
				if (event.getEntity() instanceof Audit) {
					Audit entity = (Audit)event.getEntity();
					Date now = new Date();
					entity.setCreatedTime(now);
					entity.setCreatedBy(UserSession.getUserInterface().getId());
					entity.setUpdatedTime(now);
					entity.setUpdatedBy(UserSession.getUserInterface().getId());
				}
				
				if (event.getEntity() instanceof UuidEntity) {
					UuidEntity entity = (UuidEntity) event.getEntity();
					if (StringUtils.isEmpty(entity.getUuid())) {

						entity.setUuid(UuidUtils.getUUID());
					}
				}
				return false;
			}
		});
	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
		// TODO Auto-generated method stub

	}
}
