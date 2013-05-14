/*******************************************************************************
 * Copyright (c) 2010 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Sonatype, Inc. - initial API and implementation
 *******************************************************************************/

package org.eclipse.m2e.core.internal.markers;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;

import org.apache.maven.project.MavenProject;

import org.eclipse.m2e.core.internal.MavenPluginActivator;


/**
 * MarkerUtils
 * 
 * @author mkleint
 */
public class MarkerUtils {
  private static Logger log = LoggerFactory.getLogger(MarkerUtils.class);

  public static void decorateMarker(IMarker marker) {
    BundleContext context = MavenPluginActivator.getDefault().getBundleContext();
    ServiceReference ref = context.getServiceReference(IMarkerLocationService.class.getName());
    if(ref == null) {
      log.warn("Could not find OSGI service for " + IMarkerLocationService.class.getName());
      return;
    }
    IMarkerLocationService service = (IMarkerLocationService) context.getService(ref);
    if(service != null) {
      try {
        service.findLocationForMarker(marker);
      } finally {
        context.ungetService(ref);
      }
    }
  }

  public static void addEditorHintMarkers(IMavenMarkerManager markerManager, IFile pom, MavenProject mavenProject,
      String type) {
    BundleContext context = MavenPluginActivator.getDefault().getBundleContext();
    ServiceReference ref = context.getServiceReference(IEditorMarkerService.class.getName());
    if(ref == null) {
      log.warn("Could not find OSGI service for " + IEditorMarkerService.class.getName());
      return;
    }
    IEditorMarkerService service = (IEditorMarkerService) context.getService(ref);
    if(service != null) {
      try {
        service.addEditorHintMarkers(markerManager, pom, mavenProject, type);
      } finally {
        context.ungetService(ref);
      }
    }
  }
}
