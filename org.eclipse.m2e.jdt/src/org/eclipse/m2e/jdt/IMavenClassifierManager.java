/*******************************************************************************
 * Copyright (c) 2012 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Red Hat, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.m2e.jdt;

import org.eclipse.m2e.core.project.IMavenProjectFacade;

/**
 * IMavenClassifierManager
 *
 * @author Fred Bricon
 */
public interface IMavenClassifierManager {

  IClassifierClasspathProvider getClassifierClasspathProvider(IMavenProjectFacade mavenProjectFacade, String classifier);

}
