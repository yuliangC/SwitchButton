# SwitchButton
自定义view练手项目

![](https://raw.githubusercontent.com/yuliangC/SwitchButton/master/screenshots/S80915-17115197.gif)


感谢KingJA同学的案例，在此基础上我进行了简化和重新绘制https://github.com/KingJA/SwitchButton


How to
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.yuliangC:SwitchButton:1.0.0'
	}
