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

import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;

import org.eclipse.m2e.core.project.IMavenProjectFacade;

/**
 * IClassifierClasspathProvider
 *
 * @author Fred Bricon
 */
public interface IClassifierClasspathProvider {

  boolean applies(IMavenProjectFacade mavenProjectFacade, String classifier);

  String getClassifier();

  void setTestClasspath(Set<IRuntimeClasspathEntry> resolved, IMavenProjectFacade mavenProjectFacade, IProgressMonitor monitor) throws CoreException;
  
  void setMainClasspath(Set<IRuntimeClasspathEntry> resolved, IMavenProjectFacade mavenProjectFacade, IProgressMonitor monitor) throws CoreException;
  
  
}
