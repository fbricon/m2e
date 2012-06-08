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
package org.eclipse.m2e.jdt.internal.launch;

import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;

import org.eclipse.m2e.core.project.IMavenProjectFacade;

/**
 * TestClassifierClasspathProvider
 *
 * @author Fred Bricon
 */
public class TestClassifierClasspathProvider extends MainClassifierClasspathProvider {

  @Override
  public boolean applies(IMavenProjectFacade mavenProjectFacade, String classifier) {
    return getClassifier().equals(classifier);
  }

  public String getClassifier() {
    return "test";
  }
  
  @Override
  @SuppressWarnings("unused")
  public void setMainClasspath(Set<IRuntimeClasspathEntry> resolved, IMavenProjectFacade mavenProjectFacade,
      IProgressMonitor monitor) throws CoreException {
    //Don't set the main classes
  }

  public String toString() {
    return "Test Classifier Classpath Provider";
  }

}
