package com.github.meimingle.tsvnpwdintellij.actions

import com.github.meimingle.tsvnpwdintellij.bundle.TSvnPwdBundle
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.ui.Messages
import com.tomxin.tool.tangible.ParserProgram
import com.tomxin.tool.tangible.Result

/**
 * @author meiMingle
 */
class FindSvnPasswordAction : AnAction(), DumbAware {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(CommonDataKeys.PROJECT)

        try {
           val allSvnInfo =  ParserProgram.findAllSvnInfo()
            if (!allSvnInfo.isNullOrEmpty()) {
                Messages.showMessageDialog(
                    project,
                    formatSvnInfo(allSvnInfo),
                    "TSvnPwd Information",
                    Messages.getWarningIcon()
                )
            } else {
                Messages.showMessageDialog(
                    project,
                    TSvnPwdBundle.message("popupTextNoResult"),
                    "TSvnPwd Information",
                    Messages.getInformationIcon()
                )
            }
        } catch (e: Exception) {
            Messages.showMessageDialog(
                project,
                TSvnPwdBundle.message("popupTextException",e.message!!) ,
                "TSvnPwd Information",
                Messages.getErrorIcon()
            )
        }

    }

    private fun formatSvnInfo(results: List<Result>): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("---------------------------------------------------\n")
        for (result in results) {
            stringBuilder.append("FileName = ").append(result.filename).append("\n")
            stringBuilder.append("Repository = ").append(result.repository).append("\n")
            stringBuilder.append("Username = ").append(result.username).append("\n")
            stringBuilder.append("Password = ").append(result.decryptedPassword).append("\n")
            stringBuilder.append("---------------------------------------------------\n")
        }
        return stringBuilder.toString()
    }

}
