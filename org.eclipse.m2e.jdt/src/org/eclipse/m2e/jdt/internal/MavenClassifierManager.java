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
package org.eclipse.m2e.jdt.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.jdt.IClassifierClasspathProvider;
import org.eclipse.m2e.jdt.IMavenClassifierManager;
import org.eclipse.m2e.jdt.internal.launch.AbstractClassifierClasspathProvider;
import org.eclipse.m2e.jdt.internal.launch.EjbClientClassifierClasspathProvider;
import org.eclipse.m2e.jdt.internal.launch.MainClassifierClasspathProvider;
import org.eclipse.m2e.jdt.internal.launch.TestClassifierClasspathProvider;
import org.eclipse.m2e.jdt.internal.launch.WarClassesClassifierClasspathProvider;

/**
 * MavenClassifierManager
 *
 * @author Fred Bricon
 */
public class MavenClassifierManager implements IMavenClassifierManager {
  
  private Map<String, List<IClassifierClasspathProvider>> classifierClasspathProvidersMap;
  
  private static final IClassifierClasspathProvider NO_OP_CLASSIFIER_CLASSPATH_PROVIDER = new AbstractClassifierClasspathProvider() {
    
    public String getClassifier() {
      return "(__ignore_classifier__)";
    }
    
    public boolean applies(IMavenProjectFacade mavenProjectFacade, String classifier) {
      return false;
    }
    
    public String toString() {
      return "No-Op Classifier Classpath Provider";
    }
  }; 
  
  public IClassifierClasspathProvider getClassifierClasspathProvider(IMavenProjectFacade project, String classifier) {
    classifier = classifier == null?"":classifier;
    List<IClassifierClasspathProvider> allProviders = getClassifierClasspathProviders(classifier);
    List<IClassifierClasspathProvider> compatibleProviders = new ArrayList<IClassifierClasspathProvider>();
    
    if (allProviders != null) {
      for (IClassifierClasspathProvider p : allProviders) {
        if (p.applies(project, classifier)) {
          compatibleProviders.add(p);
        }
      }
    }
    
    switch(compatibleProviders.size()) {
      case 0:
        //nothing here
        break;
      case 1:
        return compatibleProviders.get(0);
        default:
          //TODO display/log error message
    }
    return NO_OP_CLASSIFIER_CLASSPATH_PROVIDER;
  }
  
  
  protected List<IClassifierClasspathProvider> getClassifierClasspathProviders(String classifier) {
    if (classifierClasspathProvidersMap == null) {
      classifierClasspathProvidersMap = readExtensions();
    }
    return classifierClasspathProvidersMap.get(classifier);
  }
  
  protected static synchronized Map<String, List<IClassifierClasspathProvider>> readExtensions() {
    Map<String, List<IClassifierClasspathProvider>> map = new HashMap<String, List<IClassifierClasspathProvider>>();
    
    IClassifierClasspathProvider mainProvider = new MainClassifierClasspathProvider();
    map.put(mainProvider.getClassifier(), Arrays.asList(mainProvider));

    IClassifierClasspathProvider testProvider = new TestClassifierClasspathProvider();
    map.put(testProvider.getClassifier(), Arrays.asList(testProvider));
    
    IClassifierClasspathProvider warClassesProvider = new WarClassesClassifierClasspathProvider();
    map.put(warClassesProvider.getClassifier(), Arrays.asList(warClassesProvider));
    
    IClassifierClasspathProvider ejbClientProvider = new EjbClientClassifierClasspathProvider();
    map.put(ejbClientProvider.getClassifier(), Arrays.asList(ejbClientProvider));
    
    return map;
  }

  
  
}
