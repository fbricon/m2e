/*******************************************************************************
 * Copyright (c) 2008-2010 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Sonatype, Inc. - initial API and implementation
 *******************************************************************************/

package org.eclipse.m2e.core.ui.internal.actions;

import org.eclipse.jface.action.IAction;


/**
 * UpdateMavenProjectAction
 * 
 * @deprecated this action is deprecated in favor of {@link UpdateMavenProjectCommandHandler}
 */
@Deprecated
public class UpdateMavenProjectAction extends MavenProjectActionSupport {

  public static final String ID = "org.eclipse.m2e.updateConfigurationAction"; //$NON-NLS-1$

  public UpdateMavenProjectAction() {
  }

  public void run(IAction action) {
    UpdateMavenProjectCommandHandler.openUpdateProjectsDialog(getShell(), getProjects());
  }

}
