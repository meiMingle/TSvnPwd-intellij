package com.github.meimingle.tsvnpwdintellij.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.tomxin.tool.tangible.ParserProgram;
import com.tomxin.tool.tangible.Result;

import java.util.List;

/**
 * @author meiMingle
 */
public class FindSvnPasswordAction extends AnAction implements DumbAware {

    public FindSvnPasswordAction() {
        super("_Find Svn Password");
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(CommonDataKeys.PROJECT);
        List<Result> allSvnInfo = ParserProgram.findAllSvnInfo();
        if (!allSvnInfo.isEmpty()) {
            Messages.showMessageDialog(project, formatSvnInfo(allSvnInfo), "SVN Information", Messages.getInformationIcon());
        } else {
            Messages.showMessageDialog(project, "Can not find any information of svn", "Information", Messages.getInformationIcon());
        }

    }

    private String formatSvnInfo(List<Result> results) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---------------------------------------------------\n");
        for (Result result : results) {
            stringBuilder.append("FileName = ").append(result.getFilename()).append("\n");
            stringBuilder.append("Repository = ").append(result.getRepository()).append("\n");
            stringBuilder.append("Username = ").append(result.getUsername()).append("\n");
            stringBuilder.append("Password = ").append(result.getDecryptedPassword()).append("\n");
            stringBuilder.append("---------------------------------------------------\n");
        }

        return stringBuilder.toString();

    }
}
