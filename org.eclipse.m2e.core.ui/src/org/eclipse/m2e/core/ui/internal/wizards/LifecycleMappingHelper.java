/*******************************************************************************
 * Copyright (c) 2013 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Red Hat, Inc. - initial API and implementation
 *******************************************************************************/

package org.eclipse.m2e.core.ui.internal.wizards;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.eclipse.m2e.core.internal.lifecyclemapping.discovery.ILifecycleMappingRequirement;
import org.eclipse.m2e.core.internal.lifecyclemapping.discovery.LifecycleMappingDiscoveryRequest;
import org.eclipse.m2e.core.internal.lifecyclemapping.discovery.MojoExecutionMappingConfiguration.MojoExecutionMappingRequirement;
import org.eclipse.m2e.core.internal.lifecyclemapping.discovery.MojoExecutionMappingConfiguration.ProjectConfiguratorMappingRequirement;
import org.eclipse.m2e.core.internal.lifecyclemapping.discovery.PackagingTypeMappingConfiguration.LifecycleStrategyMappingRequirement;
import org.eclipse.m2e.core.internal.markers.MarkerUtils;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.configurator.MojoExecutionKey;


/**
 * Helper to build {@link LifecycleMappingDiscoveryRequest}s out of {@link IProject}s. For each project, unsatisfied
 * {@link ILifecycleMappingRequirement}s are collected from LifecycleMapping error markers.
 * 
 * @since 1.5.0
 * @author Fred Bricon
 */
public class LifecycleMappingHelper {

  private LifecycleMappingHelper() {
    //Helper class
  }

  public static LifecycleMappingDiscoveryRequest createLifecycleMappingDiscoveryRequest(IProject project,
      IProgressMonitor monitor) throws CoreException {
    return createLifecycleMappingDiscoveryRequest(Collections.singleton(project), monitor);
  }

  public static LifecycleMappingDiscoveryRequest createLifecycleMappingDiscoveryRequest(Collection<IProject> projects,
      IProgressMonitor monitor) throws CoreException {
    LifecycleMappingDiscoveryRequest request = new LifecycleMappingDiscoveryRequest();
    if(projects != null) {
      for(IProject p : projects) {
        if(!p.isAccessible() || !p.hasNature(IMavenConstants.NATURE_ID)) {
          continue;
        }
        IMarker[] lifecycleMappingMarkers = getLifecycleMappingMarkers(p);
        if(lifecycleMappingMarkers != null && lifecycleMappingMarkers.length > 0) {
          IMavenProjectFacade facade = getFacade(p, monitor);
          if(facade != null) {
            for(IMarker m : lifecycleMappingMarkers) {
              ILifecycleMappingRequirement req = toLifecycleMappingRequirement(m, facade.getPackaging());
              if(req != null) {
                request.addProject(facade, req);
              }
            }
          }
        }
      }
    }
    return request;
  }

  private static ILifecycleMappingRequirement toLifecycleMappingRequirement(IMarker marker, String packagingType) {
    String type = marker.getAttribute(IMavenConstants.MARKER_ATTR_EDITOR_HINT, null);
    if(type == null) {
      return null;
    }
    ILifecycleMappingRequirement requirement = null;
    if(IMavenConstants.EDITOR_HINT_NOT_COVERED_MOJO_EXECUTION.equals(type)) {
      MojoExecutionKey mek = MarkerUtils.getMojoExecution(marker);
      if(mek != null) {
        requirement = new MojoExecutionMappingRequirement(mek, packagingType);
      }
    } else if(IMavenConstants.EDITOR_HINT_UNKNOWN_LIFECYCLE_ID.equals(type)) {
      String lifecycleId = getLifecycleId(marker);
      if(lifecycleId != null) {
        requirement = new LifecycleStrategyMappingRequirement(null, lifecycleId);
      }
    } else if(IMavenConstants.EDITOR_HINT_MISSING_CONFIGURATOR.equals(type)) {
      String configuratorId = getConfiguratorId(marker);
      if(configuratorId != null) {
        requirement = new ProjectConfiguratorMappingRequirement(null, configuratorId);
      }
    }
    return requirement;
  }

  private static IMarker[] getLifecycleMappingMarkers(IProject p) throws CoreException {
    IMarker[] markers = p.findMarkers(IMavenConstants.MARKER_LIFECYCLEMAPPING_ID, true, IResource.DEPTH_ONE);
    return markers;
  }

  private static IMavenProjectFacade getFacade(IProject project, IProgressMonitor monitor) {
    return MavenPlugin.getMavenProjectRegistry().create(project.getFile(IMavenConstants.POM_FILE_NAME), true, monitor);
  }

  private static String getLifecycleId(IMarker marker) {
    return marker.getAttribute(IMavenConstants.MARKER_ATTR_LIFECYCLE_PHASE, null);
  }

  private static String getConfiguratorId(IMarker marker) {
    return marker.getAttribute(IMavenConstants.MARKER_ATTR_CONFIGURATOR_ID, null);
  }
}
