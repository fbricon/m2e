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

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;

import org.apache.maven.model.Build;

import org.eclipse.m2e.core.project.IMavenProjectFacade;

/**
 * MainClassifierClasspathProvider
 *
 * @author Fred Bricon
 */
public class MainClassifierClasspathProvider extends AbstractClassifierClasspathProvider {

  public boolean applies(IMavenProjectFacade mavenProjectFacade, String classifier) {
    return (classifier == null || "".equals(classifier));
  }

  public String getClassifier() {
    return "";
  }

  @Override
  public void setMainClasspath(Set<IRuntimeClasspathEntry> resolved, IMavenProjectFacade mavenProjectFacade, IProgressMonitor monitor) throws CoreException {
    Build build = mavenProjectFacade.getMavenProject(monitor).getBuild();
    final Set<IPath> allClasses = new LinkedHashSet<IPath>();
    allClasses.add(mavenProjectFacade.getProjectRelativePath(build.getOutputDirectory()));
    addFolders(resolved, mavenProjectFacade.getProject(), allClasses);
  }
  
  @Override
  public void setTestClasspath(Set<IRuntimeClasspathEntry> resolved, IMavenProjectFacade mavenProjectFacade, IProgressMonitor monitor) throws CoreException {
    Build build = mavenProjectFacade.getMavenProject(monitor).getBuild();
    final Set<IPath> allTestClasses = new LinkedHashSet<IPath>();
    allTestClasses.add(mavenProjectFacade.getProjectRelativePath(build.getTestOutputDirectory()));
    addFolders(resolved, mavenProjectFacade.getProject(), allTestClasses);
  }
  
  public String toString() {
    return "Main Classifier Classpath Provider";
  }
}
