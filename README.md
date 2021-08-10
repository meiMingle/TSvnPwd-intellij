# TSvnPwd-intellij

![Build](https://github.com/meiMingle/TSvnPwd-intellij/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)

## 介绍
<!-- Plugin description -->
忘记svn密码了吗？如果您在过去的某个时候选中了“保存身份验证”框，但此后忘记了您输入的密码，则该工具会很有帮助。
本项目核心代码移植自[http://www.leapbeyond.com/ric/TSvnPD/](http://www.leapbeyond.com/ric/TSvnPD/) 。同原作者描述，程序仅基于单一的默认环境进行编程，无法解析 Subversion 中所有允许的配置语法。
并且需要注意的是，仅当使用最初登录SVN时的同一 Windows 用户帐户登录并通过身份验证时，本工具才能有效解析出SVN的相关信息。
<!-- Plugin description end -->

## 安装

- 使用IDE内置插件系统:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "TSvnPwd"</kbd> >
  <kbd>Install Plugin</kbd>
  
- 手动:

  Download the [latest release](https://github.com/meiMingle/TSvnPwd-intellij/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
插件基于 [IntelliJ Platform Plugin Template][template].开发

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
