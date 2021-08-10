package com.gethub.meimingle.tsvnpwdintellij.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;

/**
 * @author TomXin
 */
public class FindSvnPasswordAction extends AnAction implements DumbAware {

    public FindSvnPasswordAction() {
        //
        //super("_Find Svn Password");
        // 还可以设置菜单项名称，描述，图标
        super("_Find Svn Password","Find svn password", IconLoader.getIcon("",
                FindSvnPasswordAction.class));
        AnAction optionsGroup = ActionManager.getInstance().getAction("WelcomeScreen.Options");
        if ((optionsGroup instanceof DefaultActionGroup)) {
            ((DefaultActionGroup) optionsGroup).add(this);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        String txt= Messages.showInputDialog(project, "What is your name?", "Input your name", Messages.getQuestionIcon());
        Messages.showMessageDialog(project, "Hello, " + txt + "!\n I am glad to see you.", "Information", Messages.getInformationIcon());
    }
}
