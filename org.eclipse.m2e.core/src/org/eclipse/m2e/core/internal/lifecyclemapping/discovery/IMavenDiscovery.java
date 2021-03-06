/*******************************************************************************
 * Copyright (c) 2008 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.m2e.core.internal.lifecyclemapping.discovery;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.project.MavenProject;


public interface IMavenDiscovery {

  /**
   * <p>
   * Calculates possibly empty list of discovery proposals. Multiple proposals per mapping configuration element
   * represent alternative possible changes.
   * </p>
   * <p>
   * To support incremental collection of user choices in the GUI, optional <code>preselected</code>
   * requirements/proposals map is used to eliminate new proposals that conflict with already selected choices. Result
   * is expected to include preselected proposals as-is. Implementation is expected to eliminate proposals that conflict
   * with already installed Eclipse bundles and preselected proposals.
   * </p>
   */
  public Map<ILifecycleMappingRequirement, List<IMavenDiscoveryProposal>> discover(MavenProject mavenProject,
      List<MojoExecution> mojoExecutions, List<IMavenDiscoveryProposal> preselected, IProgressMonitor monitor)
      throws CoreException;

}
