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

import org.eclipse.core.resources.IMarker;


/**
 * IMarkerLocationService
 * 
 * @author mkleint
 */
public interface IMarkerLocationService {

  /**
   * sets the offset attribute on the marker if the marker is recognized and offset found
   * 
   * @param marker
   */
  void findLocationForMarker(IMarker marker);

}
