/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.internal;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;
import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

/**
 * Create singleton services to make the Workbench singletons available. This is
 * a "hack" to provide access to the Workbench singletons.
 * 
 * @since 3.4
 */
public class WorkbenchSupportFactory extends AbstractServiceFactory {

	public Object create(Class serviceInterface, IServiceLocator parentLocator,
			IServiceLocator locator) {
		IWorkbench wb = (IWorkbench) locator.getService(IWorkbench.class);
		if (wb == null) {
			return null;
		}
		IWorkbenchPartSite site = (IWorkbenchPartSite) locator
				.getService(IWorkbenchPartSite.class);
		Object parent = parentLocator.getService(serviceInterface);
		if (parent != null) {
			if (IProgressService.class.equals(serviceInterface)) {
				if (site instanceof PartSite) {
					return ((PartSite) site).getSiteProgressService();
				}
			}
			return null;
		}
		if (IProgressService.class.equals(serviceInterface)) {
			return wb.getProgressService();
		}
		if (IWorkbenchSiteProgressService.class.equals(serviceInterface)) {
			if (site instanceof PartSite) {
				return ((PartSite) site).getSiteProgressService();
			}
		}
		return null;
	}
}