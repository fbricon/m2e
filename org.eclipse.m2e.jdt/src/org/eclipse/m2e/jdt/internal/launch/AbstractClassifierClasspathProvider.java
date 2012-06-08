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

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaRuntime;

import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.jdt.IClassifierClasspathProvider;

/**
 * AbstractClassifierClasspathProvider
 *
 * @author Fred Bricon
 */
public abstract class AbstractClassifierClasspathProvider  implements IClassifierClasspathProvider {

  public void setTestClasspath(Set<IRuntimeClasspathEntry> resolved, IMavenProjectFacade mavenProjectFacade, IProgressMonitor monitor) throws CoreException {
  }

  public void setMainClasspath(Set<IRuntimeClasspathEntry> resolved, IMavenProjectFacade mavenProjectFacade, IProgressMonitor monitor) throws CoreException {
  }

  protected void addFolders(Set<IRuntimeClasspathEntry> resolved, IProject project, Set<IPath> folders) {
    for(IPath folder : folders) {
      IResource member = project.findMember(folder); // only returns existing members
      if(member instanceof IFolder) { // must exist and be a folder
        resolved.add(JavaRuntime.newArchiveRuntimeClasspathEntry(member.getFullPath()));
      }
    }
  }

}
