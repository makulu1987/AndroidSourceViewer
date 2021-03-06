package com.apkfuns.androidsourceviewer.action;

import com.apkfuns.androidsourceviewer.util.NotificationUtils;
import com.apkfuns.androidsourceviewer.util.Utils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

public abstract class BaseSourceAction extends AnAction {

    protected String packageName;
    protected Project project;
    protected AnActionEvent actionEvent;
    protected PsiElement element;
    protected String[] data;

    @Override
    public final void actionPerformed(AnActionEvent event) {
        this.project = event.getProject();
        this.actionEvent = event;
        packageName = Utils.getClassPath(event);
        element = event.getData(LangDataKeys.PSI_ELEMENT);
        if (Utils.isEmpty(packageName)) {
            NotificationUtils.infoNotification("Must be Class or Method");
            return;
        }
        this.selectActionPerformed(event, element, packageName);
        if (shouldSelectVersion()) {
            data = Utils.getVersionList(packageName);
            if (data == null) {
                NotificationUtils.infoNotification("Invalid PackageName:" + packageName);
                return;
            }
            this.onClassSelected(this.actionEvent, packageName);
        }
    }

    /**
     *  选择类回调
     * @param event
     * @param element 选择元素类型 (类/方法/变量等等)
     * @param packageName 选择的包名
     */
    protected void selectActionPerformed(AnActionEvent event, PsiElement element, String packageName) {

    }

    /**
     * 版本选择回调
     * @param event
     * @param packageName
     */
    protected void onClassSelected(AnActionEvent event, String packageName) {

    }

    /**
     * 是否需要选择版本
     * @return
     */
    protected boolean shouldSelectVersion() {
        return true;
    }

}
