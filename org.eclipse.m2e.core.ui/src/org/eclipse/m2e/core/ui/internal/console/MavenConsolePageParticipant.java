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

package org.eclipse.m2e.core.ui.internal.console;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsolePageParticipant;
import org.eclipse.ui.part.IPageBookViewPage;

import org.eclipse.m2e.core.internal.preferences.MavenPreferenceConstants;
import org.eclipse.m2e.core.ui.internal.MavenImages;
import org.eclipse.m2e.core.ui.internal.Messages;
import org.eclipse.m2e.core.ui.internal.actions.MavenConsoleRemoveAction;
import org.eclipse.m2e.core.ui.internal.actions.MavenDebugOutputAction;


public class MavenConsolePageParticipant implements IConsolePageParticipant {

  private IAction consoleRemoveAction;

  private IAction debugAction;

  private IAction showOnErrorAction;

  private IAction showOnOutputAction;

  private static final String SHOW_ON_OUTPUT_LBL = Messages.MavenConsolePageParticipant_any;

  private static final String SHOW_ON_ERR_LBL = Messages.MavenConsolePageParticipant_error;

  public void init(IPageBookViewPage page, IConsole console) {
    this.consoleRemoveAction = new MavenConsoleRemoveAction();
    this.debugAction = new MavenDebugOutputAction();

    showOnOutputAction = new ShowOnOutputAction(console, SHOW_ON_OUTPUT_LBL);
    showOnErrorAction = new ShowOnErrorAction(console, SHOW_ON_ERR_LBL);

    IActionBars actionBars = page.getSite().getActionBars();
    configureToolBar(actionBars.getToolBarManager());
  }

  private void configureToolBar(IToolBarManager mgr) {
    mgr.appendToGroup(IConsoleConstants.LAUNCH_GROUP, consoleRemoveAction);
    mgr.prependToGroup(IConsoleConstants.OUTPUT_GROUP, debugAction);
    mgr.appendToGroup(IConsoleConstants.OUTPUT_GROUP, showOnOutputAction);
    mgr.appendToGroup(IConsoleConstants.OUTPUT_GROUP, showOnErrorAction);
  }

  public void dispose() {
    this.consoleRemoveAction = null;
    this.debugAction = null;
  }

  public void activated() {
  }

  public void deactivated() {
  }

  @SuppressWarnings("rawtypes")
  public Object getAdapter(Class adapter) {
    return null;
  }

  class ShowOnErrorAction extends MavenShowConsoleAction {
    public ShowOnErrorAction(IConsole console, String name) {
      super(name);
      setImageDescriptor(MavenImages.SHOW_CONSOLE_ERR);
    }

    /* (non-Javadoc)
     * @see org.eclipse.m2e.ui.internal.MavenShowConsoleAction#getKey()
     */
    protected String getKey() {
      return MavenPreferenceConstants.P_SHOW_CONSOLE_ON_ERR;
    }
  }

  class ShowOnOutputAction extends MavenShowConsoleAction {

    /**
     * @param console
     */
    public ShowOnOutputAction(IConsole console, String name) {
      super(name);
      setImageDescriptor(MavenImages.SHOW_CONSOLE_OUT);
    }

    /* (non-Javadoc)
     * @see org.eclipse.m2e.ui.internal.MavenShowConsoleAction#getKey()
     */
    protected String getKey() {
      return MavenPreferenceConstants.P_SHOW_CONSOLE_ON_OUTPUT;
    }

  }
}
