package com.github.meimingle.tsvnpwdintellij.services

import com.github.meimingle.tsvnpwdintellij.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
