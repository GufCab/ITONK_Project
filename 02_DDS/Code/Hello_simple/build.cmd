@ECHO OFF
REM ###########################################################################
REM #        (c) Copyright, Real-Time Innovations, All rights reserved.       #
REM #                                                                         #
REM #        Permission to modify and use for internal purposes granted.      #
REM # This software is provided "as is", without warranty, express or implied #
REM #                                                                         #
REM ###########################################################################


REM If Java compiler is not in your search path, set it here:
REM SET JAVAC="C:\Program Files\Java\jdk1.6.0_10"


REM Make sure NDDSHOME is set correctly
IF NOT DEFINED NDDSHOME (
    ECHO NDDSHOME environment variable is not set
    GOTO ENDSCRIPT
)

REM Make sure javac.exe is in the search path
FOR %%F IN (javac.exe) DO IF NOT EXIST %%~$PATH:F (
    ECHO Error: javac.exe not found in current search path.
    ECHO Make sure that Java SDK is correctly installed and you have the
    ECHO compiler in the search path.
    GOTO ENDSCRIPT
)

REM Ensure this script is invoked from the root directory of the project
IF NOT EXIST SRC (
    ECHO You must run this script from the example root directory
    GOTO ENDSCRIPT
)

REM Ensure the 'objs' directory exists
IF NOT EXIST objs (
    MD objs
)

ECHO Building all the sources in 'src' into 'objs' directory...
FOR /R SRC %%F IN (*.java) DO javac -d objs -sourcepath src -classpath "%NDDSHOME%"/class/nddsjava.jar "%%F"

:ENDSCRIPT:
