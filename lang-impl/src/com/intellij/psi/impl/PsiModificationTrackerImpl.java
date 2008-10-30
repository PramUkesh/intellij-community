/*
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: Jul 18, 2002
 * Time: 5:57:57 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.intellij.psi.impl;

import com.intellij.ProjectTopics;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.util.PsiModificationTracker;

public class PsiModificationTrackerImpl implements PsiModificationTracker, PsiTreeChangePreprocessor {
  private volatile long myModificationCount = 0;
  private volatile long myOutOfCodeBlockModificationCount = 0;
  private volatile long myJavaStructureModificationCount = 0;
  private final Listener myPublisher;

  public PsiModificationTrackerImpl(Project project) {
    myPublisher = project.getMessageBus().syncPublisher(ProjectTopics.MODIFICATION_TRACKER);
  }

  public void incCounter(){
    myModificationCount++;
    myJavaStructureModificationCount++;
    incOutOfCodeBlockModificationCounter();
  }

  public void incOutOfCodeBlockModificationCounter() {
    myOutOfCodeBlockModificationCount++;
    myPublisher.modificationCountChanged();
  }

  public void treeChanged(PsiTreeChangeEventImpl event) {
    myModificationCount++;
    if (event.getParent() instanceof PsiDirectory) {
      incOutOfCodeBlockModificationCounter();
    }

    myPublisher.modificationCountChanged();
  }

  public long getModificationCount() {
    return myModificationCount;
  }

  public long getOutOfCodeBlockModificationCount() {
    return myOutOfCodeBlockModificationCount;
  }

  public long getJavaStructureModificationCount() {
    return myJavaStructureModificationCount;
  }
}
